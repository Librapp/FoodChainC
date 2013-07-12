package lms.foodchainC.widget;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.CommentData;
import lms.foodchainC.widget.AsyncImageLoader.ImageCallback;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatListAdapter extends BaseAdapter {

	private List<CommentData> list;
	private Context context;
	private View view;
	private AsyncImageLoader asyncImageLoader;

	public ChatListAdapter(Context context, List<CommentData> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public CommentData getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatListViewCache cache;
		CommentData c = list.get(position);
		if (convertView == null) {
			asyncImageLoader = new AsyncImageLoader();
			cache = new ChatListViewCache();
			LayoutInflater inflater = LayoutInflater.from(context);
			view = inflater.inflate(R.layout.chat_list_item, null);
			cache.name = (TextView) view.findViewById(R.id.chat_list_name);
			cache.name.setText(c.name);
			cache.icon = (ImageView) view.findViewById(R.id.chat_list_icon);
			// TODO 加载头像图片
			String imageUrl = c.icon;

			cache.icon.setTag(imageUrl);
			Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
					new ImageCallback() {
						public void imageLoaded(Drawable imageDrawable,
								String imageUrl) {
							ImageView imageViewByTag = (ImageView) view
									.findViewWithTag(imageUrl);
							if (imageViewByTag != null) {
								imageViewByTag.setImageDrawable(imageDrawable);
							}
						}
					});

			if (cachedImage == null) {
				cache.icon.setImageResource(R.drawable.seat_icon_default);
			} else {
				cache.icon.setImageDrawable(cachedImage);
			}
			cache.time = (TextView) view.findViewById(R.id.chat_list_time);
			cache.time.setText(c.time);
			cache.txt = (TextView) view.findViewById(R.id.chat_list_txt);
			cache.pic = (ImageView) view.findViewById(R.id.chat_list_pic);
			cache.voice = (ImageButton) view.findViewById(R.id.chat_list_voice);
			view.setTag(cache);
		} else {
			cache = (ChatListViewCache) convertView.getTag();
		}

		switch (c.style) {
		case CommentData.TXT:
			cache.txt.setVisibility(View.VISIBLE);
			cache.pic.setVisibility(View.GONE);
			cache.voice.setVisibility(View.GONE);
			cache.txt.setText(c.txt);
			break;
		case CommentData.PIC:
			cache.txt.setVisibility(View.GONE);
			cache.pic.setVisibility(View.VISIBLE);
			cache.voice.setVisibility(View.GONE);
			// TODO 加载图片
			break;
		case CommentData.VOICE:
			cache.txt.setVisibility(View.GONE);
			cache.pic.setVisibility(View.GONE);
			cache.voice.setVisibility(View.VISIBLE);
			// TODO 加载音频
			break;
		default:
			break;
		}
		return convertView;
	}

	private class ChatListViewCache {
		protected TextView txt;
		protected TextView name;
		protected TextView time;
		protected ImageView icon;
		protected ImageView pic;
		protected ImageButton voice;

	}
}
