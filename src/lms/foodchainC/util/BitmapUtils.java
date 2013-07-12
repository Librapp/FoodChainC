package lms.foodchainC.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-6-1
 * @description Bitmap工具类
 * @changLog
 */
public class BitmapUtils {

	public static byte[] bitmapToBytes(Bitmap bitmap) {
		if (bitmap == null) {
			return null;

		}
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
		return os.toByteArray();

	}

	public static byte[] drawableToBytes(Drawable drawable) {
		if (drawable == null) {
			return null;

		}

		return bitmapToBytes(drawableToBitmap(drawable));

	}

	public static Bitmap rotateBitmap(Bitmap bitmapOrg, int angle) {

		// ��ȡ���ͼƬ�Ŀ�͸�
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();

		// //����Ԥת���ɵ�ͼƬ�Ŀ�Ⱥ͸߶�
		// int newWidth = 200;
		// int newHeight = 200;
		//
		// //���������ʣ��³ߴ��ԭʼ�ߴ�
		// float scaleWidth = ((float) newWidth) / width;
		// float scaleHeight = ((float) newHeight) / height;
		//
		// ��������ͼƬ�õ�matrix����
		Matrix matrix = new Matrix();
		//
		// // ����ͼƬ����
		// matrix.postScale(scaleWidth, scaleHeight);

		// ��תͼƬ ����
		matrix.postRotate(angle);
		// �����µ�ͼƬ
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,
				height, matrix, true);
		return resizedBitmap;
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	public static Bitmap loadImageFromUrl(String url) throws Exception {

		final DefaultHttpClient client = new DefaultHttpClient();
		final HttpGet getRequest = new HttpGet(url);
		HttpResponse response = client.execute(getRequest);
		int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode != HttpStatus.SC_OK) {
			Log.e("PicShow", "Request URL failed, error code =" + statusCode);
		}

		HttpEntity entity = response.getEntity();

		if (entity == null) {
			Log.e("PicShow", "HttpEntity is null");
		}

		InputStream is = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			is = entity.getContent();
			byte[] buf = new byte[1024];
			int readBytes = -1;
			while ((readBytes = is.read(buf)) != -1) {
				baos.write(buf, 0, readBytes);
			}
		} finally {
			if (baos != null) {
				baos.close();
			}
			if (is != null) {
				is.close();
			}
		}
		byte[] imageArray = baos.toByteArray();

		return BitmapFactory.decodeByteArray(imageArray, 0, imageArray.length);
	}

	// ָ����ַ����ͼƬд���ļ�
	public static void loadImageFromUrlAnd2File(String url) throws Exception {

		final DefaultHttpClient client = new DefaultHttpClient();
		final HttpGet getRequest = new HttpGet(url);
		HttpResponse response = client.execute(getRequest);
		int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode != HttpStatus.SC_OK) {
			Log.e("PicShow", "Request URL failed, error code =" + statusCode);
		}

		HttpEntity entity = response.getEntity();

		if (entity == null) {
			Log.e("PicShow", "HttpEntity is null");
		}

		InputStream is = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			is = entity.getContent();
			byte[] buf = new byte[1024];
			int readBytes = -1;
			while ((readBytes = is.read(buf)) != -1) {
				baos.write(buf, 0, readBytes);
			}
		} finally {
			if (baos != null) {
				baos.close();
			}
			if (is != null) {
				is.close();
			}
		}
		byte[] imageArray = baos.toByteArray();

		Log.e("loadImageFromUrlAnd2File", "start saving");

		FileInfoUtils.writeFile(imageArray, "FoodChainLogo", "文件名");

		Log.e("loadImageFromUrlAnd2File", "end saving");
	}

	// sdcard����ͼƬ���ص�imageView
	public static Bitmap getLocalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean saveBitmap(String folderPath, String fileName,
			Bitmap result) {
		FileOutputStream fOut = null;
		String dirPath = Environment.getExternalStorageDirectory()
				+ File.separator + folderPath;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);

		if (!sdCardExist) {
			return false;
		}
		File fileDir = new File(dirPath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		try {
			File f = new File(dirPath, fileName);
			f.createNewFile();
			fOut = new FileOutputStream(f);
			result.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			fOut.flush();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (fOut != null) {
					fOut.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public static void saveDrawable(Drawable drawable, String fileDir,
			String name) {
		String folderPath = Environment.getExternalStorageDirectory()
				+ File.separator + fileDir;
		byte[] buffer = drawableToBytes(drawable);
		FileInfoUtils.writeFile(buffer, folderPath, name);
	}

}
