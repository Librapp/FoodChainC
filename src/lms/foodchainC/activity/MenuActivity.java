package lms.foodchainC.activity;

import java.util.ArrayList;
import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.dao.Case_DBHelper;
import lms.foodchainC.data.CaseStyleData;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.net.JSONParser;
import lms.foodchainC.net.JSONRequest;
import lms.foodchainC.net.NetUtil;
import lms.foodchainC.widget.MyExpandableListAdapter;
import android.app.ExpandableListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

/**
 * 
 * @author 梦思
 * @description 菜单界面
 * @createTime 2014/01/07
 */
public final class MenuActivity extends ExpandableListActivity implements
		OnClickListener {
	private PullToRefreshExpandableListView mPullRefreshListView;
	private MyExpandableListAdapter mAdapter;

	private Case_DBHelper cdb;
	private List<CaseStyleData> styleList;
	private GetMenuTask getMenuTask;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ptr_expandable_list);
		mPullRefreshListView = (PullToRefreshExpandableListView) findViewById(R.id.pull_refresh_expandable_list);
		findViewById(R.id.sendorder).setOnClickListener(this);
		getMenu();
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
			AsyncTask<Object, JSONRequest, List<CaseStyleData>> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected List<CaseStyleData> doInBackground(Object... params) {

			String result = NetUtil.executePost(MenuActivity.this,
					JSONRequest.menuData(),
					RestaurantData.local().localUrl);
			styleList = new ArrayList<CaseStyleData>();
			if (cdb == null)
				cdb = new Case_DBHelper(MenuActivity.this);
			if (JSONParser.menuDataParse(result, cdb).equals("")) {
				styleList = cdb.getCaseStyleList();
			} else {
				Toast.makeText(MenuActivity.this, "加载失败", Toast.LENGTH_SHORT)
						.show();
			}
			return styleList;
		}

		@Override
		protected void onPostExecute(List<CaseStyleData> styleList) {
			mAdapter = new MyExpandableListAdapter(MenuActivity.this, styleList);
			setListAdapter(mAdapter);
			mAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();
			super.onPreExecute();
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sendorder:
			// TODO 下订单
			break;

		default:
			break;
		}
	}
}
