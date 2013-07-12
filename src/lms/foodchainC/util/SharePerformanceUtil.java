package lms.foodchainC.util;

import lms.foodchainC.data.OtherData;
import lms.foodchainC.data.Self;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePerformanceUtil {

	public static void getLInfo(Context context) {
		SharedPreferences spl = context.getSharedPreferences(
				OtherData.LOCALSETTING, Context.MODE_PRIVATE);
		// 自定义
		Self.current().name = spl.getString("name", "名字");
		Self.current().tel = spl.getString("tel", "电话");
		Self.current().passWord = spl.getString("passWord", "******");
		Self.current().intro = spl.getString("intro", "招财进宝");
		Self.current().address = spl.getString("address", "北京");

	}

	public static void saveLInfo(Context context) {
		SharedPreferences spl = context.getSharedPreferences(
				OtherData.LOCALSETTING, Context.MODE_PRIVATE);
		Editor ed = spl.edit();
		ed.putString("name", Self.current().name);
		ed.putString("tel", Self.current().tel);
		ed.putString("passWord", Self.current().passWord);
		ed.putString("intro", Self.current().intro);
		ed.putString("address", Self.current().address);
		ed.commit();
	}

	public static void getNInfo(Context context) {
		SharedPreferences spn = context.getSharedPreferences(
				OtherData.ONLINESETTING, Context.MODE_PRIVATE);
		// 系统分配
		Self.current().id = spn.getString("id", "");
		Self.current().credit = spn.getInt("credit", 0);
		Self.current().point = spn.getInt("point", 0);
	}

	public static void saveNInfo(Context context) {
		SharedPreferences spn = context.getSharedPreferences(
				OtherData.ONLINESETTING, Context.MODE_PRIVATE);
		Editor ed = spn.edit();
		ed.putString("id", Self.current().id);
		ed.putInt("credit", Self.current().credit);
		ed.putInt("point", Self.current().point);
		ed.commit();
	}
}
