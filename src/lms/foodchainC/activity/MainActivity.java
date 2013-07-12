package lms.foodchainC.activity;

import lms.foodchainC.R;
import lms.foodchainC.service.DlnaService;
import lms.foodchainC.util.DialogUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		findViewById(R.id.search).setOnClickListener(this);
		findViewById(R.id.restaurant).setOnClickListener(this);
		findViewById(R.id.bill).setOnClickListener(this);
		findViewById(R.id.message).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search:
			startService(new Intent(DlnaService.SEARCH_DEVICE));
			break;
		case R.id.restaurant:
			startActivity(new Intent(this, RestaurantActivity.class));
			break;
		case R.id.bill:

			break;
		case R.id.message:

			break;
		default:
			break;
		}
	}

	private long lastTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastTime >= 0 && currentTime - lastTime <= 2000) {
				return super.onKeyDown(keyCode, event);
			} else {
				DialogUtil.alertToast(getApplicationContext(), "再按一次退出");
				lastTime = currentTime;
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
