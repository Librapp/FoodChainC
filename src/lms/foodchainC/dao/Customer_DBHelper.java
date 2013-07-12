package lms.foodchainC.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * @author 李梦思
 * @description 顾客数据库辅助工具类
 * @createTime 2013-2-20
 */
public class Customer_DBHelper extends Base_DBHelper {
	private String TEMPCUSTOMER = "TempCustomer";
	private String VIPCUSTOMER = "VipCustomer";

	public Customer_DBHelper(Context context, CursorFactory factory, int version) {
		super(context, "fcr_customer.db", factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		creatTable();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	/** 生成表 */
	private void creatTable() {
		if (db == null) {
			db = getWritableDatabase();
		}
		db.execSQL(createVIPCustomerTable());
		db.execSQL(createTempCustomerTable());
	}

	/** 生成临时顾客表 */
	private String createTempCustomerTable() {
		return CREATE + TEMPCUSTOMER + " (" + "styleId varchar(3)"
				+ ",seatCount int(2)" + ",tableCount int(2)" + ")";
	}

	/** 生成会员表 */
	private String createVIPCustomerTable() {
		return CREATE + VIPCUSTOMER + " (" + "styleId varchar(3)"
				+ ",seatCount int(2)" + ",tableCount int(2)" + ")";
	}

}
