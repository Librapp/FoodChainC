package lms.foodchainC.widget;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.CustomerData;
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

public class CustomerOrderListAdapter extends BaseAdapter {
	private Context context;
	private List<CustomerData> lw;
	private View view;
	private AsyncImageLoader asyncImageLoader;
	private CustomerData employee;
	private Cache vc;

	public CustomerOrderListAdapter(Context context, List<CustomerData> lw) {
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
		view = inflate.inflate(R.layout.customer_order_list_item, null);
		vc = new Cache(view);
		employee = (CustomerData) getItem(position);

		vc.getId().setText(employee.id + "");
		vc.getLevel().setText(employee.level);
		vc.getCometime().setText(employee.comeTime);
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
		private TextView id;
		private TextView level;
		private TextView cometime;
		private ImageView icon;
		private View v;

		public Cache(View v) {
			this.v = v;
		}

		public TextView getId() {
			if (id == null) {
				id = (TextView) v.findViewById(R.id.el_id);
			}
			return id;
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

		public ImageView getIcon() {
			if (icon == null) {
				icon = (ImageView) v.findViewById(R.id.el_icon);
			}
			return icon;
		}
	}
}
