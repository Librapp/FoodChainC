package lms.foodchainC.fragment;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.activity.DetailActivity;
import lms.foodchainC.dao.Case_DBHelper;
import lms.foodchainC.data.CaseStyleData;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.net.JSONParser;
import lms.foodchainC.net.JSONRequest;
import lms.foodchainC.net.NetUtil;
import android.content.Context;
import android.content.Intent;
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
 * @createTime 2013/12/25
 */
public class MenuFragment extends Fragment implements OnPageChangeListener,
		OnClickListener {
	private Case_DBHelper cdb;
	private GetMenuTask getMenuTask;
	private LinearLayout title;
	private ViewPager pager;
	private MenuFragAdapter mfa;

	private List<CaseStyleData> styleList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu, container, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initView();
		getMenu();
		super.onActivityCreated(savedInstanceState);
	}

	private void getMenu() {
		if (getMenuTask != null) {
			getMenuTask.cancel(true);
			getMenuTask = null;
		}
		getMenuTask = new GetMenuTask();
		getMenuTask.execute(getActivity(), RestaurantData.local().localUrl);
	}

	private void getData() {
		styleList = cdb.getCaseStyleList();
		mfa = new MenuFragAdapter(getChildFragmentManager());
		pager.setAdapter(mfa);
		pager.setCurrentItem(0);
	}

	private void initView() {
		pager = (ViewPager) getView().findViewById(R.id.pager);
		title = (LinearLayout) getView().findViewById(R.id.title);
		getView().findViewById(R.id.sendorder).setOnClickListener(this);
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
		// TODO
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sendorder:
			Intent i = new Intent(getActivity(), DetailActivity.class);
			i.putExtra("title", R.string.sendorder);
			getActivity().startActivity(i);
			break;
		default:
			break;
		}
	}

	private class GetMenuTask extends AsyncTask<Object, Void, String> {

		@Override
		protected String doInBackground(Object... params) {
			Context context = (Context) params[0];
			String url = (String) params[1];
			String result = NetUtil.executePost(context,
					JSONRequest.menuData(), url);

			String msg = "";
			if (result != null) {
				if (cdb == null)
					cdb = new Case_DBHelper(context);
				msg = JSONParser.menuDataParse(result, cdb);
			}
			return msg;
		}

		@Override
		protected void onPostExecute(String result) {
			if (!result.equals(""))
				Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT)
						.show();
			else
				getData();
			super.onPostExecute(result);
		}

	}

}
