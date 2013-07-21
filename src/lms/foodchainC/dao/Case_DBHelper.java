package lms.foodchainC.dao;

import java.util.ArrayList;

import lms.foodchainC.data.CaseData;
import lms.foodchainC.data.CaseStyleData;
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
	private final String STYLEDATA = "styleData";
	private final String MENUDATA = "menuData";

	public Case_DBHelper(Context context) {
		super(context, "fcc_case.db", null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		createTable();
	}

	/** 生成表 */
	private void createTable() {
		db.execSQL(createStyleDataTable());
		db.execSQL(createMenuDataTable());

	}

	/** 生成菜单表 */
	private String createMenuDataTable() {
		return CREATE + MENUDATA + " (" + "id integer" + ",caseId integer"
				+ ",name varchar" + ",price float" + ",pic varchar"
				+ ",cookTime integer" + ",intro varchar" + ",style integer"
				+ ",special float" + ",state integer" + ")";
	}

	/** 生成自定义类型表 */
	private String createStyleDataTable() {
		return CREATE + STYLEDATA + " (" + "id integer" + ",name varchar" + ")";
	}

	/** 获取菜单菜详情 */
	public boolean getMenuCase(CaseData c) {
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			selectArgs = new String[] { c.name };
			cursor = db.query(MENUDATA, null, "name=?", selectArgs, null, null,
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
			cursor = db.query(MENUDATA, null, "style=?", selectArgs, null,
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

	/** 更新数据库 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	/** 全部删除 */
	public void delateAll() {
		db.delete(MENUDATA, null, null);
	}

}
