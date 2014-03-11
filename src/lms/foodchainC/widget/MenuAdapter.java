package lms.foodchainC.widget;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.CaseData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {
	private Context context;
	private List<CaseData> lc;

	public MenuAdapter(Context context, List<CaseData> lc) {
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
		ViewHolder holder;
		CaseData c = getItem(position);
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.menu_list_item, null);
			holder = new ViewHolder();
			holder.text = (TextView) view.findViewById(R.id.case_name);
			holder.text1 = (TextView) view.findViewById(R.id.case_price);
			holder.text2 = (TextView) view.findViewById(R.id.case_status);
			holder.text3 = (TextView) view.findViewById(R.id.case_count);
			holder.pic = (ImageView) view.findViewById(R.id.case_pic);
			view.setTag(holder);
		} else
			holder = (ViewHolder) view.getTag();
		holder.text1.setText(c.price + "元");
		holder.text.setText(c.name);
		holder.text3.setText("已点" + c.count + "份");

		// if (c.orderId != 0) {
		// // TODO 已点
		// view.setBackgroundColor(context.getResources().getColor(
		// R.color.com_sina_weibo_sdk_blue));
		// } else {
		// // TODO 未点
		// view.setBackgroundColor(context.getResources().getColor(
		// R.color.white));
		// }
		// switch (c.state) {
		// case CaseData.AVILIABLE:
		// holder.pic1.setImageResource(R.drawable.case_aviliable);
		// break;
		// case CaseData.UNAVILIABLE:
		// holder.pic1.setImageResource(R.drawable.case_soldout);
		// break;
		// default:
		// break;
		// }

		Drawable d = Drawable.createFromPath(c.picPath);
		if (d != null) {
			holder.pic.setImageDrawable(d);
		}
		return view;
	}
}
