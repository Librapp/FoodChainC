package lms.foodchainC.fragment;

import java.util.ArrayList;
import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.dao.Table_DBHelper;
import lms.foodchainC.data.SeatData;
import lms.foodchainC.data.TableData;
import lms.foodchainC.data.TableStyleData;
import lms.foodchainC.widget.TableAdapter;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TableStyleFragment extends ListFragment {
	private TableStyleData tsd;
	private Table_DBHelper tdb;
	private TableAdapter ta;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.tablestyle, null);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		tsd = new TableStyleData();
		tsd.id = getArguments().getString("styleId");
		// tdb = new Table_DBHelper(getActivity());
		// if (tdb.getTableStyleDetail(tsd))
		// if (tsd.getTable().size() > 0) {
		// ta = new TableAdapter(getActivity(), tsd.getTable());
		// setListAdapter(ta);
		// }
		if (tsd.id.equals("A01")) {
			tsd = new TableStyleData("A01", 10, 4);
		} else if (tsd.id.equals("A02")) {
			tsd = new TableStyleData("A02", 8, 6);
		} else if (tsd.id.equals("A03")) {
			tsd = new TableStyleData("A03", 4, 8);
		}
		createTableStyle(tsd);
		ta = new TableAdapter(getActivity(), tsd.getTable());
		setListAdapter(ta);
		super.onActivityCreated(savedInstanceState);
	}

	private void createTableStyle(TableStyleData tsd) {
		List<TableData> lt = new ArrayList<TableData>();
		for (int i = 0; i < tsd.tableCount; i++) {
			TableData td = new TableData(tsd, i);
			List<SeatData> ls = new ArrayList<SeatData>();
			for (int j = 0; j < td.seatCount; j++) {
				SeatData sd = new SeatData(td, j);
				ls.add(sd);
			}
			td.setSeat(ls);
			lt.add(td);
		}
		tsd.setTable(lt);

	}

	public static TableStyleFragment newInstance(String id) {
		TableStyleFragment f = new TableStyleFragment();
		Bundle args = new Bundle();
		args.putString("styleId", id);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

	}
}
