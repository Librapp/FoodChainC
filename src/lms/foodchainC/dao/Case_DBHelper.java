package lms.foodchainC.dao;

import java.util.ArrayList;

import lms.foodchainC.data.CaseData;
import lms.foodchainC.data.CaseStyleData;
import lms.foodchainC.data.CaseTimeData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author 李梦思
 * @description 菜单数据库辅助工具类
 * @createTime 2013-2-20
 */
public class Case_DBHelper extends Base_DBHelper {

	private static int VERSION = 1;
	private final String CASEDATA = "caseData";
	private final String STYLEDATA = "styleData";
	private final String MENUDATA = "menuData";
	private final String TIMEDATA = "timeData";

	public Case_DBHelper(Context context) {
		super(context, "tcr_case.db", null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		createTable();
	}

	/** 生成表 */
	private void createTable() {
		db.execSQL(createCaseDataTable());
		db.execSQL(createStyleDataTable());
		db.execSQL(createMenuDataTable());
		db.execSQL(createTimeDataTable());

	}

	/** 生成菜单表 */
	private String createMenuDataTable() {
		return CREATE + MENUDATA + " (" + "id integer" + ",caseId integer"
				+ ",name varchar" + ",price float" + ",pic varchar"
				+ ",cookTime integer" + ",intro varchar" + ",style integer"
				+ ",weekday integer" + ",time integer" + ",special float"
				+ ",state integer" + ")";
	}

	/** 生成菜肴表 */
	private String createCaseDataTable() {
		return CREATE + CASEDATA + " (" + AUTO_KEY + ",caseId integer"
				+ ",name varchar" + ",price float" + ",pic varchar"
				+ ",intro varchar" + ",cookTime integer" + ",style integer"
				+ ",family integer" + ")";
	}

	/** 生成自定义类型表 */
	private String createStyleDataTable() {
		return CREATE + STYLEDATA + " (" + AUTO_KEY + ",name varchar" + ")";
	}

	/** 生成自定义时段表 */
	private String createTimeDataTable() {
		return CREATE + TIMEDATA + " (" + AUTO_KEY + ",name varchar"
				+ ",startTime varchar" + ",endTime varchar"
				+ ",weekday integer" + ")";
	}

	/** 获取详情 */
	public boolean getCaseDetail(CaseData c) {
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			selectArgs = new String[] { c.name, c.id + "" };
			cursor = db.query(CASEDATA, null, "name=? OR id=?", selectArgs,
					null, null, null);
			if (cursor != null && cursor.moveToNext()) {
				c.id = cursor.getInt(cursor.getColumnIndex("id"));
				c.caseId = cursor.getInt(cursor.getColumnIndex("caseId"));
				c.name = cursor.getString(cursor.getColumnIndex("name"));
				c.price = cursor.getFloat(cursor.getColumnIndex("price"));
				c.intro = cursor.getString(cursor.getColumnIndex("intro"));
				c.picPath = cursor.getString(cursor.getColumnIndex("pic"));
				c.cookTime = cursor.getInt(cursor.getColumnIndex("cookTime"));
				c.style = cursor.getInt(cursor.getColumnIndex("style"));
				c.family = cursor.getInt(cursor.getColumnIndex("family"));
				c.isNew = false;
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

	/** 获取菜单菜详情 */
	public boolean getMenuCase(CaseData c) {
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			selectArgs = new String[] { c.name, c.weekday + "", c.time + "" };
			cursor = db.query(MENUDATA, null,
					"name=? AND weekday=? AND time=?", selectArgs, null, null,
					null);
			if (cursor != null && cursor.moveToNext()) {
				c.id = cursor.getInt(cursor.getColumnIndex("id"));
				c.caseId = cursor.getInt(cursor.getColumnIndex("caseId"));
				c.name = cursor.getString(cursor.getColumnIndex("name"));
				c.price = cursor.getFloat(cursor.getColumnIndex("price"));
				c.intro = cursor.getString(cursor.getColumnIndex("intro"));
				c.picPath = cursor.getString(cursor.getColumnIndex("pic"));
				c.cookTime = cursor.getInt(cursor.getColumnIndex("cookTime"));
				c.style = cursor.getInt(cursor.getColumnIndex("style"));
				c.weekday = cursor.getInt(cursor.getColumnIndex("weekday"));
				c.time = cursor.getInt(cursor.getColumnIndex("time"));
				c.special = cursor.getFloat(cursor.getColumnIndex("special"));
				c.state = cursor.getInt(cursor.getColumnIndex("state"));
				c.isNew = false;
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

	/** 获取时间段 */
	private ArrayList<CaseTimeData> getTime(int day) {
		ArrayList<CaseTimeData> list = new ArrayList<CaseTimeData>();
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			selectArgs = new String[] { day + "" };
			cursor = db.query(TIMEDATA, null, "weekday=?", selectArgs, null,
					null, null);
			if (cursor != null)
				while (cursor.moveToNext()) {
					CaseTimeData c = new CaseTimeData(cursor.getInt(cursor
							.getColumnIndex("id")), cursor.getString(cursor
							.getColumnIndex("name")), cursor.getString(cursor
							.getColumnIndex("startTime")),
							cursor.getString(cursor.getColumnIndex("endTime")),
							cursor.getInt(cursor.getColumnIndex("weekday")));
					list.add(c);
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

	/** 获取星期菜单 */
	public ArrayList<CaseTimeData> getMenu(int day) {
		ArrayList<CaseTimeData> list = getTime(day);
		Cursor cursor = null;
		for (int i = 0; i < list.size(); i++) {
			try {
				db = getReadableDatabase();
				selectArgs = new String[] { day + "", list.get(i).id + "" };
				cursor = db.query(MENUDATA, null, "weekday=? AND time=?",
						selectArgs, "style", null, null);
				ArrayList<CaseData> caseList = new ArrayList<CaseData>();
				if (cursor != null) {
					while (cursor.moveToNext()) {
						CaseData c = new CaseData(
								cursor.getInt(cursor.getColumnIndex("id")),
								cursor.getInt(cursor.getColumnIndex("caseId")),
								cursor.getString(cursor.getColumnIndex("name")),
								cursor.getFloat(cursor.getColumnIndex("price")),
								cursor.getString(cursor.getColumnIndex("pic")),
								cursor.getInt(cursor.getColumnIndex("cookTime")),
								cursor.getString(cursor.getColumnIndex("intro")),
								cursor.getInt(cursor.getColumnIndex("style")),
								cursor.getFloat(cursor
										.getColumnIndex("special")),
								cursor.getInt(cursor.getColumnIndex("state")),
								cursor.getInt(cursor.getColumnIndex("weekday")),
								cursor.getInt(cursor.getColumnIndex("time")));
						caseList.add(c);
					}
				}
				list.get(i).setList(caseList);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		}
		return list;
	}

	/** 按时间段获取菜单 */
	public boolean getCaseTimeData(CaseTimeData csd) {
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			selectArgs = new String[] { csd.weekday + "", csd.id + "" };
			cursor = db.query(MENUDATA, null, "weekday=? AND time=?",
					selectArgs, "style", null, null);
			ArrayList<CaseData> caseList = new ArrayList<CaseData>();
			if (cursor != null) {
				while (cursor.moveToNext()) {
					CaseData c = new CaseData(cursor.getInt(cursor
							.getColumnIndex("id")), cursor.getInt(cursor
							.getColumnIndex("caseId")), cursor.getString(cursor
							.getColumnIndex("name")), cursor.getFloat(cursor
							.getColumnIndex("price")), cursor.getString(cursor
							.getColumnIndex("pic")), cursor.getInt(cursor
							.getColumnIndex("cookTime")),
							cursor.getString(cursor.getColumnIndex("intro")),
							cursor.getInt(cursor.getColumnIndex("style")),
							cursor.getFloat(cursor.getColumnIndex("special")),
							cursor.getInt(cursor.getColumnIndex("state")),
							cursor.getInt(cursor.getColumnIndex("weekday")),
							cursor.getInt(cursor.getColumnIndex("time")));
					caseList.add(c);
				}
			}
			csd.setList(caseList);
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

	/** 获取类型 */
	public ArrayList<CaseStyleData> getStyle() {
		ArrayList<CaseStyleData> list = new ArrayList<CaseStyleData>();
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			cursor = db.query(STYLEDATA, null, null, null, null, null, "id");
			while (cursor.moveToNext()) {
				CaseStyleData c = new CaseStyleData(cursor.getInt(0),
						cursor.getString(1));
				list.add(c);
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

	/** 按类型获取菜单 */
	public boolean getCaseStyleData(CaseStyleData csd) {
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			selectArgs = new String[] { csd.id + "" };
			cursor = db.query(CASEDATA, null, "style=?", selectArgs, null,
					null, null);
			ArrayList<CaseData> caseList = new ArrayList<CaseData>();
			if (cursor != null) {
				while (cursor.moveToNext()) {
					CaseData c = new CaseData(cursor.getInt(cursor
							.getColumnIndex("id")), cursor.getInt(cursor
							.getColumnIndex("caseId")), cursor.getString(cursor
							.getColumnIndex("name")), cursor.getFloat(cursor
							.getColumnIndex("price")), cursor.getString(cursor
							.getColumnIndex("pic")), cursor.getString(cursor
							.getColumnIndex("intro")), cursor.getInt(cursor
							.getColumnIndex("cookTime")), cursor.getInt(cursor
							.getColumnIndex("style")), cursor.getInt(cursor
							.getColumnIndex("family")));
					caseList.add(c);
				}
			}
			csd.setList(caseList);
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

	/** 按名字获取类型 */
	public boolean getCaseStyleDataByName(CaseStyleData csd) {
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			selectArgs = new String[] { csd.name };
			cursor = db.query(STYLEDATA, null, "name=?", selectArgs, null,
					null, null);
			if (cursor.moveToNext()) {
				csd = new CaseStyleData(cursor.getInt(cursor
						.getColumnIndex("id")), cursor.getString(cursor
						.getColumnIndex("name")));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/** 获取时间段 */
	public boolean getCaseTimeDataByName(CaseTimeData ctd) {
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			selectArgs = new String[] { ctd.name, ctd.weekday + "" };
			cursor = db.query(TIMEDATA, null, "name=? AND weekday=?",
					selectArgs, null, null, "startTime");
			if (cursor.moveToNext() && cursor.getCount() > 0) {
				ctd = new CaseTimeData(cursor.getInt(cursor
						.getColumnIndex("id")), cursor.getString(cursor
						.getColumnIndex("name")), cursor.getString(cursor
						.getColumnIndex("startTime")), cursor.getString(cursor
						.getColumnIndex("endTime")), cursor.getInt(cursor
						.getColumnIndex("weekday")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/** 创建新菜 */
	public boolean createCase(CaseData c) {
		db = getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("caseId", c.caseId);
			values.put("name", c.name);
			values.put("price", c.price);
			values.put("pic", c.picPath);
			values.put("intro", c.intro);
			values.put("cookTime", c.cookTime);
			values.put("style", c.style);
			values.put("family", c.family);
			db.beginTransaction();
			db.insert(CASEDATA, null, values);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 创建新菜单菜 */
	public boolean createMenuCase(CaseData c) {
		db = getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("id", c.id);
			values.put("caseId", c.caseId);
			values.put("name", c.name);
			values.put("price", c.price);
			values.put("pic", c.picPath);
			values.put("intro", c.intro);
			values.put("cookTime", c.cookTime);
			values.put("style", c.style);
			values.put("weekday", c.weekday);
			values.put("time", c.time);
			values.put("special", c.special);
			values.put("state", c.state);
			db.beginTransaction();
			db.insert(MENUDATA, null, values);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 创建新时间段 */
	public boolean createCaseTime(CaseTimeData c) {
		db = getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("name", c.name);
			values.put("startTime", c.startTime);
			values.put("endTime", c.endTime);
			values.put("weekday", c.weekday);
			db.beginTransaction();
			db.insert(TIMEDATA, null, values);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 创建新类型 */
	public boolean createCaseStyle(CaseStyleData c) {
		db = getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("name", c.name);
			db.beginTransaction();
			db.insert(STYLEDATA, null, values);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 修改已有菜 */
	public boolean upgradeCase(CaseData c) {
		db = getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("caseId", c.caseId);
			values.put("name", c.name);
			values.put("price", c.price);
			values.put("pic", c.picPath);
			values.put("intro", c.intro);
			values.put("cookTime", c.cookTime);
			values.put("type", c.type);
			values.put("style", c.style);
			values.put("family", c.family);
			selectArgs = new String[] { c.id + "" };
			db.beginTransaction();
			db.update(CASEDATA, values, "id=?", selectArgs);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 修改星期菜 */
	public boolean upgradeMenuCase(CaseData c) {
		db = getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("special", c.special);
			values.put("state", c.state);
			selectArgs = new String[] { c.id + "", c.weekday + "", c.time + "" };
			db.beginTransaction();
			db.update(MENUDATA, values, "id=? AND weekday=? AND time=?",
					selectArgs);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 修改类型菜 */
	public boolean upgradeStyleCase(CaseData c) {
		db = getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("state", c.state);
			selectArgs = new String[] { c.id + "", c.weekday + "" };
			db.beginTransaction();
			db.update(STYLEDATA, values, "id=? AND weekday=?", selectArgs);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 删除已有菜 */
	public boolean deleteCase(CaseData c) {
		if (db == null)
			db = getWritableDatabase();
		try {
			selectArgs = new String[] { c.id + "" };
			db.beginTransaction();
			db.delete(CASEDATA, "id=?", selectArgs);
			db.delete(MENUDATA, "id=?", selectArgs);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 删除星期菜 */
	public boolean deleteMenuCase(CaseData c) {
		if (db == null)
			db = getWritableDatabase();
		try {
			selectArgs = new String[] { c.id + "", c.weekday + "" };
			db.beginTransaction();
			db.delete(MENUDATA, "id=? AND weekday=?", selectArgs);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 删除时间段 */
	public boolean deleteCaseTime(CaseTimeData c) {
		db = getWritableDatabase();
		try {
			selectArgs = new String[] { c.id + "", c.weekday + "" };
			db.beginTransaction();
			db.delete(TIMEDATA, "id=? AND weekday=?", selectArgs);
			db.delete(MENUDATA, "time=? AND weekday=?", selectArgs);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 删除类型 */
	public boolean deleteCaseStyle(CaseStyleData c) {
		db = getWritableDatabase();
		try {
			selectArgs = new String[] { c.id + "" };
			db.beginTransaction();
			db.delete(STYLEDATA, "id=?", selectArgs);
			ContentValues values = new ContentValues();
			values.put("style", 0);
			db.update(CASEDATA, null, "style=?", selectArgs);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 更新数据库 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
