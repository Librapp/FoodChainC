package lms.foodchainC.activity;

import lms.foodchainC.R;
import lms.foodchainC.data.SystemData;
import lms.foodchainC.net.NetMessageType;
import lms.foodchainC.util.SharePerformanceUtil;

import org.cybergarage.util.Debug;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-7-8
 * @description 启动界面
 * @changeLog
 */
public class SplashActivity extends BaseActivity {

	private final int INITDATAEND = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		SystemData.hasNet = getNetStatus();
		// TODO 弹出对话框：是否打开网络，是，转到网络设置，否，进入单机版
		// 在设置中加入网络设置
		if (!SystemData.hasNet) {
			Toast.makeText(this, NetMessageType.HAS_NO_NETWORK,
					Toast.LENGTH_LONG).show();
		} else {

		}
		SharePerformanceUtil.getLInfo(this);

		// TODO 调试模式关闭
		Debug.enabled = true;
		// 启动服务
		// startService(new Intent(this, MenuService.class));
		// startService(new Intent(this, TableService.class));
		// startService(new Intent(this, DlnaService.class));
		handler.sendEmptyMessage(INITDATAEND);
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case INITDATAEND:
				openMainPageFast();
				break;
			default:
				break;
			}
		}
	};

	private boolean getNetStatus() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}

	private void openMainPageFast() {
		Intent intent = new Intent(SplashActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}
