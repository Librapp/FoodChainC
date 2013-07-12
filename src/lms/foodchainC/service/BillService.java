package lms.foodchainC.service;

import java.util.ArrayList;

import lms.foodchainC.dao.Bill_DBHelper;
import lms.foodchainC.data.CaseData;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-15
 * @description 账单service
 * @changeLog
 */
public class BillService extends BaseService {
	private static Bill_DBHelper bdb;
	public static final int ID = 0;
	public static final int CUSTOMER = 1;
	public static final int SEAT = 2;
	public static final int TABLE = 3;
	public static final int TYPE = 4;
	public static final int ISDONE = 5;

	private static Context context;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		context = this.getApplicationContext();
		bdb = new Bill_DBHelper(context);
	}

	public static boolean getOrderList(ArrayList<CaseData> list) {
		return bdb.getOrderList(list);
	}

}
