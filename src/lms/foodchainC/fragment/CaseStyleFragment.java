package lms.foodchainC.fragment;

import lms.foodchainC.R;
import lms.foodchainC.dao.Bill_DBHelper;
import lms.foodchainC.dao.Case_DBHelper;
import lms.foodchainC.data.CaseData;
import lms.foodchainC.data.CaseStyleData;
import lms.foodchainC.widget.MenuAdapter;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * 
 * @author 梦思
 * @description 菜品分类模块
 * @createTime 2014/1/20
 */
public class CaseStyleFragment extends ListFragment {
	private CaseStyleData csd;
	private Case_DBHelper cdb;
	private Bill_DBHelper bdb;
	private MenuAdapter ma;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		csd = new CaseStyleData();
		csd.id = getArguments() != null ? getArguments().getInt("styleId") : 0;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.casestyle, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		cdb = new Case_DBHelper(getActivity());
		bdb = new Bill_DBHelper(getActivity());
		if (cdb.getCaseStyleData(csd) && csd.getList().size() > 0) {
			ma = new MenuAdapter(getActivity(), csd.getList());
			setListAdapter(ma);
		}
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		CaseData c = csd.getList().get(position);
		if (c.orderId == 0) {
			c.orderId = 1;
			bdb.insertCase(c);
		} else {
			c.orderId = 0;
			bdb.deleteCase(c);
		}
		ma.notifyDataSetChanged();
	}

	public static CaseStyleFragment newInstance(int id) {
		CaseStyleFragment f = new CaseStyleFragment();
		Bundle args = new Bundle();
		args.putInt("styleId", id);
		f.setArguments(args);
		return f;
	}
}
