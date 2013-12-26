package lms.foodchainC.fragment;

import lms.foodchainC.R;
import lms.foodchainC.activity.DetailActivity;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author 李梦思
 * @version 1.0
 * @description 搜索界面
 * @createTime 2013-9-11
 */
public class SearchFragment extends Fragment implements OnClickListener,
		TextWatcher, OnItemClickListener {
	private final int CASE = 1, RESTAURANT = 0;
	private int searchType = RESTAURANT, netType = 0;

	private BroadcastReceiver receiver;
	private ConnectivityManager connectivityManager;
	private ListView resultList;
	private RelativeLayout currentRes;
	private TextView name;
	private EditText edit;
	private ImageButton clean;
	// private ArrayList<CaseData> caseResult;
	// private ArrayList<RestaurantData> resResult;
	private GetLocalResInfoTask getLocalResInfoTask;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.search, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		connectivityManager = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		initView();
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getStringExtra("type").equals(
						OtherData.RESTAURANTDEVICETYPE)) {
					RestaurantData.local().name = intent.getStringExtra("name");
					RestaurantData.local().localUrl = intent
							.getStringExtra("address");
					getLocalResDetail();
					name.setText(RestaurantData.local().name);
					currentRes.setVisibility(View.VISIBLE);
				}
			}
		};
		super.onActivityCreated(savedInstanceState);
	}

	private void initView() {
		View v = getView();
		v.findViewById(R.id.search_btn).setOnClickListener(this);
		v.findViewById(R.id.change).setOnClickListener(this);
		clean = (ImageButton) v.findViewById(R.id.clear);
		clean.setOnClickListener(this);
		edit = (EditText) v.findViewById(R.id.edit);
		edit.addTextChangedListener(this);
		currentRes = (RelativeLayout) v.findViewById(R.id.current_res);
		currentRes.setOnClickListener(this);
		name = (TextView) v.findViewById(R.id.name);
		resultList = (ListView) v.findViewById(R.id.result);
		resultList.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_btn:
			search();
			break;
		case R.id.clear:
			edit.setText("");
			break;
		case R.id.current_res:
			getLocalResDetail();
			break;
		case R.id.change:
			// TODO
			break;
		default:
			break;
		}
	}

	/** 获取局域网内餐厅信息 */
	private void getLocalResDetail() {
		if (getLocalResInfoTask != null) {
			getLocalResInfoTask.cancel(true);
			getLocalResInfoTask = null;
		}
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
		if (s.length() > 0) {
			clean.setVisibility(View.VISIBLE);
		} else {
			clean.setVisibility(View.GONE);
		}
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
				Intent i = new Intent(getActivity(), DetailActivity.class);
				i.putExtra("isLocal", true);
				getActivity().startActivity(i);
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
		// TODO
	}
}
