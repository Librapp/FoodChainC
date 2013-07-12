package lms.foodchainC.widget;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.EmployeeData;
import android.app.Service;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CookerListAdapter extends BaseAdapter {
	private Context context;
	private List<EmployeeData> lc;
	private View view;
	private EmployeeData c;

	public CookerListAdapter(Context context, List<EmployeeData> lc) {
		this.context = context;
		this.lc = lc;
	}

	@Override
	public int getCount() {
		return lc.size();
	}

	@Override
	public Object getItem(int position) {
		return lc.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		view = inflate.inflate(R.layout.cooker_list_item, null);
		c = lc.get(position);
		TextView name = (TextView) view.findViewById(R.id.cooker_name);
		name.setText(c.name);

		TextView status = (TextView) view.findViewById(R.id.cooker_status);
		status.setText(c.state);

		ImageView pic = (ImageView) view.findViewById(R.id.cooker_icon);
		Drawable d = Drawable.createFromPath(c.headPic);
		if (d != null) {
			pic.setImageDrawable(d);
		}
		return view;
	}

}
