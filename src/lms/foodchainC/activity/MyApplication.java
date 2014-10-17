package lms.foodchainC.activity;

import lms.foodchainC.data.CustomerData;
import lms.foodchainC.data.OtherData;
import lms.foodchainC.net.NetMessageType;
import lms.foodchainC.service.DlnaService;

import org.cybergarage.util.Debug;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MyApplication extends Application {

	private static MyApplication myApplication;
	public static CustomerData self;
	public static String password;
	public static boolean isSelecting = false;

	@Override
	public void onCreate() {
		myApplication = this;
		disableConnectionReuseIfNecessary();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).build();
		ImageLoader.getInstance().init(config);
		// TODO 弹出对话框：是否打开网络，是，转到网络设置，否，进入单机版
		// 在设置中加入网络设置
		if (!getNetStatus()) {
			Toast.makeText(this, NetMessageType.HAS_NO_NETWORK,
					Toast.LENGTH_LONG).show();
		} else {

		}
		// 调试模式开关
		Debug.enabled = true;
		// 启动服务
		startService(new Intent(DlnaService.SEARCH_DEVICE));
		// 加载用户数据
		initData();
	}

	private void initData() {
		SharedPreferences spl = getSharedPreferences(OtherData.LOCALSETTING,
				Context.MODE_PRIVATE);
		self = new CustomerData();
		// 自定义
		self.name = spl.getString("name", "吃货");
		self.id = spl.getInt("id", 0);
		self.tel = spl.getString("tel", "空");
		self.email = spl.getString("email", "空");
		self.intro = spl.getString("intro", "虔诚的吃货");
		self.address = spl.getString("address", "空");
		password = spl.getString("passWord", "123456");
	}

	private boolean getNetStatus() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}

	private void disableConnectionReuseIfNecessary() {
		// Work around pre-Froyo bugs in HTTP connection reuse.
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
			System.setProperty("http.keepAlive", "false");
		}
	}

	public static void getInfo() {
		SharedPreferences spl = myApplication.getSharedPreferences(
				OtherData.LOCALSETTING, Context.MODE_PRIVATE);
		self = new CustomerData();
		// 自定义
		self.name = spl.getString("name", "吃货");
		self.id = spl.getInt("id", 0);
		self.tel = spl.getString("tel", "空");
		self.email = spl.getString("email", "空");
		self.intro = spl.getString("intro", "虔诚的吃货");
		self.address = spl.getString("address", "空");
		self.credit = spl.getInt("credit", 0);
		self.point = spl.getInt("point", 0);
		password = spl.getString("passWord", "123456");
	}

	public static void saveInfo() {
		SharedPreferences spl = myApplication.getSharedPreferences(
				OtherData.LOCALSETTING, Context.MODE_PRIVATE);
		Editor ed = spl.edit();
		ed.putString("name", self.name);
		ed.putString("tel", self.tel);
		ed.putString("passWord", password);
		ed.putString("intro", self.intro);
		ed.putString("address", self.address);
		ed.putString("email", self.email);
		ed.putInt("id", self.id);
		ed.putInt("credit", self.credit);
		ed.putInt("point", self.point);
		ed.commit();
	}
}
