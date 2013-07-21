package lms.foodchainC.activity;

import java.util.ArrayList;
import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.dao.Case_DBHelper;
import lms.foodchainC.data.CaseData;
import lms.foodchainC.data.CaseStyleData;
import lms.foodchainC.data.MenuData;
import lms.foodchainC.data.OtherData;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.fragment.MenuFragment;
import lms.foodchainC.net.JSONParser;
import lms.foodchainC.net.JSONRequest;
import lms.foodchainC.net.NetUtil;
import lms.foodchainC.util.DialogUtil;
import lms.foodchainC.widget.CaseAddListener;
import lms.foodchainC.widget.ViewHolder;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MenuActivity extends SlidingFragmentActivity implements
		OnClickListener, OnPageChangeListener, TabListener, CaseAddListener {

	private ViewPager viewPager;
	private ArrayList<CaseStyleData> styleList;
	private ArrayList<CaseData> orderList;
	private ListView orderListVIew;
	private Button order;
	private OrderListAdapter ola;
	private MenuFragAdapter mfa;
	private MenuData menuData;
	private GetMenuDataTask getMenuDataTask;
	private Case_DBHelper cdb;
	private SharedPreferences sp;
	private FragmentTransaction mCurTransaction;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		cdb = new Case_DBHelper(this);
		sp = getSharedPreferences(OtherData.TEMPRESID, Context.MODE_PRIVATE);
		if (sp.getInt("restaurantId", 0) == RestaurantData.current().id) {
			styleList = cdb.getStyle();
			for (CaseStyleData csd : styleList) {
				Tab tab = getSupportActionBar().newTab();
				tab.setText(csd.name);
				getSupportActionBar().addTab(tab);
			}
		} else {
			getMenu();
		}
		initView();
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
		orderListVIew = new ListView(this);
		View v = LayoutInflater.from(this).inflate(R.layout.orderlist, null);
		orderListVIew = (ListView) v.findViewById(R.id.list);
		order = (Button) v.findViewById(R.id.order);
		order.setOnClickListener(this);
		setBehindContentView(R.layout.orderlist);
		viewPager = (ViewPager) findViewById(R.id.pager);
	}

	private class MenuFragAdapter extends FragmentPagerAdapter {

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
			if (mCurTransaction == null)
				mCurTransaction = getSupportFragmentManager()
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

		@Override
		public int getItemPosition(Object object) {
			return PagerAdapter.POSITION_NONE;
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
		switch (v.getId()) {
		case R.id.order:
			// TODO 发送订单
			break;
		default:
			break;
		}
	}

	private class GetMenuDataTask extends AsyncTask<Integer, Integer, String> {

		@Override
		protected String doInBackground(Integer... params) {
			String result = NetUtil.executePost(MenuActivity.this,
					JSONRequest.menuDataRequest(), NetUtil.LOCALHOST);
			menuData = new MenuData();
			String msg = JSONParser.menuDataParse(result, menuData, cdb);
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

	public class OrderListAdapter extends BaseAdapter {
		private Context context;
		private List<CaseData> lc;

		public OrderListAdapter(Context context, ListView list,
				List<CaseData> lc) {
			this.context = context;
			this.lc = lc;
		}

		@Override
		public int getCount() {
			return lc.size();
		}

		@Override
		public Object getItem(int position) {
			return lc.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder holder;
			CaseData c = lc.get(position);
			if (view == null) {
				view = LayoutInflater.from(context).inflate(
						R.layout.menu_list_item, null);
				holder = new ViewHolder();
				holder.text = (TextView) view.findViewById(R.id.case_name);
				holder.text1 = (TextView) view.findViewById(R.id.case_price);
				holder.pic1 = (ImageView) view.findViewById(R.id.case_status);
				holder.pic = (ImageView) view.findViewById(R.id.case_pic);
				view.setTag(holder);
			} else
				holder = (ViewHolder) view.getTag();
			holder.text1.setText(c.price + "元");
			holder.text.setText(c.name);

			switch (c.state) {
			case CaseData.AVILIABLE:
				holder.pic1.setImageResource(R.drawable.case_aviliable);
				break;
			case CaseData.UNAVILIABLE:
				holder.pic1.setImageResource(R.drawable.case_soldout);
				break;
			default:
				break;
			}

			Drawable d = Drawable.createFromPath(c.picPath);
			if (d != null) {
				holder.pic.setImageDrawable(d);
			}
			return view;
		}

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAdd(int caseId) {
		// TODO Auto-generated method stub

	}

}
