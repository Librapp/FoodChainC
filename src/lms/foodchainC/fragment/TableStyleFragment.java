package lms.foodchainC.fragment;

import java.util.ArrayList;
import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.activity.SecondaryActivity;
import lms.foodchainC.dao.Table_DBHelper;
import lms.foodchainC.data.SeatData;
import lms.foodchainC.data.TableData;
import lms.foodchainC.data.TableStyleData;
import lms.foodchainC.widget.TableAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class TableStyleFragment extends ListFragment implements
		OnItemClickListener, OnItemLongClickListener {
	private TableStyleData tsd;
	private Table_DBHelper tdb;
	private TableAdapter ta;
	private SeatData seat;
	private final int SIT = 0, DETAIL = 2;

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

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		switch (seat.state) {
		case SeatData.AVAILIABLE:
			menu.add(0, SIT, 0, "入座");
			break;
		case SeatData.OCCUPY:
			menu.add(0, DETAIL, 2, "查看");
			break;
		default:
			break;
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case SIT:
			// TODO 发送入座请求
			break;
		case DETAIL:
			Intent intent = new Intent(getActivity(), SecondaryActivity.class);
			intent.putExtra("title", R.string.userinfo);
			intent.putExtra("id", seat.customerId);
			getActivity().startActivity(intent);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		seat = (SeatData) arg0.getAdapter().getItem(arg2);
		return false;
	}
}
