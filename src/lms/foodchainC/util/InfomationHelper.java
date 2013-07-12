package lms.foodchainC.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;

/**
 * 
 * @author 系统信息工具类
 * @date 2011-12-5
 * 
 */
public class InfomationHelper {

	private static ExecutorService executorService = Executors
			.newFixedThreadPool(1);
	public final static String FOLDERNAME = "FCR/caseP";
	public final static String SDCARD_MNT = "/mnt/sdcard";
	public final static String FILE_EXTENTION = ".jpg";
	public final static String SDCARD = "/sdcard";
	public static String SD_PATH = "";

	public static String getFileName() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SS");
		String fileName = format.format(new Timestamp(System
				.currentTimeMillis()));
		return fileName;
	}

	public static String getCamerPath() {
		return Environment.getExternalStorageDirectory() + "/";
	}

	public static String getAbsolutePathFromNoStandardUri(Uri mUri) {
		String filePath = null;

		String mUriString = mUri.toString();
		mUriString = Uri.decode(mUriString);

		String pre1 = "file://" + SDCARD + File.separator;
		String pre2 = "file://" + SDCARD_MNT + File.separator;

		if (mUriString.startsWith(pre1)) {
			filePath = Environment.getExternalStorageDirectory().getPath()
					+ File.separator + mUriString.substring(pre1.length());
		} else if (mUriString.startsWith(pre2)) {
			filePath = Environment.getExternalStorageDirectory().getPath()
					+ File.separator + mUriString.substring(pre2.length());
		}
		return filePath;
	}

	public static boolean checkNetWork(Context context) {
		boolean newWorkOK = false;
		ConnectivityManager connectManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectManager.getActiveNetworkInfo() != null) {
			newWorkOK = true;
		}
		return newWorkOK;
	}

	public static Bitmap getScaleBitmap(Context context, String filePath) {
		BitmapFactory.Options opts = new BitmapFactory.Options();

		opts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, opts);
		opts.inJustDecodeBounds = false;
		int ratio = (int) (opts.outHeight / (float) 200);
		if (ratio <= 0)
			ratio = 1;
		opts.inSampleSize = ratio;
		bitmap = BitmapFactory.decodeFile(filePath, opts);
		return bitmap;
	}

	public static Bitmap getFinalScaleBitmap(Context context, String filePath) {
		BitmapFactory.Options opts = new BitmapFactory.Options();

		opts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, opts);
		opts.inJustDecodeBounds = false;

		int ratio = (int) (opts.outHeight / (float) 200);
		if (ratio <= 0)
			ratio = 1;

		if (FileInfoUtils.getFileSize(filePath) > 300000) {
			opts.inSampleSize = ratio;
		} else {
			opts.inSampleSize = 2;
		}

		bitmap = BitmapFactory.decodeFile(filePath, opts);
		SD_PATH = Environment.getExternalStorageDirectory() + File.separator
				+ FOLDERNAME + File.separator;

		String s = SD_PATH + getFileName() + FILE_EXTENTION;
		savePic2SD(bitmap, s, SD_PATH);

		return bitmap;
	}

	public static void savePic2SD(Bitmap bitmap, String path, String folder) {

		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			File fileDir = new File(folder);
			if (!fileDir.exists()) {
				fileDir.mkdir();
			}
		}

		File file = new File(path);
		try {
			FileOutputStream out = new FileOutputStream(file);

			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void savePic2SDBadge(final String url, final String path,
			final String folder) {

		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					final Drawable drawable = Drawable.createFromStream(
							new URL(url).openStream(), "image.png");

					savePic2SD(drawableToBitmap(drawable), path, folder);

				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});

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

}
