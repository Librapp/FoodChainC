package lms.foodchainC.fragment;

import java.util.ArrayList;

import lms.foodchainC.R;
import lms.foodchainC.dao.Case_DBHelper;
import lms.foodchainC.data.CaseStyleData;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.net.JSONParser;
import lms.foodchainC.net.JSONRequest;
import lms.foodchainC.net.NetUtil;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 
 * @author 梦思
 * @description 菜单模块
 * @createTime 2013/12/30
 */
public class MenuFragment extends Fragment implements OnPageChangeListener,
		OnClickListener {
	private Case_DBHelper cdb;
	private LinearLayout title;
	private ViewPager pager;
	private ArrayList<CaseStyleData> styleList;
	private MenuFragAdapter mfa;
	private int currentItem = 0;
	private GetMenuTask getMenuTask;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		cdb = new Case_DBHelper(getActivity());
		initView();
		getData(getActivity());
		super.onActivityCreated(savedInstanceState);
	}

	private void getData(Context context) {
		// if (getArguments().getInt("id") != RestaurantData.local().id) {
		getMenu();
		// } else {
		// cdb = new Case_DBHelper(context);
		// styleList = cdb.getCaseStyleList();
		// for (CaseStyleData csd : styleList) {
		// TextView name = new TextView(getActivity());
		// name.setText(csd.name);
		// title.addView(name);
		// }
		// mfa = new MenuFragAdapter(getChildFragmentManager());
		// pager.setAdapter(mfa);
		// pager.setCurrentItem(0);
		// title.setOnCreateContextMenuListener(this);
		// }
	}

	private void initView() {
		View v = getView();
		pager = (ViewPager) v.findViewById(R.id.pager);
		title = (LinearLayout) v.findViewById(R.id.title);
	}

	private class MenuFragAdapter extends FragmentPagerAdapter {

		public MenuFragAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			CaseStyleData csd = styleList.get(arg0);
			return CaseStyleFragment.newInstance(csd.id);
		}

		@Override
		public int getCount() {
			return styleList.size();
		}

		@Override
		public int getItemPosition(Object object) {
			return PagerAdapter.POSITION_NONE;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return styleList.get(position).name;
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
		currentItem = arg0;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}
	}

	private void getMenu() {
		if (getMenuTask != null) {
			getMenuTask.cancel(true);
			getMenuTask = null;
		}
		getMenuTask = new GetMenuTask();
		getMenuTask.execute();
	}

	private class GetMenuTask extends
			AsyncTask<Object, JSONRequest, ArrayList<CaseStyleData>> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected ArrayList<CaseStyleData> doInBackground(Object... params) {

			String result = NetUtil.executePost(getActivity()
					.getApplicationContext(), JSONRequest.menuDataRequest(),
					RestaurantData.local().localUrl);
			styleList = new ArrayList<CaseStyleData>();
			if (JSONParser.menuDataParse(result, cdb).equals("")) {
				styleList = cdb.getCaseStyleList();
			} else {
				Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT)
						.show();
			}
			return styleList;
		}

		@Override
		protected void onPostExecute(ArrayList<CaseStyleData> styleList) {
			mfa = new MenuFragAdapter(getChildFragmentManager());
			pager.setAdapter(mfa);
			super.onPreExecute();
		};

	}

}
