package lms.foodchainC.dao;

import java.util.ArrayList;
import java.util.List;

import lms.foodchainC.data.SeatData;
import lms.foodchainC.data.TableData;
import lms.foodchainC.data.TableStyleData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author 李梦思
 * @description 桌椅数据库辅助工具类
 * @createTime 2013-2-20
 */
public class Table_DBHelper extends Base_DBHelper {

	private static int VERSION = 1;
	private final String TABLESTYLEDATA = "tableStyleData";
	private final String TABLEDATA = "tableData";
	private final String SEATDATA = "seatData";

	public Table_DBHelper(Context context) {
		super(context, "fcc_table.db", null, VERSION);
		// writeThread.start();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		createTable();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.setVersion(newVersion);
	}

	/** 生成表 */
	private void createTable() {
		if (db == null) {
			db = getWritableDatabase();
		}
		db.execSQL(createTableDataTable());
		db.execSQL(createTableStyleDataTable());
		db.execSQL(createSeatDataTable());
	}

	/** 生成餐桌类型表 */
	private String createTableStyleDataTable() {
		return CREATE + TABLESTYLEDATA + " (" + "styleId varchar,"
				+ "restaurantId varchar," + "seatCount integer,"
				+ "tableCount integer" + ")";
	}

	/** 生成餐桌表 */
	private String createTableDataTable() {
		return CREATE + TABLEDATA + " (" + "tableId varchar"
				+ ",styleId varchar" + ",restaurantId varchar"
				+ ",state integer" + ",seatCount integer"
				+ ",customerId integer" + ",customerName varchar"
				+ ",icon varchar" + ",bookTime varchar" + ")";
	}

	/** 生成座位表 */
	private String createSeatDataTable() {
		return CREATE + SEATDATA + " (" + "seatId varchar" + ",tableId varchar"
				+ ",styleId varchar" + ",restaurantId varchar"
				+ ",state integer" + ",customerId integer"
				+ ",customerName varchar" + ",icon varchar"
				+ ",bookTime varchar" + ")";
	}

	// ------------------------------------------------------ 插入一组对象

	// ------------------------------------------------------ 获取一个对象
	/** 获取餐桌详情 */
	public boolean getTableDataDetail(TableData table) {
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			selectArgs = new String[] { table.id };
			cursor = db.query(TABLESTYLEDATA, null, "tableId=?", selectArgs,
					null, null, null);
			if (cursor != null && cursor.moveToNext()) {
				table = new TableData(cursor.getString(cursor
						.getColumnIndex("tableId")), cursor.getString(cursor
						.getColumnIndex("styleId")), cursor.getInt(cursor
						.getColumnIndex("seatCount")), cursor.getInt(cursor
						.getColumnIndex("state")), cursor.getInt(cursor
						.getColumnIndex("customerId")), cursor.getString(cursor
						.getColumnIndex("customerName")),
						cursor.getString(cursor.getColumnIndex("bookTime")));
				getSeatDataByTable(table);
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	/** 获取座位详情 */
	public boolean getSeatDetail(SeatData seat) {
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			selectArgs = new String[] { seat.seatId };
			cursor = db.query(SEATDATA, null, "seatId=? ", selectArgs, null,
					null, null);

			if (cursor != null) {
				while (cursor.moveToNext()) {
					seat = new SeatData(cursor.getString(cursor
							.getColumnIndex("seatId")), cursor.getString(cursor
							.getColumnIndex("tableId")),
							cursor.getString(cursor.getColumnIndex("styleId")),
							cursor.getInt(cursor.getColumnIndex("state")),
							cursor.getInt(cursor.getColumnIndex("customerId")),
							cursor.getString(cursor
									.getColumnIndex("customerName")),
							cursor.getString(cursor.getColumnIndex("bookTime")));
				}
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	// ------------------------------------------------------------- 获取一组对象
	/** 获取餐桌类型列表 */
	public List<TableStyleData> getTableStyleDataList() {
		Cursor cursor = null;
		List<TableStyleData> list = new ArrayList<TableStyleData>();
		try {
			db = getReadableDatabase();
			cursor = db.query(TABLESTYLEDATA, null, null, null, null, null,
					null);

			if (cursor != null) {
				while (cursor.moveToNext()) {
					TableStyleData tableStyle = new TableStyleData(
							cursor.getString(cursor.getColumnIndex("styleId")),
							cursor.getInt(cursor.getColumnIndex("tableCount")),
							cursor.getInt(cursor.getColumnIndex("seatCount")));
					if (getTableStyleDetail(tableStyle))
						list.add(tableStyle);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return list;
	}

	/** 获取餐桌类型详情 */
	public boolean getTableStyleDetail(TableStyleData tableStyle) {
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			selectArgs = new String[] { tableStyle.id };
			cursor = db.query(TABLESTYLEDATA, null, "styleId=? ", selectArgs,
					null, null, null);
			if (cursor != null && cursor.moveToNext()) {
				tableStyle = new TableStyleData(cursor.getString(cursor
						.getColumnIndex("styleId")), cursor.getInt(cursor
						.getColumnIndex("tableCount")), cursor.getInt(cursor
						.getColumnIndex("seatCount")));
				getTableDataByTableStyle(tableStyle);
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	/** 根据餐桌类型获取餐桌 */
	public boolean getTableDataByTableStyle(TableStyleData ts) {
		Cursor cursor = null;
		List<TableData> list = new ArrayList<TableData>();
		try {
			db = getReadableDatabase();
			selectArgs = new String[] { ts.id };
			cursor = db.query(TABLEDATA, null, "styleId=? ", selectArgs, null,
					null, null);
			if (cursor != null) {
				while (cursor.moveToNext()) {
					TableData table = new TableData(cursor.getString(cursor
							.getColumnIndex("tableId")),
							cursor.getString(cursor.getColumnIndex("styleId")),
							cursor.getInt(cursor.getColumnIndex("seatCount")),
							cursor.getInt(cursor.getColumnIndex("state")),
							cursor.getInt(cursor.getColumnIndex("customerId")),
							cursor.getString(cursor
									.getColumnIndex("customerName")),
							cursor.getString(cursor.getColumnIndex("bookTime")));
					getSeatDataByTable(table);
					list.add(table);
				}
			}
			ts.setTable(list);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	/** 根据餐桌获取座位 */
	public boolean getSeatDataByTable(TableData table) {
		Cursor cursor = null;
		List<SeatData> list = new ArrayList<SeatData>();
		try {
			selectArgs = new String[] { table.id };
			cursor = db.query(SEATDATA, null, "tableId=? ", selectArgs, null,
					null, null);

			if (cursor != null) {
				while (cursor.moveToNext()) {
					SeatData seat = new SeatData(cursor.getString(cursor
							.getColumnIndex("seatId")), cursor.getString(cursor
							.getColumnIndex("tableId")),
							cursor.getString(cursor.getColumnIndex("styleId")),
							cursor.getInt(cursor.getColumnIndex("state")),
							cursor.getInt(cursor.getColumnIndex("customerId")),
							cursor.getString(cursor
									.getColumnIndex("customerName")),
							cursor.getString(cursor.getColumnIndex("bookTime")));
					list.add(seat);
				}
			}
			table.setSeat(list);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	/** 获取所有餐桌列表 */
	public List<TableData> getTableList() {
		Cursor cursor = null;
		List<TableData> list = new ArrayList<TableData>();
		try {
			db = getReadableDatabase();
			cursor = db.query(TABLEDATA, null, null, null, null, null, null);
			if (cursor != null) {
				while (cursor.moveToNext()) {
					TableData table = new TableData(cursor.getString(cursor
							.getColumnIndex("tableId")),
							cursor.getString(cursor.getColumnIndex("styleId")),
							cursor.getInt(cursor.getColumnIndex("seatCount")),
							cursor.getInt(cursor.getColumnIndex("state")),
							cursor.getInt(cursor.getColumnIndex("customerId")),
							cursor.getString(cursor
									.getColumnIndex("customerName")),
							cursor.getString(cursor.getColumnIndex("bookTime")));
					getSeatDataByTable(table);
					list.add(table);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return list;
	}

	// ------------------------------------------------------------ 插入单个对象
	/** 插入单个座位 */
	private void insertSeatData(SeatData s) {
		if (db == null)
			db = getWritableDatabase();
		if (s == null)
			return;
		try {
			ContentValues values = new ContentValues();
			values.put("seatId", s.seatId);
			values.put("tableId", s.tableId);
			values.put("styleId", s.styleId);
			values.put("customerName", s.customerName);
			values.put("customerId", s.customerId);
			values.put("bookTime", s.bookTime);
			values.put("state", s.state);

			db.insert(SEATDATA, null, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 插入单个餐桌 */
	private boolean insertTableData(TableData s) {
		if (db == null)
			db = getWritableDatabase();
		if (s == null)
			return false;
		try {
			ContentValues values = new ContentValues();
			values.put("tableId", s.id);
			values.put("styleId", s.styleId);
			values.put("state", s.state);
			values.put("seatCount", s.seatCount);
			values.put("customerName", s.customerName);
			values.put("customerId", s.customerId);
			values.put("bookTime", s.bookTime);
			db.insert(TABLEDATA, null, values);
			List<SeatData> list = new ArrayList<SeatData>();
			for (int i = 0; i < s.seatCount; i++) {
				SeatData t = new SeatData(s, i);
				insertSeatData(t);
				list.add(t);
			}
			s.setSeat(list);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 插入单个餐桌类型 */
	public boolean insertTableStyle(TableStyleData s) {
		if (db == null)
			db = getWritableDatabase();
		try {
			ContentValues values = new ContentValues();

			values.put("styleId", s.id);
			values.put("seatCount", s.seatCount);
			values.put("tableCount", s.tableCount);
			db.insert(TABLESTYLEDATA, null, values);
			List<TableData> list = new ArrayList<TableData>();
			for (int i = 0; i < s.tableCount; i++) {
				TableData t = new TableData(s, i);
				t.restaurantId = s.restaurantId;
				insertTableData(t);
				list.add(t);
			}
			s.setTable(list);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	public boolean insertTableStyleList(List<TableStyleData> list) {
		if (db == null)
			db = getWritableDatabase();
		try {
			db.beginTransaction();
			for (TableStyleData ts : list) {
				insertTableStyle(ts);
			}
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	// ------------------------------------------------------------ 修改单个对象
	/** 修改单个座位 */
	public boolean upgradeSeat(SeatData s) {
		try {
			db.beginTransaction();
			ContentValues values = new ContentValues();

			String[] whereArgs = { s.seatId };
			values.put("state", s.state);
			values.put("customerName", s.customerName);
			values.put("customerId", s.customerId);
			values.put("bookTime", s.bookTime);

			db.update(SEATDATA, values, "id=?", whereArgs);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 修改单个餐桌 */
	public boolean upgradeTable(TableData t) {
		try {
			db.beginTransaction();
			ContentValues values = new ContentValues();
			selectArgs = new String[] { t.id };
			values.put("customerName", t.customerName);
			values.put("customerId", t.customerId);
			values.put("bookTime", t.bookTime);
			values.put("state", t.state);

			db.update(TABLEDATA, values, "tableId=?", selectArgs);
			db.update(SEATDATA, values, "tableId=?", selectArgs);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 修改餐桌类型 */
	public boolean upgradeTableStyle(TableStyleData ts) {
		if (deleteSeatDataByStyle(ts) && insertTableStyle(ts))
			return true;
		else
			return false;
	}

	// --------------------------------------------------------- 删除数据
	/** 根据类型删除 */
	public boolean deleteSeatDataByStyle(TableStyleData ts) {
		try {
			db = getWritableDatabase();
			db.beginTransaction();
			selectArgs = new String[] { ts.id };
			db.delete(SEATDATA, "styleId=?", selectArgs);
			db.delete(TABLEDATA, "styleId=?", selectArgs);
			db.delete(TABLESTYLEDATA, "styleId=?", selectArgs);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 控制数据库读写 */
	// public final static int SET_SEAT = 1;
	// private Handler writeHandler;
	// private Thread writeThread = new Thread() {
	// public void run() {
	// Looper.prepare();
	// writeHandler = new Handler() {
	// @Override
	// public void handleMessage(Message msg) {
	// try {
	// switch (msg.what) {
	// case SET_SEAT:
	//
	// break;
	// default:
	// break;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// db.close();
	// }
	// }
	// };
	// Looper.loop();
	// };
	// };
	//
	// public void setSQLData(int type, int id, Object obj) {
	// Message msg = writeHandler.obtainMessage();
	// msg.what = type;
	// msg.arg1 = id;
	// msg.obj = obj;
	//
	// writeHandler.sendMessage(msg);
	// }
}
