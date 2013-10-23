package lms.foodchainC.fragment;

import java.util.ArrayList;

import lms.foodchainC.R;
import lms.foodchainC.dao.Bill_DBHelper;
import lms.foodchainC.data.BillData;
import lms.foodchainC.data.CaseData;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.net.NetUtil;
import lms.foodchainC.widget.BillAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class BillFragment extends Fragment {
	private ListView list;
	private BillAdapter ba;
	private UpdateBillTask updateBillTask;
	private ArrayList<BillData> billList;
	private ArrayList<CaseData> orderList;
	private Bill_DBHelper bdb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.hall, container);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		bdb = new Bill_DBHelper(getActivity());
		refresh();
	}

	private void refresh() {
		if (updateBillTask != null) {
			updateBillTask.cancel(true);
			updateBillTask = null;
		}
		updateBillTask = new UpdateBillTask();
		updateBillTask.execute();
	}

	private class UpdateBillTask extends AsyncTask<Object, String, String> {

		@Override
		protected String doInBackground(Object... params) {

			String result = NetUtil.executePost((Context) params[0],
					(String) params[1],
					RestaurantData.current().device.getLocation());
			billList = new ArrayList<BillData>();
			return result;
		}

		protected void onPostExecute(String result) {
			// TODO
		};

	}

	private class LoadBillTask extends AsyncTask<Object, String, Boolean> {

		@Override
		protected Boolean doInBackground(Object... params) {
			orderList = new ArrayList<CaseData>();
			boolean result = bdb.getOrderList(orderList);
			billList = new ArrayList<BillData>();
			return result;
		}

		protected void onPostExecute(Boolean result) {
			// TODO
		};

	}

}
