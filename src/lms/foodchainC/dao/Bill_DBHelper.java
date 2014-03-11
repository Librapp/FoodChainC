package lms.foodchainC.dao;

import java.util.ArrayList;
import java.util.List;

import lms.foodchainC.data.BillData;
import lms.foodchainC.data.CaseData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author 李梦思
 * @description 账单数据库辅助工具类
 * @createTime 2013-3-20
 */
public class Bill_DBHelper extends Base_DBHelper {

	private static int VERSION = 1;
	private final String BILLDATA = "billData";
	private final String ORDERDATA = "orderData";

	public Bill_DBHelper(Context context) {
		super(context, "fcc_bill.db", null, VERSION);
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
		db.execSQL(createBillDataTable());
		db.execSQL(createOrderDataTable());
	}

	/** 生成账单表 */
	private String createBillDataTable() {
		return CREATE + BILLDATA + " (" + "billId integer primary key,"
				+ "customerId varchar," + "customerName varchar,"
				+ "seatId varchar," + "tableId varchar,"
				+ "createTime varchar," + "state integer," + "tip integer,"
				+ "discount float," + "cost float" + ")";
	}

	/** 生成订单表 */
	private String createOrderDataTable() {
		return CREATE + ORDERDATA + " (" + "id integer primary key"
				+ ",name varchar" + ",price float" + ",pic varchar"
				+ ",cookTime integer" + ",style integer" + ",state integer"
				+ ",count integer" + ")";
	}

	/** 创建新菜 */
	public boolean insertCase(CaseData c) {
		try {
			ContentValues values = new ContentValues();
			values.put("id", c.id);
			if (getCase(c)) {
				db = getWritableDatabase();
				values.put("count", c.count);
				selectArgs = new String[] { "count=?" };
				db.update(ORDERDATA, values, null, selectArgs);
			} else {
				db = getWritableDatabase();
				values.put("name", c.name);
				values.put("price", c.price);
				values.put("pic", c.picPath);
				values.put("cookTime", c.cookTime);
				values.put("style", c.style);
				values.put("state", c.state);
				values.put("count", c.count);
				db.insert(ORDERDATA, null, values);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean getCase(CaseData c) {
		db = getReadableDatabase();
		Cursor cursor = null;
		try {
			selectArgs = new String[] { c.id + "" };
			cursor = db.query(ORDERDATA, null, "id=?", selectArgs, null, null,
					null);
			return cursor.moveToFirst();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	/** 获取帐单 */
	public boolean getBillData(BillData b) {
		db = getReadableDatabase();
		Cursor cursor = null;
		try {
			selectArgs = new String[] { b.customerId, b.customerName, b.seatId,
					b.tableId };
			cursor = db.query(BILLDATA, null,
					"customerId=? OR customerName=? OR seatId=? OR tableId=?",
					selectArgs, "tableId", null, "seatId");
			if (cursor != null) {
				b.id = cursor.getInt(cursor.getColumnIndex("billId"));
				b.customerId = cursor.getString(cursor
						.getColumnIndex("customerId"));
				b.customerName = cursor.getString(cursor
						.getColumnIndex("customerName"));
				b.seatId = cursor.getString(cursor.getColumnIndex("seatId"));
				b.tableId = cursor.getString(cursor.getColumnIndex("tableId"));
				b.createTime = cursor.getString(cursor
						.getColumnIndex("createTime"));
				b.type = cursor.getInt(cursor.getColumnIndex("type"));
				b.address = cursor.getString(cursor.getColumnIndex("address"));
				b.tel = cursor.getString(cursor.getColumnIndex("tel"));
				b.state = cursor.getInt(cursor.getColumnIndex("state"));
				b.cost = cursor.getFloat(cursor.getColumnIndex("cost"));
				b.discount = cursor.getFloat(cursor.getColumnIndex("discount"));
				b.tip = cursor.getInt(cursor.getColumnIndex("tip"));
			}
			if (getOrderData(b))
				return true;
			else
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

	/** 获取订单 */
	public boolean getOrderData(BillData b) {
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			List<CaseData> list = new ArrayList<CaseData>();
			selectArgs = new String[] { b.id + "" };
			cursor = db.query(ORDERDATA, null, "billId=?", selectArgs, null,
					null, null);

			if (cursor != null) {
				while (cursor.moveToNext()) {
					CaseData c = new CaseData(
							cursor.getInt(cursor.getColumnIndex("orderId")),
							cursor.getInt(cursor.getColumnIndex("id")),
							cursor.getInt(cursor.getColumnIndex("caseId")),
							cursor.getString(cursor.getColumnIndex("caseName")),
							cursor.getFloat(cursor.getColumnIndex("price")),
							cursor.getInt(cursor.getColumnIndex("billId")),
							cursor.getString(cursor.getColumnIndex("orderTime")),
							cursor.getInt(cursor.getColumnIndex("state")),
							cursor.getInt(cursor.getColumnIndex("type")),
							cursor.getString(cursor.getColumnIndex("message")));
					list.add(c);
				}
				b.setCaseList(list);
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

	/** 获取订单 */
	public List<CaseData> getOrderList() {
		Cursor cursor = null;
		List<CaseData> list = new ArrayList<CaseData>();
		try {
			db = getReadableDatabase();
			cursor = db.query(ORDERDATA, null, null, null, null, null,
					"orederId");
			if (cursor != null) {
				while (cursor.moveToNext()) {
					CaseData c = new CaseData(
							cursor.getInt(cursor.getColumnIndex("orderId")),
							cursor.getInt(cursor.getColumnIndex("id")),
							cursor.getInt(cursor.getColumnIndex("caseId")),
							cursor.getString(cursor.getColumnIndex("caseName")),
							cursor.getFloat(cursor.getColumnIndex("price")),
							cursor.getInt(cursor.getColumnIndex("billId")),
							cursor.getString(cursor.getColumnIndex("orderTime")),
							cursor.getInt(cursor.getColumnIndex("state")),
							cursor.getInt(cursor.getColumnIndex("type")),
							cursor.getString(cursor.getColumnIndex("message")));
					list.add(c);
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

	/** 创建账单 */
	public boolean createBillData(BillData b) {
		if (db == null)
			db = getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("customerId", b.customerId);
			values.put("customerName", b.customerName);
			values.put("seatId", b.seatId);
			values.put("tableId", b.tableId);
			values.put("createTime", b.createTime);
			values.put("type", b.type);
			db.beginTransaction();
			db.insert(BILLDATA, null, values);
			createOrderData(b);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 创建订单 */
	private void createOrderData(BillData b) {
		try {
			for (CaseData c : b.getCaseList()) {
				ContentValues values = new ContentValues();
				values.put("caseId", c.id);
				values.put("caseName", c.name);
				values.put("price", c.price);
				values.put("billId", b.id);
				values.put("orderTime", c.orderTime);
				values.put("state", c.state);
				values.put("type", c.type);
				values.put("message", c.message);
				db.insert(BILLDATA, null, values);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 删除账单 */
	public boolean deleteBill(BillData b) {
		if (db == null)
			db = getWritableDatabase();
		try {
			selectArgs = new String[] { b.id + "" };
			db.beginTransaction();
			db.delete(BILLDATA, "billId=?", selectArgs);
			db.delete(ORDERDATA, "billId=?", selectArgs);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 删除订单 */
	public boolean deleteOrder(CaseData c) {
		if (db == null)
			db = getWritableDatabase();
		try {
			selectArgs = new String[] { c.orderId + "" };
			db.beginTransaction();
			db.delete(ORDERDATA, "oiderId=?", selectArgs);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	public void deleteCase(CaseData c) {
		if (db == null)
			db = getWritableDatabase();
		try {
			selectArgs = new String[] { c.id + "" };
			db.beginTransaction();
			db.delete(ORDERDATA, "id=?", selectArgs);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
