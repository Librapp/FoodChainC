package lms.foodchainC.fragment;

import java.util.ArrayList;
import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.dao.Table_DBHelper;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.data.SeatData;
import lms.foodchainC.data.TableStyleData;
import lms.foodchainC.net.JSONParser;
import lms.foodchainC.net.JSONRequest;
import lms.foodchainC.net.NetUtil;
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
import android.widget.Button;
import android.widget.Toast;

public class HallFragment extends Fragment implements OnPageChangeListener {
	private ViewPager pager;
	private List<TableStyleData> styleList;
	private Button setSeat;
	private HallFragAdapter mfa;
	private GetHallInfoTask getHallInfoTask;
	private Table_DBHelper tdb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.hall, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initView();
		tdb = new Table_DBHelper(getActivity());
		styleList = tdb.getTableStyleDataList();

		mfa = new HallFragAdapter(getChildFragmentManager());
		pager.setAdapter(mfa);
		super.onActivityCreated(savedInstanceState);
	}

	private void initView() {
		pager = (ViewPager) getView().findViewById(R.id.pager);
		setSeat = (Button) getView().findViewById(R.id.setseat);
		setSeat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new SetSeatTask().execute(new SeatData());
			}
		});
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
			AsyncTask<Object, JSONRequest, List<TableStyleData>> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected List<TableStyleData> doInBackground(Object... params) {

			String result = NetUtil.executePost(getActivity()
					.getApplicationContext(), JSONRequest.hallInfo(),
					RestaurantData.local().localUrl);
			styleList = new ArrayList<TableStyleData>();
			JSONParser.hallInfoParse(result, tdb);
			return styleList;
		}

		@Override
		protected void onPostExecute(List<TableStyleData> styleList) {
			mfa = new HallFragAdapter(getChildFragmentManager());
			pager.setAdapter(mfa);
			super.onPreExecute();
		};
	}

	private class SetSeatTask extends AsyncTask<Object, Void, String> {

		@Override
		protected String doInBackground(Object... params) {
			return NetUtil.executePost(getActivity(),
					JSONRequest.setSeat((SeatData) params[0]),
					RestaurantData.local().localUrl);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("")) {
				// TODO
			} else {
				Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT)
						.show();
			}
			super.onPostExecute(result);
		}
	}
}
