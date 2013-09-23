package lms.foodchainC.fragment;

import java.util.ArrayList;

import lms.foodchainC.R;
import lms.foodchainC.dao.Case_DBHelper;
import lms.foodchainC.data.CaseStyleData;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuFragment extends Fragment implements OnPageChangeListener,
		OnClickListener, OnLongClickListener {
	private Case_DBHelper cdb;
	private LinearLayout title;
	private ViewPager pager;
	private ArrayList<CaseStyleData> styleList;
	private MenuFragAdapter mfa;
	private final int DELETE = 1;
	private final int EDIT = 2;
	private int currentItem = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initView();
		getData(getActivity());
		super.onActivityCreated(savedInstanceState);
	}

	private void getData(Context context) {
		cdb = new Case_DBHelper(context);
		styleList = cdb.getStyle();
		for (CaseStyleData csd : styleList) {
			TextView name = new TextView(getActivity());
			name.setText(csd.name);
			name.setOnLongClickListener(this);
			title.addView(name);
		}
		mfa = new MenuFragAdapter(getChildFragmentManager());
		pager.setAdapter(mfa);
		pager.setCurrentItem(0);
		title.setOnLongClickListener(this);
		title.setOnCreateContextMenuListener(this);
	}

	private void initView() {
		pager = (ViewPager) getView().findViewById(R.id.pager);
		title = (LinearLayout) getView().findViewById(R.id.title);
		getView().findViewById(R.id.create).setOnClickListener(this);
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

	@Override
	public boolean onLongClick(View v) {
		return false;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.add(0, DELETE, 1, "删除");
		menu.add(0, EDIT, 2, "编辑");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		CaseStyleData c = styleList.get(currentItem);
		switch (item.getItemId()) {

		default:
			break;
		}
		return true;
	}

}
