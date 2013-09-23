package lms.foodchainC.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

/**
 * 
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-6-1
 * @description 图片工具类
 * @changLog
 */
public class PicUtils {

	private Context c;
	private Activity a;
	public static final int PHOTO = 1;
	public static final int CAMERA = 2;
	public static final int PHOTORESULT = 3;

	public static final int PIC_SIZE_LIMITE_W = 150;
	public static final int PIC_SIZE_LIMITE_H = 150;
	public static final String IMAGE_UNSPECIFIED = "image/*";

	public PicUtils(Context c, Activity a) {
		this.c = c;
		this.a = a;
	}

	public Bitmap loadImgThumbnail(String imgName, int kind) {
		Bitmap bitmap = null;

		String[] proj = { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DISPLAY_NAME };

		Cursor cursor = a.managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj,
				MediaStore.Images.Media.DISPLAY_NAME + "='" + imgName + "'",
				null, null);

		if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
			ContentResolver crThumb = c.getContentResolver();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 1;
			bitmap = MediaStore.Images.Thumbnails.getThumbnail(crThumb,
					cursor.getInt(0), kind, options);
		}
		return bitmap;
	}

}
