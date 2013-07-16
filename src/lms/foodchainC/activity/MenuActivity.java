package lms.foodchainC.activity;

import java.util.ArrayList;

import lms.foodchainC.R;
import lms.foodchainC.data.CaseStyleData;
import lms.foodchainC.data.MenuData;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.fragment.MenuFragment;
import lms.foodchainC.net.JSONParser;
import lms.foodchainC.net.JSONRequest;
import lms.foodchainC.net.NetUtil;
import lms.foodchainC.util.DialogUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MenuActivity extends SlidingFragmentActivity implements
		OnClickListener, OnPageChangeListener {

	private ViewPager viewPager;
	private ArrayList<CaseStyleData> styleList;
	private MenuFragAdapter mfa;
	private MenuData menuData;
	private GetMenuDataTask getMenuDataTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		initView();
		getMenu();
	}

	private void getMenu() {
		if (getMenuDataTask != null) {
			getMenuDataTask.cancel(true);
			getMenuDataTask = null;
		}
		getMenuDataTask = new GetMenuDataTask();
		if (RestaurantData.current().isLocal)
			getMenuDataTask.execute(0);
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.pager);
	}

	private class MenuFragAdapter extends FragmentStatePagerAdapter {

		public MenuFragAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			CaseStyleData csd = styleList.get(arg0);
			Bundle bundle = new Bundle();
			bundle.putInt("styleId", csd.id);
			return MenuFragment.instantiate(getApplicationContext(), csd.name,
					bundle);
		}

		@Override
		public int getCount() {
			return styleList.size();
		}

		@Override
		public Object instantiateItem(ViewGroup arg0, int arg1) {
			CaseStyleData csd = styleList.get(arg1);
			FragmentTransaction mCurTransaction = getSupportFragmentManager()
					.beginTransaction();
			Fragment frag = getSupportFragmentManager().findFragmentByTag(
					csd.name);
			if (frag == null) {
				frag = getItem(arg1);
				mCurTransaction.add(frag, csd.name);
			} else
				mCurTransaction.attach(frag);
			mCurTransaction.commit();
			return frag;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	private class GetMenuDataTask extends AsyncTask<Integer, Integer, String> {

		@Override
		protected String doInBackground(Integer... params) {
			String result = NetUtil.executePost(MenuActivity.this,
					JSONRequest.menuDataRequest(), NetUtil.LOCALHOST);
			String msg = JSONParser.menuDataParse(result, menuData);
			return msg;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("")) {
				mfa = new MenuFragAdapter(getSupportFragmentManager());
				viewPager.setAdapter(mfa);
			} else
				DialogUtil.alertToast(MenuActivity.this, result);
			super.onPostExecute(result);
		}
	}

}
