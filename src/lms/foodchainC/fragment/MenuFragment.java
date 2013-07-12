package lms.foodchainC.fragment;

import java.util.ArrayList;

import lms.foodchainC.R;
import lms.foodchainC.data.CaseTimeData;
import lms.foodchainC.data.TableData;
import lms.foodchainC.net.JSONRequest;
import lms.foodchainC.netUtil.NetUtil;
import lms.foodchainC.widget.TableAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MenuFragment extends Fragment {
	private ListView list;
	private TableAdapter ta;
	private GetMenuTask getMenuTask;
	private Context context;
	private ArrayList<CaseTimeData> tableList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.menu, container);
		list = (ListView) v.findViewById(R.id.hall_tablelist);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		context = getActivity();
		refresh();
	}

	private void refresh() {
		if (getMenuTask != null) {
			getMenuTask.cancel(true);
			getMenuTask = null;
		}
		getMenuTask = new GetMenuTask();
		getMenuTask.execute();
	}

	private class GetMenuTask extends
			AsyncTask<Object, JSONRequest, ArrayList<TableData>> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected ArrayList<TableData> doInBackground(Object... params) {

			String result = NetUtil.executePost((Context) params[0],
					(String) params[1], NetUtil.LOCALHOST);
			tableList = new ArrayList<CaseTimeData>();
			return null;
		}

		protected void onPostExecute(ArrayList<CaseTimeData> result) {
			list.setAdapter(ta);
		};

	}
}
