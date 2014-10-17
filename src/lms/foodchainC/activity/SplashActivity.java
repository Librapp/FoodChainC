package lms.foodchainC.activity;

import lms.foodchainC.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-7-8
 * @description 启动界面
 * @changeLog
 */
public class SplashActivity extends Activity {

	private final int INITDATAEND = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		handler.sendEmptyMessageDelayed(INITDATAEND, 2000);
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

	private void openMainPageFast() {
		Intent intent = new Intent(SplashActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}
