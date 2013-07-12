package lms.foodchainC.widget;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.TableData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class TableAdapter extends BaseAdapter {
	private Context context;
	private List<TableData> lt;

	public TableAdapter(Context context, List<TableData> lt) {
		this.context = context;
		this.lt = lt;
	}

	@Override
	public int getCount() {
		return lt.size();
	}

	@Override
	public Object getItem(int position) {
		return lt.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		TableData t = lt.get(position);
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.table_list_item, null);
			holder = new ViewHolder();
			holder.id = (TextView) view.findViewById(R.id.tli_table_id);
			holder.icon = (ImageView) view.findViewById(R.id.tli_table_icon);
			holder.seat = (GridView) view.findViewById(R.id.tli_seat_grid);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.id.setText(t.tableId);
		SeatAdapter sa = new SeatAdapter(context, t.getSeat());
		holder.seat.setAdapter(sa);
		holder.seat.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 跳转到顾客详情

			}
		});
		// TODO 根据状态换图片
		switch (t.state) {
		case TableData.AVAILIABLE:
			holder.icon.setImageResource(R.drawable.icon);
			break;

		default:
			break;
		}
		return view;
	}

	static class ViewHolder {
		TextView id;
		ImageView icon;
		GridView seat;
	}
}
