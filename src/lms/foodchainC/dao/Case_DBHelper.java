package lms.foodchainC.dao;

import java.util.ArrayList;
import java.util.List;

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
	private final String CASEDATA = "caseData";
	private final String STYLEDATA = "styleData";

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
		db.execSQL(createCaseDataTable());
		db.execSQL(createStyleDataTable());

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

	/** 获取详情 */
	public boolean getCaseDetail(CaseData c) {
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			selectArgs = new String[] { c.name, c.id + "", c.caseId + "" };
			cursor = db.query(CASEDATA, null, "name=? OR id=? OR caseId=?",
					selectArgs, null, null, null);
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

	/** 获取类型 */
	public ArrayList<CaseStyleData> getCaseStyleList() {
		ArrayList<CaseStyleData> list = new ArrayList<CaseStyleData>();
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			cursor = db.query(STYLEDATA, null, null, null, null, null, "id");
			while (cursor.moveToNext()) {
				CaseStyleData c = new CaseStyleData(cursor.getInt(0),
						cursor.getString(1));
				if (getCaseStyleData(c))
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
				csd.id = cursor.getInt(cursor.getColumnIndex("id"));
				csd.name = cursor.getString(cursor.getColumnIndex("name"));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/** 创建新菜 */
	private boolean insertCase(CaseData c) {
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
			db.insert(CASEDATA, null, values);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/** 创建新类型 */
	private boolean insertCaseStyle(CaseStyleData cs) {
		try {
			ContentValues values = new ContentValues();
			values.put("name", cs.name);
			values.put("id", cs.id);
			db.insert(STYLEDATA, null, values);
			if (cs.getList() != null)
				for (CaseData c : cs.getList()) {
					insertCase(c);
				}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 插入类型列表
	 * 
	 * @param list
	 * @return
	 */
	public boolean insertCaseStyleList(List<CaseStyleData> list) {
		if (db == null)
			db = getWritableDatabase();
		try {
			db.beginTransaction();
			for (CaseStyleData cs : list) {
				insertCaseStyle(cs);
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

	/** 删除已有菜 */
	public boolean deleteCase(CaseData c) {
		if (db == null)
			db = getWritableDatabase();
		try {
			selectArgs = new String[] { c.id + "" };
			db.beginTransaction();
			db.delete(CASEDATA, "id=?", selectArgs);
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
