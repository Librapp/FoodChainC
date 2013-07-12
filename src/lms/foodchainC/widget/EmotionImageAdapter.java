package lms.foodchainC.widget;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.EmotionData;
import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * @author ����
 * @version 2.0
 * @description ��������
 * @createTime 2012-6-8
 * @changeLog
 */
public class EmotionImageAdapter extends BaseAdapter {

	private List<EmotionData> ids;
	private Context c;

	public EmotionImageAdapter(Context c, List<EmotionData> ids) {
		this.ids = ids;
		this.c = c;
	}

	@Override
	public int getCount() {
		return ids.size();
	}

	@Override
	public Object getItem(int arg0) {
		return ids.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater li = (LayoutInflater) c
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		View v = li.inflate(R.layout.emotiongrid_item, null);

		ImageView img = (ImageView) v.findViewById(R.id.emotion_grid_img);
		img.setImageResource(ids.get(position).getId());

		return v;
	}

}
