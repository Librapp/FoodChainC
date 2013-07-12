package lms.foodchainC.widget;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.BillData;
import lms.foodchainC.data.CaseData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class BillAdapter extends BaseAdapter {
	private Context context;
	private List<BillData> lc;

	public BillAdapter(Context context, ListView list, List<BillData> lc) {
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
		View view = convertView;
		ViewHolder bc;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.bill_list_item, null);
			bc = new ViewHolder();
			view.setTag(bc);
		} else
			bc = (ViewHolder) view.getTag();
		final BillData c = lc.get(position);
		bc.text.setText(c.id);
		bc.text2.setText(c.cost + "元");
		bc.text3.setText(c.createTime);
		if (c.state == 1) {
			bc.text4.setText("已付款");
		} else {
			bc.text4.setText("未付款");
		}
		switch (c.type) {
		case CaseData.HERE:
			bc.text1.setText("即食");
			if (!c.seatId.equals("")) {
				bc.text5.setText("座位号" + c.seatId);
			} else if (!c.tableId.equals("")) {
				bc.text5.setText("桌号" + c.tableId);
			} else {
				bc.text5.setText(c.customerName);
			}
			break;
		case CaseData.PACK:
			bc.text1.setText("带走");
			if (!c.seatId.equals("")) {
				bc.text5.setText("座位号" + c.seatId);
			} else if (!c.tableId.equals("")) {
				bc.text5.setText("桌号" + c.tableId);
			} else {
				bc.text5.setText(c.customerName);
			}
			break;
		case CaseData.AWAY:
			bc.text1.setText("外卖");
			bc.text5.setText(c.address + c.customerName);
			break;
		default:
			break;
		}

		return view;
	}
}
