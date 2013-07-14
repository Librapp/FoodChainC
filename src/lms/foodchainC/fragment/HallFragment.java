package lms.foodchainC.fragment;

import java.util.ArrayList;

import lms.foodchainC.R;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.data.TableData;
import lms.foodchainC.net.JSONRequest;
import lms.foodchainC.net.NetUtil;
import lms.foodchainC.widget.TableAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class HallFragment extends Fragment {
	private ListView list;
	private TableAdapter ta;
	private GetHallInfoTask getHallInfoTask;
	private Context context;
	private ArrayList<TableData> tableList;

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
		if (getHallInfoTask != null) {
			getHallInfoTask.cancel(true);
			getHallInfoTask = null;
		}
		getHallInfoTask = new GetHallInfoTask();
		getHallInfoTask.execute();
	}

	private class GetHallInfoTask extends
			AsyncTask<Object, JSONRequest, ArrayList<TableData>> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected ArrayList<TableData> doInBackground(Object... params) {

			String result = NetUtil.executePost((Context) params[0],
					(String) params[1],
					RestaurantData.current().device.getLocation());
			tableList = new ArrayList<TableData>();
			return null;
		}

		protected void onPostExecute(ArrayList<TableData> result) {
			// TODO
			ta = new TableAdapter(context, result);
			list.setAdapter(ta);
		};

	}
}
