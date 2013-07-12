package lms.foodchainC.widget;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.CustomerData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomerListAdapter extends BaseAdapter {
	private Context context;
	private List<CustomerData> lw;
	private AsyncImageLoader asyncImageLoader;
	private CustomerData employee;

	public CustomerListAdapter(Context context, List<CustomerData> lw) {
		this.context = context;
		this.lw = lw;
		asyncImageLoader = new AsyncImageLoader();
	}

	@Override
	public int getCount() {
		return lw.size();
	}

	@Override
	public Object getItem(int position) {
		return lw.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Cache viewHolder;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.employee_list_item, null);
			viewHolder = new Cache();
			// viewHolder.state = (TextView) view.findViewById(R.id.el_status);
			viewHolder.name = (TextView) view.findViewById(R.id.el_name);
			// viewHolder.id = (TextView) view.findViewById(R.id.el_id);
			// viewHolder.gender = (TextView) view.findViewById(R.id.el_gender);
			// viewHolder.level = (TextView) view.findViewById(R.id.el_level);
			// viewHolder.cometime = (TextView) view
			// .findViewById(R.id.el_cometime);
			// viewHolder.leavetime = (TextView) view
			// .findViewById(R.id.el_leavetime);
			// viewHolder.icon = (ImageView) view.findViewById(R.id.el_icon);
			view.setTag(viewHolder);
		} else
			viewHolder = (Cache) view.getTag();
		employee = (CustomerData) getItem(position);

		// viewHolder.state.setText(employee.status);
		viewHolder.name.setText(employee.name);
		// viewHolder.id.setText(employee.id + "");
		// viewHolder.gender.setText(employee.gender);
		// viewHolder.level.setText(employee.level);
		// viewHolder.cometime.setText(employee.comeTime);
		// viewHolder.leavetime.setText(employee.leaveTime);

		// Drawable d = Drawable.createFromPath(employee.headPic);
		// if (d != null) {
		// viewHolder.icon.setImageDrawable(d);
		// }
		return view;
	}

	private class Cache {
		public TextView state;
		public TextView name;
		public TextView id;
		public TextView gender;
		public TextView level;
		public TextView cometime;
		public TextView leavetime;
		public ImageView icon;
	}
}
