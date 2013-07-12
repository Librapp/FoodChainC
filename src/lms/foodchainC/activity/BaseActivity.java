package lms.foodchainC.activity;

import lms.foodchainC.util.DialogUtil;
import android.app.Activity;
import android.view.KeyEvent;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-18
 * @description 基本界面
 * @changeLog
 */
public class BaseActivity extends Activity {
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
