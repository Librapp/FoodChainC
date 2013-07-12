package lms.foodchainC.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import lms.foodchainC.data.OtherData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.cybergarage.util.Debug;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2013-2-20
 * @description 地理位置服务
 * @changeLog
 */
public class LocateMySelfService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	LocationManager locationManager;
	private String lat;
	private String lon;

	private static final int MESSAGE_NEW_LOCATION = 1;
	private static final int MESSAGE_OVER = 2;
	private static final int MESSAGE_FAILED_USING_BASE = 3;
	private Thread t;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MESSAGE_NEW_LOCATION:
				if (msg.obj == null) {
					startGetLocationByWifiThread();
				} else {
					saveLocationInfo((String) msg.obj);
				}
				close();
				break;
			case MESSAGE_OVER:
				if (Debug.isOn())
					Log.e("LocateMySelfService", "message over close");
				close();
				break;
			case MESSAGE_FAILED_USING_BASE:
				startGetLocationByWifiThread();
				break;
			default:
				break;
			}
		};
	};

	private void close() {
		locationManager = null;
		telephonyManager = null;
		if (t != null) {
			t = null;
		}
		stopSelf();
	}

	private void saveLocationInfo(String city) {
		if (null == city) {
			return;
		}
		OtherData.currentCity = city;
		Toast.makeText(getApplicationContext(), OtherData.currentCity,
				Toast.LENGTH_SHORT).show();
		if (Debug.isOn())
			Log.e("LocateMySelfService", OtherData.currentCity);

		SharedPreferences sp = getSharedPreferences("otherInfo",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("currentCity", OtherData.currentCity);
		editor.commit();
	}

	private void initLocationInfo() {
		SharedPreferences sp = getSharedPreferences("otherInfo",
				Context.MODE_PRIVATE);
		String tempCity = sp.getString("currentCity", "北京");
		OtherData.currentCity = tempCity;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		initLocationInfo();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location != null) {
			double longtitude = location.getLongitude();
			double latitude = location.getLatitude();
			lat = String.valueOf(latitude);
			lon = String.valueOf(longtitude);
			if (lat != null) {
				t = new Thread() {
					public void run() {
						try {
							getAddress(lat, lon);
						} catch (Exception e) {
							handler.sendEmptyMessage(MESSAGE_OVER);
						}
					}
				};
				t.start();
			} else {
				startGetLocationByBaseThread();
			}
		} else {
			startGetLocationByBaseThread();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void startGetLocationByBaseThread() {
		t = new Thread() {
			public void run() {
				getLocationByBase();
			}
		};
		t.start();
	}

	private void startGetLocationByWifiThread() {
		if (Debug.isOn())
			Log.e("LocateMySelftService", "WifiConnection");
		t = new Thread() {
			public void run() {
				getLocationByWifi();
			}
		};
		t.start();
	}

	private void getAddress(String lat, String lon) throws Exception {
		URL url = new URL("http://maps.google.cn/maps/geo?key=abcdefg&q=" + lat
				+ "," + lon);
		InputStream inputStream = url.openConnection().getInputStream();
		InputStreamReader inputReader = new InputStreamReader(inputStream,
				"utf-8");
		BufferedReader bufReader = new BufferedReader(inputReader);
		String line = "", lines = "";
		while ((line = bufReader.readLine()) != null) {
			lines += line;
		}
		if (!lines.equals("")) {
			JSONObject jsonobject = new JSONObject(lines);
			JSONArray jsonArray = new JSONArray(jsonobject.get("Placemark")
					.toString());
			for (int i = 0; i < jsonArray.length(); i++) {
				// 取出JSON数据中user字段内容
				JSONObject u = jsonArray.getJSONObject(i)
						.getJSONObject("AddressDetails")
						.getJSONObject("Country");
				JSONObject uu = u.getJSONObject("AdministrativeArea")
						.getJSONObject("Locality");
				String city = uu.getString("LocalityName");
				Message msg = new Message();
				msg.what = MESSAGE_NEW_LOCATION;
				msg.obj = city;
				handler.sendMessage(msg);
			}
		}
	}

	private TelephonyManager telephonyManager;

	public void getLocationByBase() {
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		try {

			int type = telephonyManager.getNetworkType();
			int lac = 0;
			int ci = 0;
			if (type == TelephonyManager.PHONE_TYPE_GSM) {
				if (Debug.isOn())
					Log.e("LocatteMySeleftService", "GSM");
				GsmCellLocation location = (GsmCellLocation) telephonyManager
						.getCellLocation();
				lac = location.getLac();
				ci = location.getCid();

				int mcc = Integer.valueOf(telephonyManager.getNetworkOperator()
						.substring(0, 3));
				int mnc = Integer.valueOf(telephonyManager.getNetworkOperator()
						.substring(3, 5));

				JSONObject holder = new JSONObject();
				holder.put("version", "1.1.0");
				holder.put("host", "maps.google.com");
				holder.put("request_address", true);
				JSONArray array = new JSONArray();
				JSONObject data = new JSONObject();
				data.put("cell_id", ci);
				data.put("location_area_code", lac);
				data.put("mobile_country_code", mcc);
				data.put("mobile_network_code", mnc);
				array.put(data);
				holder.put("cell_towers", array);
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost("http://www.google.com/loc/json");
				StringEntity se = new StringEntity(holder.toString());
				post.setEntity(se);
				org.apache.http.HttpResponse resp = client.execute(post);
				HttpEntity entity = resp.getEntity();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						entity.getContent()));
				StringBuffer sb = new StringBuffer();
				String result = br.readLine();
				while (result != null) {
					sb.append(result);
					result = br.readLine();
				}
				JSONObject jsonObject = new JSONObject(sb.toString());

				JSONObject jsonObject1 = new JSONObject(
						jsonObject.getString("location"));

				getAddress(jsonObject1.getString("latitude"),
						jsonObject1.getString("longitude"));
			} else if (type == TelephonyManager.PHONE_TYPE_CDMA
					|| type == TelephonyManager.NETWORK_TYPE_EVDO_A
					|| type == TelephonyManager.NETWORK_TYPE_1xRTT) {
				if (Debug.isOn())
					Log.e("LocatteMySeleftService", "CDMA");
				CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) telephonyManager
						.getCellLocation();
				lac = cdmaCellLocation.getNetworkId();
				ci = cdmaCellLocation.getBaseStationId();
				int sid = cdmaCellLocation.getSystemId();

				int mcc = Integer.valueOf(telephonyManager.getNetworkOperator()
						.substring(0, 3));

				JSONObject holder = new JSONObject();
				holder.put("version", "1.1.0");
				holder.put("host", "maps.google.com");
				holder.put("request_address", true);
				holder.put("radio_type", "cdma");
				JSONArray array = new JSONArray();
				JSONObject data = new JSONObject();
				data.put("cell_id", ci);
				data.put("location_area_code", lac);
				data.put("mobile_country_code", mcc);
				data.put("mobile_network_code", sid);
				data.put("address_language", "zh_CN");
				data.put("age", 0);
				array.put(data);
				holder.put("cell_towers", array);
				if (Debug.isOn())
					Log.e("LocateMySelftService", holder.toString());
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost("http://www.google.com/loc/json");
				StringEntity se = new StringEntity(holder.toString());
				post.setEntity(se);
				org.apache.http.HttpResponse resp = client.execute(post);
				HttpEntity entity = resp.getEntity();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						entity.getContent()));
				StringBuffer sb = new StringBuffer();
				String result = br.readLine();
				while (result != null) {
					sb.append(result);
					result = br.readLine();
				}
				if (Debug.isOn())
					Log.e("LocateMySelftService", sb.toString());
				JSONObject jsonObject = new JSONObject(sb.toString());

				JSONObject jsonObject1 = new JSONObject(
						jsonObject.getString("location"));

				getAddress(jsonObject1.getString("latitude"),
						jsonObject1.getString("longitude"));
			} else {
				handler.sendEmptyMessage(MESSAGE_FAILED_USING_BASE);
			}
		} catch (Exception e) {
			handler.sendEmptyMessage(MESSAGE_FAILED_USING_BASE);
		}
	}

	public void getLocationByWifi() {
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		String mac = wifiManager.getConnectionInfo().getBSSID();
		if (mac == null) {
			return;
		}
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://www.google.com/loc/json");
		JSONObject holder = new JSONObject();
		try {
			holder.put("version", "1.1.0");
			holder.put("host", "maps.google.com");

			JSONObject data;
			JSONArray array = new JSONArray();
			data = new JSONObject();
			data.put("mac_address", mac);
			data.put("signal_strength", 8);
			data.put("age", 0);
			array.put(data);
			holder.put("wifi_towers", array);
			StringEntity se = new StringEntity(holder.toString());
			post.setEntity(se);
			HttpResponse resp = client.execute(post);
			int state = resp.getStatusLine().getStatusCode();
			if (state == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				if (entity != null) {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(entity.getContent()));
					StringBuffer sb = new StringBuffer();
					String resute = "";
					while ((resute = br.readLine()) != null) {
						sb.append(resute);
					}
					br.close();

					data = new JSONObject(sb.toString());
					data = (JSONObject) data.get("location");
					getAddress(String.valueOf(data.get("latitude")),
							String.valueOf(data.get("longitude")));
				} else {
					handler.sendEmptyMessage(MESSAGE_OVER);
				}
			} else {
				handler.sendEmptyMessage(MESSAGE_OVER);
			}
		} catch (Exception e) {
			handler.sendEmptyMessage(MESSAGE_OVER);
		}

	}
}
