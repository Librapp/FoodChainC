package lms.foodchainC.dao;

import lms.foodchainC.data.RestaurantData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Restaurant_DBHelper extends Base_DBHelper {
	private static int VERSION = 1;
	private final String RESTAURANTDATA = "restaurantData";

	public Restaurant_DBHelper(Context context) {
		super(context, "fcc_restaurant.db", null, VERSION);
		// TODO Auto-generated constructor stub
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
		db.execSQL(createRestaurantDataTable());
	}

	/** 生成饭店表 */
	private String createRestaurantDataTable() {
		return CREATE + RESTAURANTDATA + " (" + "id integer," + "name varchar,"
				+ "address varchar," + "intro varchar," + "tel varchar,"
				+ "sms varchar," + "opentime varchar," + "waitNumber integer"
				+ ")";
	}

	public boolean getRestaurantData(RestaurantData r) {
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			selectArgs = new String[] { r.id + "", r.name, r.address, r.intro,
					r.tel, r.sms, r.opentime, r.waitNumber + "" };
			cursor = db
					.query(RESTAURANTDATA,
							null,
							"id=? OR name=? OR address=? OR intro=? OR tel=? OR ms=? OR opentime=? OR waitNumber=?",
							selectArgs, null, null, "id");
			if (cursor != null) {
				r.id = cursor.getInt(cursor.getColumnIndex("id"));
				r.name = cursor.getString(cursor.getColumnIndex("name"));
				r.address = cursor.getString(cursor.getColumnIndex("address"));
				r.intro = cursor.getString(cursor.getColumnIndex("intro"));
				r.tel = cursor.getString(cursor.getColumnIndex("tel"));
				r.sms = cursor.getString(cursor.getColumnIndex("sms"));
				r.opentime = cursor
						.getString(cursor.getColumnIndex("opentime"));
				r.waitNumber = cursor.getInt(cursor
						.getColumnIndex("waitNumber"));
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

	/** 插入饭店 */
	public boolean insertRestaurantData(RestaurantData r) {
		if (db == null)
			db = getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("id", r.id);
			values.put("name", r.name);
			values.put("address", r.address);
			values.put("intro", r.intro);
			values.put("tel", r.tel);
			values.put("sms", r.sms);
			values.put("opentime", r.opentime);
			values.put("waitNumber", r.waitNumber);
			db.insert(RESTAURANTDATA, null, values);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 删除饭店 */
	public boolean deleteRestaurantData(RestaurantData r) {
		if (db == null)
			db = getWritableDatabase();
		try {
			selectArgs = new String[] { r.id + "", r.name };
			db.delete(RESTAURANTDATA, "id=? OR name=?", selectArgs);
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
