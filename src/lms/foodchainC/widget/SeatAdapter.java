package lms.foodchainC.widget;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.SeatData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SeatAdapter extends BaseAdapter {
	private Context context;
	private List<SeatData> ls;

	public SeatAdapter(Context context, List<SeatData> ls) {
		this.context = context;
		this.ls = ls;
	}

	@Override
	public int getCount() {
		return ls.size();
	}

	@Override
	public Object getItem(int position) {
		return ls.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.seat_list_item, null);
			holder = new ViewHolder();
			holder.id = (TextView) view.findViewById(R.id.seat_id);
			holder.icon = (ImageView) view.findViewById(R.id.seat_icon);
			holder.clean = (ImageView) view.findViewById(R.id.seat_clean);
			holder.order = (ImageView) view.findViewById(R.id.seat_order);
			holder.pay = (ImageView) view.findViewById(R.id.seat_pay);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		SeatData s = ls.get(position);
		switch (s.state) {
		case SeatData.DIRTY:
			holder.clean.setImageResource(R.drawable.seat_dirty);
			holder.order.setImageResource(R.drawable.seat_order_normal);
			holder.pay.setImageResource(R.drawable.seat_pay_normal);
			holder.icon.setImageResource(R.drawable.seat_icon_default);
			holder.id.setVisibility(View.GONE);
			break;
		case SeatData.AVAILIABLE:
			holder.clean.setImageResource(R.drawable.seat_clean);
			holder.order.setImageResource(R.drawable.seat_order_normal);
			holder.pay.setImageResource(R.drawable.seat_pay_normal);
			holder.icon.setImageResource(R.drawable.seat_icon_default);
			holder.id.setVisibility(View.GONE);
			break;
		case SeatData.OCCUPY:
			holder.id.setText(ls.get(position).customerName);
			holder.clean.setImageResource(R.drawable.seat_clean);
			holder.order.setImageResource(R.drawable.seat_order_normal);
			holder.pay.setImageResource(R.drawable.seat_pay_normal);
			// TODO　获取用户头像
			holder.id.setVisibility(View.VISIBLE);
			break;
		case SeatData.HAVING:
			holder.id.setText(ls.get(position).customerName);
			holder.clean.setImageResource(R.drawable.seat_clean);
			holder.order.setImageResource(R.drawable.seat_order_normal);
			holder.pay.setImageResource(R.drawable.seat_pay_normal);
			// TODO　获取用户头像
			holder.id.setVisibility(View.VISIBLE);
			break;
		case SeatData.WAITING:
			holder.id.setText(ls.get(position).customerName);
			holder.clean.setImageResource(R.drawable.seat_clean);
			holder.order.setImageResource(R.drawable.seat_order_pressed);
			holder.pay.setImageResource(R.drawable.seat_pay_normal);
			// TODO　获取用户头像
			holder.id.setVisibility(View.VISIBLE);
			break;
		case SeatData.PAY:
			holder.id.setText(ls.get(position).customerName);
			holder.clean.setImageResource(R.drawable.seat_clean);
			holder.order.setImageResource(R.drawable.seat_order_normal);
			holder.pay.setImageResource(R.drawable.seat_pay_pressed);
			// TODO　获取用户头像

			holder.id.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
		return view;
	}

	class ViewHolder {
		TextView id;
		ImageView icon;
		ImageView clean;
		ImageView order;
		ImageView pay;
	}
}
