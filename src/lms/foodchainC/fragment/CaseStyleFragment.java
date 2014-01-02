package lms.foodchainC.fragment;

import lms.foodchainC.R;
import lms.foodchainC.activity.DetailActivity;
import lms.foodchainC.dao.Case_DBHelper;
import lms.foodchainC.data.CaseStyleData;
import lms.foodchainC.widget.MenuAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class CaseStyleFragment extends ListFragment implements OnClickListener {
	private CaseStyleData csd;
	private Case_DBHelper cdb;
	private MenuAdapter ma;
	private Button edit;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		csd = new CaseStyleData();
		csd.id = getArguments() != null ? getArguments().getInt("styleId") : 0;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menulist, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		cdb = new Case_DBHelper(getActivity());

		edit = new Button(getActivity());
		edit.setText(R.string.edit);
		edit.setOnClickListener(this);
		getListView().addFooterView(edit);
		if (cdb.getCaseStyleData(csd) && csd.getList().size() > 0) {
			ma = new MenuAdapter(getActivity(), csd.getList());
			setListAdapter(ma);
		}
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

	}

	public static CaseStyleFragment newInstance(int id) {
		CaseStyleFragment f = new CaseStyleFragment();
		Bundle args = new Bundle();
		args.putInt("styleId", id);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), DetailActivity.class);
		intent.putExtra("id", csd.id);
		startActivity(intent);
	}

}
