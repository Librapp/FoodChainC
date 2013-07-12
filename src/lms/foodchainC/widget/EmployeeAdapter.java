package lms.foodchainC.widget;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.EmployeeData;
import lms.foodchainC.widget.AsyncImageLoader.ImageCallback;
import android.app.Service;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EmployeeAdapter extends BaseAdapter {
	private Context context;
	private List<EmployeeData> lw;
	private View view;
	private AsyncImageLoader asyncImageLoader;
	private EmployeeData employee;
	private Cache vc;

	public EmployeeAdapter(Context context, List<EmployeeData> lw) {
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
		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		view = inflate.inflate(R.layout.employee_list_item, null);
		vc = new Cache(view);
		employee = (EmployeeData) getItem(position);

		vc.getStatus().setText(employee.state);
		vc.getPosition().setText(employee.position);
		vc.getName().setText(employee.name);
		vc.getId().setText(employee.id + "");
		vc.getGender().setText(employee.gender);
		vc.getLevel().setText(employee.level);
		vc.getCometime().setText(employee.comeTime);
		vc.getLeavetime().setText(employee.leaveTime);
		// TODO 头像
		String imageUrl = employee.headPic;

		vc.getIcon().setTag(imageUrl);
		Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {
						ImageView imageViewByTag = (ImageView) view
								.findViewWithTag(imageUrl);
						if (imageViewByTag != null) {
							imageViewByTag.setImageDrawable(imageDrawable);
						}
					}
				});

		if (cachedImage == null) {
			vc.getIcon().setImageResource(R.drawable.seat_icon_default);
		} else {
			vc.getIcon().setImageDrawable(cachedImage);
		}
		return view;
	}

	private class Cache {
		private TextView status;
		private TextView position;
		private TextView name;
		private TextView id;
		private TextView gender;
		private TextView level;
		private TextView cometime;
		private TextView leavetime;
		private ImageView icon;
		private View v;

		public Cache(View v) {
			this.v = v;
		}

		public TextView getStatus() {
			if (status == null) {
				status = (TextView) v.findViewById(R.id.el_status);
			}
			return status;
		}

		public TextView getPosition() {
			if (position == null) {
				position = (TextView) v.findViewById(R.id.el_position);
			}
			return position;
		}

		public TextView getName() {
			if (name == null) {
				name = (TextView) v.findViewById(R.id.el_name);
			}
			return name;
		}

		public TextView getId() {
			if (id == null) {
				id = (TextView) v.findViewById(R.id.el_id);
			}
			return id;
		}

		public TextView getGender() {
			if (gender == null) {
				gender = (TextView) v.findViewById(R.id.el_gender);
			}
			return gender;
		}

		public TextView getLevel() {
			if (level == null) {
				level = (TextView) v.findViewById(R.id.el_level);
			}
			return level;
		}

		public TextView getCometime() {
			if (cometime == null) {
				cometime = (TextView) v.findViewById(R.id.el_cometime);
			}
			return cometime;
		}

		public TextView getLeavetime() {
			if (leavetime == null) {
				leavetime = (TextView) v.findViewById(R.id.el_leavetime);
			}
			return leavetime;
		}

		public ImageView getIcon() {
			if (icon == null) {
				icon = (ImageView) v.findViewById(R.id.el_icon);
			}
			return icon;
		}
	}
}
