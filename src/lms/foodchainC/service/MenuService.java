package lms.foodchainC.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import lms.foodchainC.dao.Case_DBHelper;
import lms.foodchainC.data.CaseData;
import lms.foodchainC.data.CaseStyleData;
import lms.foodchainC.data.CaseTimeData;
import lms.foodchainC.data.SystemData;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-15
 * @description 菜单service
 * @changeLog
 */
public class MenuService extends BaseService {
	private static Case_DBHelper am;
	private static Context context;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		context = this.getApplicationContext();
		am = new Case_DBHelper(context);
		whatDay();
	}

	private void whatDay() {
		SimpleDateFormat sdf1 = new SimpleDateFormat("E", Locale.CHINA);
		if (sdf1.format(new Date()).equals("周一")
				|| sdf1.format(new Date()).equals("星期一")) {
			SystemData.weekday = 0;
		} else if (sdf1.format(new Date()).equals("周二")
				|| sdf1.format(new Date()).equals("星期二")) {
			SystemData.weekday = 1;
		} else if (sdf1.format(new Date()).equals("周三")
				|| sdf1.format(new Date()).equals("星期三")) {
			SystemData.weekday = 2;
		} else if (sdf1.format(new Date()).equals("周四")
				|| sdf1.format(new Date()).equals("星期四")) {
			SystemData.weekday = 3;
		} else if (sdf1.format(new Date()).equals("周五")
				|| sdf1.format(new Date()).equals("星期五")) {
			SystemData.weekday = 4;
		} else if (sdf1.format(new Date()).equals("周六")
				|| sdf1.format(new Date()).equals("星期六")) {
			SystemData.weekday = 5;
		} else if (sdf1.format(new Date()).equals("周日")
				|| sdf1.format(new Date()).equals("星期日")) {
			SystemData.weekday = 6;
		}
		sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		String[] ls = sdf1.format(new Date()).split("-");
		SystemData.date = ls[0] + "\n" + ls[1] + "-" + ls[2];
	}

	/** 获取星期菜单 */
	public static ArrayList<CaseTimeData> getMenu(int whatDayOn) {
		return am.getMenu(whatDayOn);
	}

	/** 获取类型列表 */
	public static ArrayList<CaseStyleData> getStyle() {
		return am.getStyle();
	}

	/** 获取类型详情 */
	public static boolean getStyleDetail(CaseStyleData csd) {
		return am.getCaseStyleData(csd);
	}

	/** 按名字获取类型详情 */
	public static boolean getStyleDetailByName(CaseStyleData csd) {
		return am.getCaseStyleDataByName(csd);
	}

	/** 获取时间段详情 */
	public static boolean getTimeDetail(CaseTimeData ctd) {
		return am.getCaseTimeData(ctd);
	}

	/** 按名称获取时间段详情 */
	public static boolean getTimeDetailByName(CaseTimeData ctd) {
		return am.getCaseTimeDataByName(ctd);
	}

	/** 修改星期菜 */
	public static boolean upgradeMenuCase(CaseData c) {
		return am.upgradeMenuCase(c);
	}

	/** 修改全部 */
	public static boolean upgradeCase(CaseData c) {
		return am.upgradeCase(c);
	}

	/** 删除已有菜 */
	public static boolean deleteCase(CaseData c) {
		return am.deleteCase(c);
	}

	/** 删除星期菜 */
	public static boolean deleteMenuCase(CaseData c) {
		return am.deleteMenuCase(c);
	}

	/** 删除时间段 */
	public static boolean deleteTime(CaseTimeData c) {
		return am.deleteCaseTime(c);
	}

	/** 删除类型 */
	public static boolean deleteStyle(CaseStyleData c) {
		return am.deleteCaseStyle(c);
	}

	/** 创建星期菜 */
	public static boolean createMenuCase(CaseData c) {
		return am.createMenuCase(c);
	}

	/** 创建新菜 */
	public static boolean createCase(CaseData c) {
		if (am.createCase(c))
			return am.getCaseDetail(c);
		else
			return false;
	}

	/** 创建新类型 */
	public static boolean createStyle(CaseStyleData c) {
		if (am.createCaseStyle(c))
			return am.getCaseStyleDataByName(c);
		else
			return false;
	}

	/** 创建新时间段 */
	public static boolean createTime(CaseTimeData c) {
		if (am.createCaseTime(c))
			return am.getCaseTimeDataByName(c);
		else
			return false;
	}

	/** 获取详情 */
	public static boolean getCaseDetail(CaseData c) {
		return am.getCaseDetail(c);
	}

	/** 获取菜单菜详情 */
	public static boolean getMenuCase(CaseData c) {
		return am.getMenuCase(c);
	}

}
