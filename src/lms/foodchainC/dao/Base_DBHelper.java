package lms.foodchainC.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author 李梦思
 * @description 数据库辅助工具基类
 * @createTime 2013-2-20
 */
public abstract class Base_DBHelper extends SQLiteOpenHelper {
	SQLiteDatabase db;
	public static final String CREATE = "CREATE TABLE IF NOT EXISTS ";
	public static final String DROP = "DROP TABLE IF EXISTS ";
	public static final String AUTO_KEY = "id integer primary key autoincrement";
	public String DATABASE_NAME;
	public static String[] selectArgs;
	public Context context;

	public Base_DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		DATABASE_NAME = name;
		this.context = context;
	}

	public boolean deleteDataBase() {
		return context.deleteDatabase(DATABASE_NAME);
	}
}
