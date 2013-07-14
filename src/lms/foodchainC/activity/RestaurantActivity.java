package lms.foodchainC.activity;

import java.util.ArrayList;

import lms.foodchainC.R;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.data.TableStyle;
import lms.foodchainC.fragment.HallFragment;
import lms.foodchainC.fragment.MenuFragment;
import lms.foodchainC.net.JSONParser;
import lms.foodchainC.net.JSONRequest;
import lms.foodchainC.net.NetUtil;
import lms.foodchainC.service.DlnaService;
import lms.foodchainC.util.DialogUtil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * @author 李梦思
 * @version 1.0
 * @description 主界面
 * @createTime 2013-3-28
 */
public class RestaurantActivity extends FragmentActivity implements
		OnClickListener {
	private final int MENU = 0;
	private final int HALL = 1;
	private ArrayList<TableStyle> tableStyleList;
	private HallFragment restaurantFragment;
	private MenuFragment menuFragment;
	private RelativeLayout loading;
	private Button menu;
	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			loading.setVisibility(View.GONE);
			menu.setVisibility(View.VISIBLE);
			// getHallInfo();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant);
		loading = (RelativeLayout) findViewById(R.id.loading);
		menu = (Button) findViewById(R.id.menu);
		menu.setOnClickListener(this);
		if (RestaurantData.current().device == null)
			search();
		registerReceiver(receiver, new IntentFilter(
				DlnaService.NEW_DEVICES_FOUND));
	}

	protected void getHallInfo() {
		String result = NetUtil.executeGet(getApplicationContext(),
				JSONRequest.hallInfoRequest(), NetUtil.LOCALHOST);
		String msg = JSONParser.hallInfoParse(result, tableStyleList);
		if (msg.equals("")) {

		} else
			DialogUtil.alertToast(getApplicationContext(), msg);
	}

	private void search() {
		startService(new Intent(DlnaService.SEARCH_DEVICE));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU, 0, R.string.menu);
		menu.add(0, HALL, 1, R.string.hall);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		FragmentTransaction tf = getSupportFragmentManager().beginTransaction();
		switch (item.getItemId()) {
		case MENU:
			tf.replace(R.id.frame, menuFragment);
			break;
		case HALL:
			tf.replace(R.id.frame, restaurantFragment);
			break;
		default:
			break;
		}
		tf.commit();
		return true;
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(this, MenuActivity.class));
	}
}