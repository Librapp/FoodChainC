package lms.foodchainC.widget;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.CaseData;
import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class BillDetailAdapter extends BaseAdapter {
	private Context context;
	private List<CaseData> lc;
	private View view;
	private CaseData c;

	public BillDetailAdapter(Context context, ListView list, List<CaseData> lc) {
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
		view = inflate.inflate(R.layout.billdetail_list_item, null);
		c = lc.get(position);
		TextView name = (TextView) view.findViewById(R.id.name);
		name.setText(c.name);

		TextView price = (TextView) view.findViewById(R.id.price);
		price.setText(c.price + "元");

		TextView orderTime = (TextView) view.findViewById(R.id.createtime);
		orderTime.setText(c.orderTime);

		ImageView status = (ImageView) view.findViewById(R.id.state);
		switch (c.state) {
		case CaseData.AVILIABLE:
			status.setImageResource(R.drawable.case_aviliable);
			break;
		case CaseData.UNAVILIABLE:
			status.setImageResource(R.drawable.case_soldout);
			break;
		default:
			break;
		}

		TextView type = (TextView) view.findViewById(R.id.type);
		switch (c.type) {
		case CaseData.HERE:
			type.setText("即食");
			break;
		case CaseData.PACK:
			type.setText("带走");
			break;
		case CaseData.AWAY:
			type.setText("外卖");
			break;
		default:
			break;
		}

		return view;
	}

}
