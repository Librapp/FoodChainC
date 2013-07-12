package lms.foodchainC.activity;

import java.util.ArrayList;

import lms.foodchainC.R;
import lms.foodchainC.data.CaseStyleData;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.net.JSONParser;
import lms.foodchainC.net.MenuRequest;
import lms.foodchainC.netUtil.NetUtil;
import lms.foodchainC.util.DialogUtil;
import lms.foodchainC.widget.MenuAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MenuActivity extends SlidingFragmentActivity implements
		OnClickListener, OnPageChangeListener, OnItemClickListener {

	private ArrayList<CaseStyleData> styleList;
	private ArrayList<CaseStyleData> timeList;
	private ViewPager viewPager;
	private GetMenuTask getMenuTask;
	private int currentPage = 0;
	private int currentFlag = 0;
	private final int STYLE = 1;
	private final int TIME = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		initView();
		getData();
	}

	private void getData() {
		if (getMenuTask != null) {
			getMenuTask.cancel(true);
			getMenuTask = null;
		}
		getMenuTask = new GetMenuTask();
		getMenuTask.execute(this);
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.pager);
	}

	private void refreshList(ArrayList<CaseStyleData> list) {
		viewPager.setAdapter(new MenuFragAdapter(list));
	}

	private class MenuFragAdapter extends PagerAdapter {
		private ArrayList<CaseStyleData> list;
		private ArrayList<ListView> mViews;

		MenuFragAdapter(ArrayList<CaseStyleData> list) {
			this.list = list;
			mViews = new ArrayList<ListView>();
			for (CaseStyleData csd : list) {
				ListView listView = new ListView(MenuActivity.this);
				listView.setAdapter(new MenuAdapter(MenuActivity.this, csd
						.getList()));
				mViews.add(listView);
			}
		}

		@Override
		public int getCount() {
			return mViews.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mViews.get(position));// 删除页卡
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return list.get(position).name;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mViews.get(position));
			return mViews.get(position);
		}

	}

	private class GetMenuTask extends AsyncTask<Object, String, String> {

		@Override
		protected String doInBackground(Object... params) {
			String result = NetUtil.executeGet(getApplicationContext(),
					new MenuRequest(1).make(),
					RestaurantData.current().localUrl);
			String msg = JSONParser.caseStyleDataParse(result, styleList);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("")) {
				refreshList(styleList);
			} else
				DialogUtil.alertToast(MenuActivity.this, result);
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
		currentPage = arg0;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (currentFlag) {
		case TIME:

			break;
		case STYLE:

			break;
		default:
			break;
		}
	}
}
