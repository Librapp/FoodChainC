package lms.foodchainC.fragment;

import lms.foodchainC.R;
import lms.foodchainC.dao.Case_DBHelper;
import lms.foodchainC.data.CaseStyleData;
import lms.foodchainC.net.JSONParser;
import lms.foodchainC.net.JSONRequest;
import lms.foodchainC.net.NetUtil;
import lms.foodchainC.util.DialogUtil;
import lms.foodchainC.widget.CaseAddListener;
import lms.foodchainC.widget.MenuAdapter;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MenuFragment extends ListFragment {
	private CaseStyleData csd;
	private Case_DBHelper cdb;
	private MenuAdapter ma;
	private GetMenuTask getMenuTask;
	private CaseAddListener mListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		csd = new CaseStyleData();
		csd.id = getArguments().getInt("styleId");
	}

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

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mListener = (CaseAddListener) activity;
	}

	private void getMenu() {
		if (getMenuTask != null) {
			getMenuTask.cancel(true);
			getMenuTask = null;
		}
		getMenuTask = new GetMenuTask();
		getMenuTask.execute(csd.id);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mListener.caseAdd(csd.getList().get(position).id);
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
