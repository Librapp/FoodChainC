package lms.foodchainC.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import lms.foodchainC.activity.MyApplication;
import lms.foodchainC.data.CustomerData;
import lms.foodchainC.data.EmployeeData;
import lms.foodchainC.data.OtherData;
import lms.foodchainC.data.RestaurantData;

import org.cybergarage.http.HTTPHeader;
import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.device.DeviceChangeListener;
import org.cybergarage.upnp.device.SearchResponseListener;
import org.cybergarage.upnp.ssdp.SSDPPacket;
import org.cybergarage.util.Debug;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-15
 * @description DlnaService
 * @changeLog
 */
public class DlnaService extends Service implements DeviceChangeListener {
	public static final String NEW_RESTAURANT_FOUND = "newRestaurantFound";
	public static final String SEARCH_DEVICE = "search_FC_device";

	private final IBinder binder = new DlnaServiceBinder();
	private static ControlPoint c;
	// CP有没有启动
	private static boolean started = false;
	private static SearchDeviceTask searchDeviceTask;

	public class DlnaServiceBinder extends Binder {
		public DlnaService getService() {
			return DlnaService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onCreate() {
		initControlPoint();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (SEARCH_DEVICE.equals(intent.getAction())) {
			searchDevice();
		}
		return Service.START_NOT_STICKY;
	}

	private void searchDevice() {
		if (searchDeviceTask != null) {
			searchDeviceTask.cancel(true);
			searchDeviceTask = null;
		}
		searchDeviceTask = new SearchDeviceTask();
		searchDeviceTask.execute(RestaurantData.local().isLocal);
	}

	private String getMyUUID() {
		final TelephonyManager tm = (TelephonyManager) getBaseContext()
				.getSystemService(TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String uniqueId = deviceUuid.toString();
		if (Debug.isOn())
			Log.d("debug", "uuid=" + uniqueId);
		return uniqueId;
	}

	private void initControlPoint() {
		c = new ControlPoint();
		c.addDeviceChangeListener(this);
		c.addSearchResponseListener(new SearchResponseListener() {
			@Override
			public void deviceSearchResponseReceived(SSDPPacket ssdpPacket) {
				Log.e("收到响应", Calendar.getInstance().getTime().toGMTString());
				String name = HTTPHeader.getValue(ssdpPacket.getData(),
						"MYNAME");
				if (!RestaurantData.local().name.equals(name)) {
					String l = ssdpPacket.getRemoteAddress();
					RestaurantData.local().localUrl = "http://" + l + ":4004";
					Intent intent = new Intent(NEW_RESTAURANT_FOUND);
					intent.putExtra("type", OtherData.RESTAURANTDEVICETYPE);
					intent.putExtra("address", RestaurantData.local().localUrl);
					RestaurantData.local().isLocal = true;
					sendBroadcast(intent);
				}
			}
		});
	}

	@Override
	public void deviceAdded(Device dev) {
		if (OtherData.CUSTOMERDEVICETYPE.equals(dev.getDeviceType())) {
			// TODO 发现新顾客
			CustomerData c = new CustomerData(dev);
			if (RestaurantData.current().getCustomer() != null)
				RestaurantData.current().getCustomer().add(c);
			else {
				List<CustomerData> lc = new ArrayList<CustomerData>();
				lc.add(c);
				RestaurantData.current().setCustomer(lc);
			}
		} else if (OtherData.WAITERDEVICETYPE.equals(dev.getDeviceType())) {
			// TODO 发现服务生
			EmployeeData c = new EmployeeData(dev, EmployeeData.WAITER);
			RestaurantData.current().getWaiter().add(c);
		} else if (OtherData.CLEANERDEVICETYPE.equals(dev.getDeviceType())) {
			// TODO 发现保洁员
			EmployeeData c = new EmployeeData(dev, EmployeeData.CLEANER);
			RestaurantData.current().getCleaner().add(c);
		} else if (OtherData.COOKERDEVICETYPE.equals(dev.getDeviceType())) {
			// TODO 发现厨师
			EmployeeData c = new EmployeeData(dev, EmployeeData.COOKER);
			RestaurantData.current().getCooker().add(c);
		} else if (OtherData.RESTAURANTDEVICETYPE.equals(dev.getDeviceType())) {
			Log.e("获取设备", Calendar.getInstance().getTime().toGMTString());
			if (MyApplication.isSelecting) {
				MyApplication.isSelecting = false;
				String l = dev.getLocation();
				Intent intent = new Intent(NEW_RESTAURANT_FOUND);
				intent.putExtra("type", OtherData.RESTAURANTDEVICETYPE);
				String address = "";
				if (l.contains("%"))
					address = l.substring(0, l.lastIndexOf("%")) + "]:4004";
				else
					address = l.substring(0, l.lastIndexOf(":")) + ":4004";
				intent.putExtra("address", address);
				RestaurantData.local().isLocal = true;
				RestaurantData.local().localUrl = address;
				RestaurantData.local().name = dev.getFriendlyName();
				sendBroadcast(intent);
			}
		}
	}

	@Override
	public void deviceRemoved(Device dev) {
		// TODO Auto-generated method stub

	}

	private class SearchDeviceTask extends AsyncTask<Object, Void, Void> {

		@Override
		protected Void doInBackground(Object... params) {
			if (!started) {
				c.start("FC");
				started = true;
			} else {
				c.search("FC");
			}
			return null;
		}
	}

}
