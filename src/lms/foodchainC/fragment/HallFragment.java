package lms.foodchainC.fragment;

import java.util.ArrayList;

import lms.foodchainC.R;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.data.TableStyleData;
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
import android.view.ViewGroup;

public class HallFragment extends Fragment implements OnPageChangeListener {
	private ViewPager pager;
	private ArrayList<TableStyleData> styleList;
	private HallFragAdapter mfa;
	private GetHallInfoTask getHallInfoTask;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.hall, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initView();
		refresh();
		super.onActivityCreated(savedInstanceState);
	}

	private void initView() {
		pager = (ViewPager) getView().findViewById(R.id.pager);
	}

	private class HallFragAdapter extends FragmentPagerAdapter {

		public HallFragAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			TableStyleData csd = styleList.get(arg0);
			return TableStyleFragment.newInstance(csd.id);
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
			return styleList.get(position).seatCount + "人桌";
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

	private void refresh() {
		if (getHallInfoTask != null) {
			getHallInfoTask.cancel(true);
			getHallInfoTask = null;
		}
		getHallInfoTask = new GetHallInfoTask();
		getHallInfoTask.execute();
	}

	private class GetHallInfoTask extends
			AsyncTask<Object, JSONRequest, ArrayList<TableStyleData>> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected ArrayList<TableStyleData> doInBackground(Object... params) {

			String result = NetUtil.executePost((Context) params[0],
					(String) params[1],
					RestaurantData.current().device.getLocation());
			styleList = new ArrayList<TableStyleData>();
			JSONParser.hallInfoParse(result, styleList);
			return styleList;
		}

		@Override
		protected void onPostExecute(ArrayList<TableStyleData> styleList) {
			mfa = new HallFragAdapter(getChildFragmentManager());
			pager.setAdapter(mfa);
			super.onPreExecute();
		};

	}
}
