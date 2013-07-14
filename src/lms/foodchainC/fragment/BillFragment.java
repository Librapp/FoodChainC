package lms.foodchainC.fragment;

import java.util.ArrayList;

import lms.foodchainC.R;
import lms.foodchainC.data.BillData;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.net.JSONRequest;
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
	private UploadBillTask uploadBillTask;
	private Context context;
	private ArrayList<BillData> billList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.hall, container);
		list = (ListView) v.findViewById(R.id.hall_tablelist);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		context = getActivity();
		refresh();
	}

	private void refresh() {
		if (uploadBillTask != null) {
			uploadBillTask.cancel(true);
			uploadBillTask = null;
		}
		uploadBillTask = new UploadBillTask();
		uploadBillTask.execute();
	}

	private class UploadBillTask extends
			AsyncTask<Object, JSONRequest, Boolean> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Object... params) {

			String result = NetUtil.executePost((Context) params[0],
					(String) params[1],
					RestaurantData.current().device.getLocation());
			billList = new ArrayList<BillData>();
			return null;
		}

		protected void onPostExecute(Boolean result) {
			// TODO
		};

	}
}
