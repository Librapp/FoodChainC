package lms.foodchainC.service;

import lms.foodchainC.dao.Message_DBHelper;
import lms.foodchainC.data.MessageData;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2013-6-18
 * @description 消息service
 * @changeLog
 */
public class MessageService extends BaseService {
	private static Message_DBHelper mdb;
	private static Context context;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		context = this.getApplicationContext();
		mdb = new Message_DBHelper(context);
	}

	public static boolean insertMessage(MessageData m) {
		return mdb.insert(m);
	}

}
