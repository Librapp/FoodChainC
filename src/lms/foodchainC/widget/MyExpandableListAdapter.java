package lms.foodchainC.widget;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.CaseData;
import lms.foodchainC.data.CaseStyleData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author 梦思
 * @description 可展开ListView适配器
 * @createTime 2014/01/07
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
	private Context context;
	private List<CaseStyleData> list;

	public MyExpandableListAdapter(Context context, List<CaseStyleData> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		CaseData c = getChild(groupPosition, childPosition);
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.menu_list_item, null);
			holder = new ViewHolder();
			holder.text = (TextView) view.findViewById(R.id.case_name);
			holder.text1 = (TextView) view.findViewById(R.id.case_price);
			holder.text2 = (TextView) view.findViewById(R.id.case_status);
			holder.pic = (ImageView) view.findViewById(R.id.case_pic);
			view.setTag(holder);
		} else
			holder = (ViewHolder) view.getTag();
		holder.text1.setText(c.price + "元");
		holder.text.setText(c.name);
		Drawable d = Drawable.createFromPath(c.picPath);
		if (d != null) {
			holder.pic.setImageDrawable(d);
		}
		return view;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TextView text = new TextView(context);
		text.setText(getGroup(groupPosition).name);
		text.setTextAppearance(context, R.style.titletext);
		return text;
	}

	@Override
	public int getGroupCount() {
		return list.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return getGroup(groupPosition).getList().size();
	}

	@Override
	public CaseStyleData getGroup(int groupPosition) {
		return list.get(groupPosition);
	}

	@Override
	public CaseData getChild(int groupPosition, int childPosition) {
		return getGroup(groupPosition).getList().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
}
