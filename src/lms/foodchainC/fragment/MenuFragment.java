package lms.foodchainC.fragment;

import java.util.ArrayList;

import lms.foodchainC.dao.Case_DBHelper;
import lms.foodchainC.data.CaseStyleData;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.net.JSONParser;
import lms.foodchainC.net.JSONRequest;
import lms.foodchainC.net.NetUtil;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshExpandableListFragment;

/**
 * 
 * @author 梦思
 * @description 菜单模块
 * @createTime 2013/12/30
 */
public class MenuFragment extends PullToRefreshExpandableListFragment implements
		OnItemClickListener {
	private Case_DBHelper cdb;
	private ArrayList<CaseStyleData> styleList;
	private int currentItem = 0;
	private GetMenuTask getMenuTask;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		cdb = new Case_DBHelper(getActivity());
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
			// mAdapter = new SimpleExpandableListAdapter(this, groupData,
			// android.R.layout.simple_expandable_list_item_1,
			// new String[] { KEY }, new int[] { android.R.id.text1 },
			// childData,
			// android.R.layout.simple_expandable_list_item_2, new String[] {
			// KEY }, new int[] { android.R.id.text1 });
			// mPullRefreshListView.setAdapter(adapter);
			super.onPreExecute();
		};

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

}
