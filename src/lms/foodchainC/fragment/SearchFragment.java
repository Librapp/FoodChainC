package lms.foodchainC.fragment;

import java.util.ArrayList;

import lms.foodchainC.R;
import lms.foodchainC.data.CaseData;
import lms.foodchainC.data.OtherData;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.net.JSONParser;
import lms.foodchainC.net.JSONRequest;
import lms.foodchainC.net.NetUtil;
import lms.foodchainC.service.DlnaService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author 李梦思
 * @version 1.0
 * @description 搜索界面
 * @createTime 2013-9-11
 */
public class SearchFragment extends Fragment implements OnClickListener,
		TextWatcher, OnItemClickListener {
	private final int CASE = 1;
	private final int RESTAURANT = 0;
	private int searchType = RESTAURANT;
	private int netType = 0;

	private BroadcastReceiver receiver;
	private ConnectivityManager connectivityManager;
	private LinearLayout currentLayout;
	private ListView resultList;
	private TextView currentRes;
	private EditText edit;
	private ImageButton clean;
	private ArrayList<CaseData> caseResult;
	private ArrayList<RestaurantData> resResult;
	private GetLocalResInfoTask getLocalResInfoTask;
	private FragmentTransaction ft;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.search, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		connectivityManager = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		ft = getFragmentManager().beginTransaction();
		initView();
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getStringExtra("type").equals(
						OtherData.RESTAURANTDEVICETYPE)) {
					RestaurantData.local().name = intent.getStringExtra("name");
					RestaurantData.local().localUrl = intent
							.getStringExtra("address");
					currentRes.setText(RestaurantData.local().name);
					currentLayout.setVisibility(View.VISIBLE);
				}
			}
		};
		super.onActivityCreated(savedInstanceState);
	}

	private void initView() {
		getView().findViewById(R.id.search_restaurant).setOnClickListener(this);
		getView().findViewById(R.id.search_case).setOnClickListener(this);
		getView().findViewById(R.id.search_btn).setOnClickListener(this);
		getView().findViewById(R.id.search_hall).setOnClickListener(this);
		getView().findViewById(R.id.search_menu).setOnClickListener(this);
		clean = (ImageButton) getView().findViewById(R.id.search_clean);
		clean.setOnClickListener(this);
		edit = (EditText) getView().findViewById(R.id.search_edit);
		edit.addTextChangedListener(this);
		currentLayout = (LinearLayout) getView().findViewById(
				R.id.current_layout);
		currentRes = (TextView) getView().findViewById(R.id.current_res);
		currentRes.setOnClickListener(this);
		resultList = (ListView) getView().findViewById(R.id.resultlist);
		resultList.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_restaurant:
			searchType = RESTAURANT;
			break;
		case R.id.search_case:
			searchType = CASE;
			break;
		case R.id.search_btn:
			search();
			break;
		case R.id.search_clean:
			edit.setText("");
			break;
		case R.id.current_res:
			getLocalResDetail();
			break;
		case R.id.search_hall:
			// TODO 跳转到大厅
			ft.replace(R.id.frame, new HallFragment());
			ft.commit();
			break;
		case R.id.search_menu:
			// TODO 跳转到菜单
			ft.replace(R.id.frame, new MenuFragment());
			ft.commit();
			break;
		default:
			break;
		}
	}

	/** 获取局域网内餐厅信息 */
	private void getLocalResDetail() {
		getLocalResInfoTask = new GetLocalResInfoTask();
		getLocalResInfoTask.execute(RestaurantData.local().localUrl);
	}

	private void search() {
		switch (searchType) {
		case CASE:
			// TODO 判断是否在饭店然后搜索
			break;
		case RESTAURANT:
			connectivityManager = (ConnectivityManager) getActivity()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();
			netType = networkInfo.getType();
			switch (netType) {
			case ConnectivityManager.TYPE_MOBILE:
				// openWifiDialog();
				// TODO 打开并自动连接到饭店网络
				break;
			case ConnectivityManager.TYPE_WIFI:
				searchInLan();
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
	}

	private void searchInLan() {
		getActivity().startService(new Intent(DlnaService.SEARCH_DEVICE));
	}

	// private void openWifiDialog() {
	// MyDialogFragment.openWifiInstance().show(getChildFragmentManager(),
	// "dialog");
	// }

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		if (s.length() > 0)
			edit.setVisibility(View.VISIBLE);
		else
			edit.setVisibility(View.GONE);
	}

	@Override
	public void onResume() {
		getActivity().registerReceiver(receiver,
				new IntentFilter(DlnaService.NEW_RESTAURANT_FOUND));
		super.onResume();
	}

	private class GetLocalResInfoTask extends AsyncTask<Object, String, String> {

		@Override
		protected String doInBackground(Object... params) {
			String result = NetUtil.executePost(getActivity()
					.getApplicationContext(), JSONRequest
					.restaurantInfoRequest(), RestaurantData.local().localUrl);
			String msg = JSONParser.restaurantInfoParse(result,
					RestaurantData.local());
			return msg;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("")) {
				// TODO
				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(R.id.frame, new HallFragment());
				ft.commit();
			} else {

			}
			super.onPostExecute(result);
		}
	}

	@Override
	public void onPause() {
		getActivity().unregisterReceiver(receiver);
		super.onPause();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {
		case R.id.resultlist:

			break;
		default:
			break;
		}
	}
}
