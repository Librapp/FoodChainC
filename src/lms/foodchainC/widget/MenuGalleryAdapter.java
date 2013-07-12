package lms.foodchainC.widget;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.CaseData;
import lms.foodchainC.data.OtherData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuGalleryAdapter extends BaseAdapter {
	private Context context;
	private List<CaseData> lc;

	public MenuGalleryAdapter(Context context, StepGallery list,
			List<CaseData> lc) {
		this(context, list, lc, false);
	}

	public MenuGalleryAdapter(Context context, StepGallery list,
			List<CaseData> lc, boolean ordered) {
		this.context = context;
		if (lc != null)
			this.lc = lc;
		else
			this.lc = new ArrayList<CaseData>();
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
		View view = convertView;
		ViewHolder viewHolder;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.special_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) view
					.findViewById(R.id.special_case_name);
			viewHolder.price = (TextView) view
					.findViewById(R.id.special_case_price);
			viewHolder.pic = (ImageView) view
					.findViewById(R.id.special_case_pic);
			view.setTag(viewHolder);
		} else
			viewHolder = (ViewHolder) view.getTag();

		CaseData c = (CaseData) getItem(position);
		viewHolder.name.setText(c.name);
		viewHolder.price.setText(c.price + "元");
		String fileDir = OtherData.CASE_PIC;
		String filePath = fileDir + c.name + ".jpg";
		File dir = new File(fileDir);
		if (!dir.exists()) {
			dir.mkdir();
		}
		Drawable d = Drawable.createFromPath(filePath);
		if (d != null) {
			viewHolder.pic.setImageDrawable(d);
		}
		return view;
	}

	static class ViewHolder {
		TextView name;
		TextView price;
		ImageView pic;
	}
}
