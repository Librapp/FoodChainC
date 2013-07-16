package lms.foodchainC.dao;

import java.util.ArrayList;

import lms.foodchainC.data.MessageData;
import lms.foodchainC.data.UserData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author 李梦思
 * @description 消息数据库辅助工具类
 * @createTime 2013-6-18
 */
public class Message_DBHelper extends Base_DBHelper {
	private String MESSAGEDATA = "message";

	public Message_DBHelper(Context context) {
		super(context, "fcr_customer.db", null, 1);
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
		db.execSQL(createMessageTable());
	}

	/** 生成消息表 */
	private String createMessageTable() {
		return CREATE + MESSAGEDATA + " (" + AUTO_KEY + ",direction integer"
				+ ",hasRead boolean" + ",content varchar" + ",url varchar"
				+ ",pic varchar" + ",time varchar" + ",sId integer"
				+ ",rId integer" + ",userName varchar" + ",userPic varchar"
				+ ")";
	}

	/** 增 */
	public boolean insert(MessageData c) {
		db = getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("id", c.id);
			values.put("direction", c.direction);
			values.put("hasRead", c.hasRead);
			values.put("content", c.content);
			values.put("url", c.url);
			values.put("pic", c.pic);
			values.put("time", c.time);
			values.put("sId", c.sId);
			values.put("rId", c.rId);
			values.put("userName", c.userName);
			values.put("userPic", c.userPic);
			db.beginTransaction();
			db.insert(MESSAGEDATA, null, values);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 删 */
	public boolean delete(MessageData c) {
		db = getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("id", c.id);
			values.put("direction", c.direction);
			values.put("hasRead", c.hasRead);
			values.put("content", c.content);
			values.put("url", c.url);
			values.put("pic", c.pic);
			values.put("time", c.time);
			values.put("sId", c.sId);
			values.put("rId", c.rId);
			values.put("userName", c.userName);
			values.put("userPic", c.userPic);
			db.beginTransaction();
			db.insert(MESSAGEDATA, null, values);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	/** 改 */
	public boolean upgrade(MessageData c) {
		db = getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("hasRead", c.hasRead);
			selectArgs = new String[] { c.id + "" };
			db.beginTransaction();
			db.update(MESSAGEDATA, values, "id=?", selectArgs);
			db.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.endTransaction();
		}
	}

	public boolean getMessageList(UserData c) {
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			selectArgs = new String[] { c.id + "", c.id + "" };
			cursor = db.query(MESSAGEDATA, null, "sId=? OR rId=?", selectArgs,
					null, null, "id");
			if (cursor != null) {
				ArrayList<MessageData> list = new ArrayList<MessageData>();
				while (cursor.moveToNext()) {
					MessageData m = new MessageData();
					m.id = cursor.getInt(cursor.getColumnIndex("id"));
					m.direction = cursor.getInt(cursor
							.getColumnIndex("direction"));
					m.hasRead = cursor.getInt(cursor.getColumnIndex("hasRead"));
					m.content = cursor.getString(cursor
							.getColumnIndex("content"));
					m.url = cursor.getString(cursor.getColumnIndex("url"));
					m.pic = cursor.getString(cursor.getColumnIndex("pic"));
					m.time = cursor.getString(cursor.getColumnIndex("time"));
					m.userName = cursor.getString(cursor
							.getColumnIndex("userName"));
					m.userPic = cursor.getString(cursor
							.getColumnIndex("userPic"));
					m.sId = cursor.getInt(cursor.getColumnIndex("sId"));
					m.rId = cursor.getInt(cursor.getColumnIndex("rId"));
					list.add(m);
				}
				c.setMessage(list);
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

}
