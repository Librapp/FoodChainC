package lms.foodchainC.widget;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.dao.Bill_DBHelper;
import lms.foodchainC.data.CaseData;
import lms.foodchainC.data.CaseStyleData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
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
	private Bill_DBHelper bdb;

	public MyExpandableListAdapter(Context context, List<CaseStyleData> list) {
		this.context = context;
		this.list = list;
		bdb = new Bill_DBHelper(context);
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
			holder.btn = (ImageButton) view.findViewById(R.id.plus);
			holder.btn1 = (ImageButton) view.findViewById(R.id.minus);
			holder.text3 = (TextView) view.findViewById(R.id.count);
			view.setTag(holder);
		} else
			holder = (ViewHolder) view.getTag();
		holder.text1.setText(c.price + "元");
		holder.text.setText(c.name);
		holder.btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 把订单数据存到数据库

			}
		});
		holder.btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 从订单数据库中删除

			}
		});
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
		text.setTextAppearance(context, R.style.titletext);
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 40);
		text.setLayoutParams(lp);
		// Center the text vertically
		text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		// Set the text starting position
		text.setPadding(36, 0, 0, 0);

		text.setText(getGroup(groupPosition).name);
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
