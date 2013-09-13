package lms.foodchainC.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import lms.foodchainC.data.CustomerData;
import lms.foodchainC.data.EmployeeData;
import lms.foodchainC.data.OtherData;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.data.Self;
import lms.foodchainC.data.TableStyleData;
import lms.foodchainC.net.JSONParser;
import lms.foodchainC.net.JSONRequest;
import lms.foodchainC.net.NetUtil;
import lms.foodchainC.upnp.ControlPoint;
import lms.foodchainC.util.DialogUtil;
import lms.foodchainC.util.FileInfoUtils;

import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.DeviceList;
import org.cybergarage.upnp.RootDescription;
import org.cybergarage.upnp.device.DeviceChangeListener;
import org.cybergarage.upnp.device.SearchResponseListener;
import org.cybergarage.upnp.ssdp.SSDPPacket;
import org.cybergarage.util.Debug;
import org.cybergarage.xml.Node;

import android.app.Service;
import android.content.Intent;
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

	public static final String NEW_DEVICES_FOUND = "newDeviceFound";
	public static final String SEARCH_DEVICE = "search_device";

	private final IBinder binder = new DlnaServiceBinder();
	private ControlPoint c;
	// CP有没有启动
	private static boolean started = false;
	private lms.foodchainC.upnp.Device d;

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
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				initServer();
			}
		});
		thread.start();
		initControlPoint();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (SEARCH_DEVICE.equals(intent.getAction())) {
			multicastSearch();
		}
		return Service.START_NOT_STICKY;
	}

	private void initServer() {
		Node root = new Node(RootDescription.ROOT_ELEMENT);
		root.setNameSpace("", RootDescription.ROOT_ELEMENT_NAMESPACE);
		Node spec = new Node(RootDescription.SPECVERSION_ELEMENT);
		Node maj = new Node(RootDescription.MAJOR_ELEMENT);
		maj.setValue("1");
		Node min = new Node(RootDescription.MINOR_ELEMENT);
		min.setValue("0");
		spec.addNode(maj);
		spec.addNode(min);
		root.addNode(spec);

		Node device = new Node(Device.ELEM_NAME);
		root.addNode(device);

		d = new lms.foodchainC.upnp.Device(root, device);
		d.setFriendlyName(Self.current().name);
		String uuid = "uuid:" + getMyUUID();
		d.setUDN(uuid);
		// TODO
		d.setDeviceType(OtherData.CUSTOMERDEVICETYPE);
		d.setDescriptionURI(OtherData.DESCRIPTIONURL);
		if (FileInfoUtils.writeFile(d.getRootNode().toString().getBytes(),
				"FCR", "description.xml"))
			d.start();
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

			}
		});
	}

	public void multicastSearch() {

		if (Debug.isOn()) {
			DeviceList deviceList = c.getDeviceList();
			Iterator iterator = deviceList.iterator();
			while (iterator.hasNext()) {
				Device next = (Device) iterator.next();
				System.out.println(next.getFriendlyName());
			}
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				if (!started) {
					c.start();
					started = true;
				} else {
					c.search();
				}
			}
		}).start();
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
			sendBroadcast(new Intent(NEW_DEVICES_FOUND));
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
			RestaurantData.current().device = dev;
			getResDetail(dev);
		}
	}

	private void getResDetail(Device dev) {
		RestaurantData.current().localUrl = "http://"
				+ dev.getInterfaceAddress() + ":4004";
		String result = NetUtil.executeGet(getApplicationContext(),
				JSONRequest.restaurantInfoRequest(),
				RestaurantData.current().localUrl);
		String msg = JSONParser.restaurantInfoParse(result,
				RestaurantData.current());
		if (msg.equals(""))
			sendBroadcast(new Intent(NEW_DEVICES_FOUND).putExtra("type",
					OtherData.RESTAURANTDEVICETYPE));
		else
			DialogUtil.alertToast(getApplicationContext(), msg);
	}

	public String getHallInfo(ArrayList<TableStyleData> tableStyleList) {
		String result = NetUtil.executeGet(getApplicationContext(),
				JSONRequest.hallInfoRequest(),
				RestaurantData.current().localUrl);
		String msg = JSONParser.hallInfoParse(result, tableStyleList);
		if (msg.equals("")) {
			// TODO 存储
		}
		return msg;
	}

	@Override
	public void deviceRemoved(Device dev) {
		// TODO Auto-generated method stub

	}

}
