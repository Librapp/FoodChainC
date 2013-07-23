package lms.foodchainC.fragment;

import lms.foodchainC.R;
import lms.foodchainC.dao.Case_DBHelper;
import lms.foodchainC.data.CaseStyleData;
import lms.foodchainC.widget.CaseAddListener;
import lms.foodchainC.widget.MenuAdapter;
import android.app.Activity;
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
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mListener = (CaseAddListener) activity;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mListener.caseAdd(csd.getList().get(position).id);
	}
}
