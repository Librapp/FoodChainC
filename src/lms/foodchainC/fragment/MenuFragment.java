package lms.foodchainC.fragment;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.activity.DetailActivity;
import lms.foodchainC.dao.Bill_DBHelper;
import lms.foodchainC.dao.Case_DBHelper;
import lms.foodchainC.data.CaseData;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 
 * @author 梦思
 * @description 菜单模块
 * @createTime 2013/12/25
 */
public class MenuFragment extends Fragment implements OnPageChangeListener,
		OnClickListener, OnItemClickListener {
	private Case_DBHelper cdb;
	private Bill_DBHelper bdb;
	private GetMenuTask getMenuTask;
	private ViewPager pager;
	private MenuFragAdapter mfa;
	private Button order;
	private int orderCount = 0;
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
		cdb = new Case_DBHelper(getActivity());
		// getMenu();
		getData();
		bdb = new Bill_DBHelper(getActivity());
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
		// styleList = new ArrayList<CaseStyleData>();
		// for (int i = 0; i < 10; i++) {
		// CaseStyleData csd = new CaseStyleData();
		// styleList.add(csd);
		// }
		mfa = new MenuFragAdapter(getChildFragmentManager());
		pager.setAdapter(mfa);
		pager.setCurrentItem(0);
	}

	private void initView() {
		pager = (ViewPager) getView().findViewById(R.id.pager);
		order = (Button) getView().findViewById(R.id.sendorder);
		order.setOnClickListener(this);
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		CaseData c = (CaseData) arg0.getAdapter().getItem(arg2);
		if (bdb.insertCase(c)) {
			orderCount++;
			order.setText("已点" + orderCount + "个菜");
		}
	}

}
