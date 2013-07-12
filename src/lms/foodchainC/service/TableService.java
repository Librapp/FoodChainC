package lms.foodchainC.service;

import java.util.List;

import lms.foodchainC.dao.Table_DBHelper;
import lms.foodchainC.data.SeatData;
import lms.foodchainC.data.TableData;
import lms.foodchainC.data.TableStyle;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-15
 * @description 桌位service
 * @changeLog
 */
public class TableService extends BaseService {
	private static Table_DBHelper tdb;
	private static Context context;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		context = this.getApplicationContext();
		tdb = new Table_DBHelper(context);
	}

	/** 创建新餐桌类型 */
	public static boolean createTableStyle(TableStyle ts) {
		return tdb.insertTableStyle(ts);
	}

	/** 修改餐桌类型 */
	public static boolean upgradeTableStyle(TableStyle ts) {
		return tdb.upgradeTableStyle(ts);
	}

	/** 修改餐桌 */
	public static boolean upgradeTable(TableData t) {
		return tdb.upgradeTable(t);
	}

	/** 修改座位 */
	public static boolean upgradeSeat(SeatData s) {
		return tdb.upgradeSeat(s);
	}

	/** 删除餐桌类型 */
	public static boolean deleteTableStyle(TableStyle ts) {
		return tdb.deleteSeatDataByStyle(ts);
	}

	/** 获取餐桌类型列表 */
	public static List<TableStyle> getTableStyles() {
		return tdb.getTableStyleDataList();
	}

	/** 获取餐桌类型详情 */
	public static boolean getTableStyleDetail(TableStyle tableStyle) {
		return tdb.getTableStyleDetail(tableStyle);
	}

	/** 获取餐桌列表 */
	public static List<TableData> getTableList() {
		return tdb.getTableList();
	}
}
