package lms.foodchainC.util;

import java.lang.reflect.Field;
import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.EmotionData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

/**
 * @author 李梦思
 * @createTime 2012-7-8
 * @description 文字转表情
 * @changeLog
 */
public class Txt2Image {

	public static SpannableString txtToImg(String content, Context c) {
		SpannableString ss = new SpannableString(content);
		int starts = 0;
		int end = 0;

		if (content.indexOf("[", starts) != -1
				&& content.indexOf("]", end) != -1) {
			starts = content.indexOf("[", starts);
			end = content.indexOf("]", end);
			String phrase = content.substring(starts, end + 1);
			String imageName = "";
			List<EmotionData> list = MakeEmotionsList.current().getLe();
			for (EmotionData emotion : list) {
				if (emotion.getPhrase().equals(phrase)) {
					imageName = emotion.getImageName();
				}
			}

			try {
				Field f = (Field) R.drawable.class.getDeclaredField(imageName);
				int i = f.getInt(R.drawable.class);
				Drawable drawable = c.getResources().getDrawable(i);
				if (drawable != null) {
					drawable.setBounds(5, 7, drawable.getIntrinsicWidth() + 10,
							drawable.getIntrinsicHeight() + 10);
					ImageSpan span = new ImageSpan(drawable,
							ImageSpan.ALIGN_BASELINE);
					ss.setSpan(span, starts, end + 1,
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {

			}
		}
		return ss;
	}
}
