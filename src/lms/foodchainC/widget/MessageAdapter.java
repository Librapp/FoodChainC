package lms.foodchainC.widget;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.EmotionData;
import lms.foodchainC.data.MessageData;
import lms.foodchainC.data.UserData;
import lms.foodchainC.util.MakeEmotionsList;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author 李梦思
 * @version 1.0
 * @description 消息适配
 * @createTime 2012-10-24
 * @changeLog
 */
public class MessageAdapter extends BaseAdapter {

	private Context c;
	private List<MessageData> lcd;

	public MessageAdapter(Context c, List<MessageData> lcd) {
		this.c = c;
		this.lcd = lcd;
	}

	@Override
	public int getCount() {
		return lcd.size();
	}

	@Override
	public MessageData getItem(int position) {
		return lcd.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		ViewHolder holder;
		if (v == null) {
			v = LayoutInflater.from(c)
					.inflate(R.layout.message_list_item, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) v.findViewById(R.id.ml_user_pic);
			holder.name = (TextView) v.findViewById(R.id.ml_user_name);
			holder.content = (TextView) v.findViewById(R.id.ml_content);
			holder.time = (TextView) v.findViewById(R.id.ml_sendtime);
			holder.direction = (TextView) v.findViewById(R.id.ml_direction);
			holder.pic = (ImageView) v.findViewById(R.id.ml_img);
			holder.voice = (ImageButton) v.findViewById(R.id.ml_voice);
			v.setTag(holder);
		} else
			holder = (ViewHolder) v.getTag();

		final MessageData cd = getItem(position);

		holder.icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (cd.direction == 1)
					UserData.current().id = cd.sId;
				else
					UserData.current().id = cd.rId;
				// TODO 用户详情
			}
		});
		holder.name.setText(cd.userName);
		holder.content.setText(cd.content);
		text2Emotion(holder.content);
		holder.time.setText(cd.time);
		if (cd.direction == 0)
			holder.direction.setText("From");
		else
			holder.direction.setText("To");
		return v;
	}

	private void text2Emotion(TextView txt) {
		String text = txt.getText().toString();
		SpannableString spannable = new SpannableString(text);
		int start = 0;
		int t = 0;
		ImageSpan span;
		Drawable drawable;
		List<EmotionData> le = MakeEmotionsList.current().getLe();

		for (int i = 0; i < le.size(); i++) {

			int l = le.get(i).getPhrase().length();
			for (start = 0; (start + l) <= text.length(); start += l) {

				t = text.indexOf(le.get(i).getPhrase(), start);
				if (t != -1) {

					drawable = c.getResources().getDrawable(le.get(i).getId());
					drawable.setBounds(5, 5, drawable.getIntrinsicWidth(),
							drawable.getIntrinsicHeight());
					span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);

					spannable.setSpan(span, t, t + l,
							Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
				}
			}
		}
		txt.setText(spannable);
	}

	private class ViewHolder {
		ImageView icon;
		TextView name;
		TextView content;
		TextView time;
		TextView direction;
		ImageView pic;
		ImageButton voice;
	}
}
