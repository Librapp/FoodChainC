package lms.foodchainC.fragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import lms.foodchainC.R;
import lms.foodchainC.data.CaseData;
import lms.foodchainC.data.OtherData;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.net.JSONParser;
import lms.foodchainC.net.JSONRequest;
import lms.foodchainC.net.NetUtil;
import lms.foodchainC.service.DlnaService;
import lms.foodchainC.widget.WifiScanAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
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
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * @author 李梦思
 * @version 1.0
 * @description 搜索界面
 * @createTime 2013-9-11
 */
public class SearchFragment extends Fragment implements OnClickListener,
		TextWatcher, OnDismissListener, OnItemClickListener {
	private final int CASE = 1;
	private final int RESTAURANT = 0;
	private int searchType = RESTAURANT;
	private int netType = 0;

	private BroadcastReceiver receiver;
	private ConnectivityManager connectivityManager;
	private WifiManager wifiManager;
	private LinearLayout nearbyLayout;
	private ListView resultList;
	private ListView nearbyList;
	private EditText edit;
	private ImageButton clean;
	private ArrayList<CaseData> caseResult;
	private ArrayList<RestaurantData> resResult;
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
		wifiManager = (WifiManager) getActivity().getSystemService(
				Context.WIFI_SERVICE);
		initView();
		super.onActivityCreated(savedInstanceState);
	}

	private void initView() {
		getView().findViewById(R.id.search_restaurant).setOnClickListener(this);
		getView().findViewById(R.id.search_case).setOnClickListener(this);
		getView().findViewById(R.id.search_btn).setOnClickListener(this);
		clean = (ImageButton) getView().findViewById(R.id.search_clean);
		clean.setOnClickListener(this);
		edit = (EditText) getView().findViewById(R.id.search_edit);
		edit.addTextChangedListener(this);
		nearbyLayout = (LinearLayout) getView().findViewById(
				R.id.current_layout);
		nearbyList = (ListView) getView().findViewById(R.id.nearbylist);
		nearbyList.setOnItemClickListener(this);
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
		default:
			break;
		}
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
		receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getStringExtra("type").equals(
						OtherData.RESTAURANTDEVICETYPE)) {
					String address = intent.getStringExtra("address");
					getLocalResInfoTask = new GetLocalResInfoTask();
					getLocalResInfoTask.execute(address);
				}
			}
		};
		getActivity().registerReceiver(receiver,
				new IntentFilter(DlnaService.NEW_DEVICES_FOUND));
		switch (netType) {
		case ConnectivityManager.TYPE_MOBILE:
			// if (wifiManager.isWifiEnabled()) {
			// nearbyList.setAdapter(new WifiScanAdapter(getActivity(),
			// wifiManager.getScanResults()));
			// nearbyLayout.setVisibility(View.VISIBLE);
			// }
			break;
		default:
			break;
		}
		super.onResume();
	}

	private class GetLocalResInfoTask extends AsyncTask<Object, String, String> {

		@Override
		protected String doInBackground(Object... params) {
			String result = NetUtil.executeGet(getActivity()
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
				// FragmentTransaction ft = getFragmentManager()
				// .beginTransaction();
				// ft.replace(R.id.frame, new HallFragment());
				// ft.commit();
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
	public void onDismiss(DialogInterface dialog) {
		switch (netType) {
		case ConnectivityManager.TYPE_MOBILE:
			if (wifiManager.isWifiEnabled()) {
				nearbyList.setAdapter(new WifiScanAdapter(getActivity(),
						wifiManager.getScanResults()));
				nearbyLayout.setVisibility(View.VISIBLE);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {
		case R.id.nearbylist:
			ScanResult result = (ScanResult) nearbyList.getAdapter().getItem(
					position);
			WifiConfiguration wifiConfig = new WifiConfiguration();
			wifiConfig.SSID = result.SSID;
			try {
				Method connect = WifiManager.class.getMethod("connect",
						WifiConfiguration.class);
				connect.invoke(wifiManager, wifiConfig, null);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case R.id.resultlist:

			break;
		default:
			break;
		}
	}
}
