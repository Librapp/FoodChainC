package lms.foodchainC.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author 李梦思
 * @description 菜单数据库辅助工具类
 * @createTime 2013-4-24
 */
public class Menu_DBHelper extends Base_DBHelper {

	private static int VERSION = 1;
	private final String MENUDATA = "menuData";
	private final String TIMEDATA = "timeData";

	public Menu_DBHelper(Context context) {
		super(context, "fcr_bill.db", null, VERSION);
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
		db.execSQL(createMenuDataTable());
		db.execSQL(createTimeDataTable());
	}

	/** 生成菜单表 */
	private String createMenuDataTable() {
		return CREATE + MENUDATA + " (" + "caseId integer" + ",name varchar"
				+ ",price float" + ",pic varchar" + ",cookTime integer"
				+ ",style integer" + ",weekday integer" + ",time integer"
				+ ",special float" + ",state integer" + ")";
	}

	/** 生成自定义时段表 */
	private String createTimeDataTable() {
		return CREATE + TIMEDATA + " ("
				+ "id integer primary key autoincrement" + ",name varchar"
				+ ",startTime varchar" + ",endTIme varchar"
				+ ",weekday integer" + ")";
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
