package lms.foodchainC.fragment;

import java.util.ArrayList;

import lms.foodchainC.R;
import lms.foodchainC.dao.Case_DBHelper;
import lms.foodchainC.data.CaseStyleData;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.net.GetMenuTask;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * 
 * @author 梦思
 * @description 菜单模块
 * @createTime 2013/12/25
 */
public class MenuFragment extends Fragment implements OnPageChangeListener,
		OnClickListener {
	private Case_DBHelper cdb;
	private LinearLayout title;
	private ViewPager pager;
	private MenuFragAdapter mfa;

	private ArrayList<CaseStyleData> styleList;

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
		getData(getActivity());
		super.onActivityCreated(savedInstanceState);
	}

	private void getMenu() {
		new GetMenuTask().execute(getActivity(),
				RestaurantData.local().localUrl);
	}

	private void getData(Context context) {
		cdb = new Case_DBHelper(context);
		styleList = cdb.getCaseStyleList();
		RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rl.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		rl.setMargins(5, 5, 5, 5);
		for (int i = 0; i < styleList.size(); i++) {
			CaseStyleData csd = styleList.get(i);
			Button name = new Button(getActivity());
			name.setId(csd.id);
			name.setLayoutParams(rl);
			name.setText(csd.name);
			name.setOnCreateContextMenuListener(this);
			title.addView(name);
		}
		mfa = new MenuFragAdapter(getChildFragmentManager());
		pager.setAdapter(mfa);
		pager.setCurrentItem(0);
	}

	private void initView() {
		pager = (ViewPager) getView().findViewById(R.id.pager);
		title = (LinearLayout) getView().findViewById(R.id.title);
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
		default:
			break;
		}
	}

}
