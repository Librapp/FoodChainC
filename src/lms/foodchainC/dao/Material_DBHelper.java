package lms.foodchainC.dao;

import lms.foodchainC.data.MaterialData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author 李梦思
 * @description 材料数据库辅助工具类
 * @createTime 2013-9-2
 */
public class Material_DBHelper extends Base_DBHelper {

	private static int VERSION = 1;
	private final String MATERIALDATA = "materialData";

	public Material_DBHelper(Context context) {
		super(context, "fcr_material.db", null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		createTable();
	}

	/** 生成表 */
	private void createTable() {
		if (db == null) {
			db = getWritableDatabase();
		}
		db.execSQL(createMaterialDataTable());
	}

	/** 生成材料表 */
	private String createMaterialDataTable() {
		return CREATE + MATERIALDATA + " (" + "materialId integer,"
				+ "name varchar," + "unit varchar," + "price float,"
				+ "count float" + ")";
	}

	/** 获取材料 */
	public boolean getMaterial(MaterialData b) {
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			selectArgs = new String[] { b.materialId + "", b.name, b.unit,
					b.price + "", b.count + "" };
			cursor = db.query(MATERIALDATA, null,
					"materialId=? OR name=? OR unit=? OR price=? OR count=?",
					selectArgs, "unit", null, "name");
			if (cursor != null) {
				b.materialId = cursor.getInt(cursor
						.getColumnIndex("materialId"));
				b.name = cursor.getString(cursor.getColumnIndex("name"));
				b.unit = cursor.getString(cursor.getColumnIndex("unit"));
				b.price = cursor.getFloat(cursor.getColumnIndex("price"));
				b.count = cursor.getFloat(cursor.getColumnIndex("count"));
			}
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

	/** 创建材料 */
	public boolean createMaterial(MaterialData b) {
		if (db == null)
			db = getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("materialId", b.materialId);
			values.put("name", b.name);
			values.put("unit", b.unit);
			values.put("price", b.price);
			values.put("count", b.count);
			db.insert(MATERIALDATA, null, values);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 删除材料 */
	public boolean deleteMaterial(MaterialData b) {
		if (db == null)
			db = getWritableDatabase();
		try {
			selectArgs = new String[] { b.materialId + "" };
			db.delete(MATERIALDATA, "materialId=?", selectArgs);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 更新材料属性 */
	public boolean upgradeMaterial(MaterialData m) {
		if (db == null)
			db = getWritableDatabase();
		try {
			selectArgs = new String[] { m.name };
			ContentValues values = new ContentValues();
			values.put("materialId", m.materialId);
			values.put("name", m.name);
			values.put("unit", m.unit);
			values.put("price", m.price);
			values.put("count", m.count);
			db.update(MATERIALDATA, values, "name=?", selectArgs);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
