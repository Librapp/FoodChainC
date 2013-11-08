package lms.foodchainC.activity;

import lms.foodchainC.R;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.fragment.HallFragment;
import lms.foodchainC.fragment.ResDetailFragment;
import lms.foodchainC.fragment.SearchFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class RestaurantDetailActivity extends SherlockFragmentActivity
		implements OnClickListener, TabListener {
	private boolean isLocal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.restaurant);
		super.onCreate(savedInstanceState);
		setTitle(R.string.restaurant);
		initView();
		final ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

		bar.addTab(bar.newTab().setText(R.string.menu).setTabListener(this));
		bar.addTab(bar.newTab().setText(R.string.info).setTabListener(this));
		bar.addTab(bar.newTab().setText(R.string.hall).setTabListener(this));
		bar.getTabAt(1).select();
	}

	private void initView() {
		isLocal = getIntent().getBooleanExtra("isLocal", true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (tab.getText().equals(getResources().getString(R.string.menu))) {
			Fragment frag = getSupportFragmentManager().findFragmentByTag(
					getResources().getString(R.string.menu));
			if (frag == null) {
				frag = new SearchFragment();
				ft.replace(R.id.frame, frag,
						getResources().getString(R.string.menu));
			} else
				ft.attach(frag);
		} else if (tab.getText()
				.equals(getResources().getString(R.string.hall))) {
			Fragment frag = getSupportFragmentManager().findFragmentByTag(
					getResources().getString(R.string.hall));
			if (frag == null) {
				frag = new HallFragment();
				ft.replace(R.id.frame, frag,
						getResources().getString(R.string.hall));
			} else
				ft.attach(frag);
		} else if (tab.getText()
				.equals(getResources().getString(R.string.info))) {
			Fragment frag = getSupportFragmentManager().findFragmentByTag(
					getResources().getString(R.string.info));
			if (frag == null) {
				if (isLocal) {
					ft.replace(R.id.frame,
							ResDetailFragment.instance(RestaurantData.local()),
							getResources().getString(R.string.info));
				} else {
					ft.replace(R.id.frame, ResDetailFragment
							.instance(RestaurantData.current()), getResources()
							.getString(R.string.info));
				}
			} else
				ft.attach(frag);
		}
		ft.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}
