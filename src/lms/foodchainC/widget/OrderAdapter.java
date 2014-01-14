package lms.foodchainC.widget;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.CaseData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderAdapter extends BaseAdapter {
	private Context context;
	private List<CaseData> lc;

	public OrderAdapter(Context context, List<CaseData> lc) {
		this.context = context;
		this.lc = lc;
	}

	@Override
	public int getCount() {
		return lc.size();
	}

	@Override
	public CaseData getItem(int position) {
		return lc.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		final CaseData c = getItem(position);
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.order_list_item, null);
			holder = new ViewHolder();
			holder.text = (TextView) view.findViewById(R.id.case_name);
			holder.text1 = (TextView) view.findViewById(R.id.case_price);
			holder.text2 = (TextView) view.findViewById(R.id.count);
			holder.pic = (ImageView) view.findViewById(R.id.case_pic);
			holder.btn = (ImageButton) view.findViewById(R.id.plus);
			holder.btn1 = (ImageButton) view.findViewById(R.id.minus);
			view.setTag(holder);
		} else
			holder = (ViewHolder) view.getTag();
		holder.text1.setText(c.price + "元");
		holder.text.setText(c.name);
		holder.text2.setText(c.count);
		holder.btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				c.count++;
				holder.text2.setText(c.count);
				notifyDataSetChanged();
			}
		});

		holder.btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				c.count--;
				holder.text2.setText(c.count);
				notifyDataSetChanged();
			}
		});

		Drawable d = Drawable.createFromPath(c.picPath);
		if (d != null) {
			holder.pic.setImageDrawable(d);
		}
		return view;
	}
}
