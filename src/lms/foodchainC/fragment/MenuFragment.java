package lms.foodchainC.fragment;

import lms.foodchainC.R;
import lms.foodchainC.dao.Case_DBHelper;
import lms.foodchainC.data.CaseStyleData;
import lms.foodchainC.net.JSONParser;
import lms.foodchainC.net.JSONRequest;
import lms.foodchainC.net.NetUtil;
import lms.foodchainC.util.DialogUtil;
import lms.foodchainC.widget.MenuAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MenuFragment extends ListFragment {
	private ListView list;
	private CaseStyleData csd;
	private Case_DBHelper cdb;
	private MenuAdapter ma;
	private GetMenuTask getMenuTask;

	private int styleId;

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		styleId = getArguments() != null ? getArguments().getInt("styleId") : 0;
		csd = new CaseStyleData();
		csd.id = styleId;
	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.menulist, container, false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		cdb = new Case_DBHelper(getActivity());
		if (cdb.getCaseStyleData(csd)) {
			ma = new MenuAdapter(getActivity(), csd.getList());
			setListAdapter(ma);
		} else {
			getMenu();
		}
	}

	private void getMenu() {
		if (getMenuTask != null) {
			getMenuTask.cancel(true);
			getMenuTask = null;
		}
		getMenuTask = new GetMenuTask();
		getMenuTask.execute(styleId);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

	}

	private class GetMenuTask extends AsyncTask<Integer, Integer, String> {

		@Override
		protected String doInBackground(Integer... params) {
			String result = NetUtil.executePost(getActivity(),
					JSONRequest.menuRequest(params[0]), NetUtil.LOCALHOST);
			String msg = JSONParser.caseStyleDataParse(result, csd);
			return msg;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("")) {

			} else
				DialogUtil.alertToast(getActivity(), result);
			super.onPostExecute(result);
		}

	}
}
