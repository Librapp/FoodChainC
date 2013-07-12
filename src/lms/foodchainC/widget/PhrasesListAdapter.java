package lms.foodchainC.widget;

import java.util.List;

import lms.foodchainC.R;
import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author ����
 * @version 2.0
 * @description ���ö�������
 * @createTime 2012-6-8
 * @changeLog
 */
public class PhrasesListAdapter extends BaseAdapter {

	private List<String> str;
	private Context c;

	public PhrasesListAdapter(Context c, List<String> s) {
		this.c = c;
		this.str = s;
	}

	@Override
	public int getCount() {
		return str.size();
	}

	@Override
	public Object getItem(int position) {
		return str.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflate = (LayoutInflater) c
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		View view = inflate.inflate(R.layout.phrase_list_item, null);
		TextView t = (TextView) view.findViewById(R.id.phrase_list_item_txt);
		t.setText(str.get(position));
		return view;
	}

}
