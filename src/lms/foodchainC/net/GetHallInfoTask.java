package lms.foodchainC.net;

import lms.foodchainC.dao.Table_DBHelper;
import android.content.Context;
import android.os.AsyncTask;

/**
 * 
 * @author Luke
 * @description 获取大厅状态
 * @creatTime 2013/12/29
 */
public class GetHallInfoTask extends AsyncTask<Object, Void, String> {

	@Override
	protected String doInBackground(Object... params) {
		Context context = (Context) params[0];
		String url = (String) params[1];
		String result = NetUtil.executePost(context, JSONRequest.hallInfo(),
				url);
		return JSONParser.hallInfoParse(result, new Table_DBHelper(context));
	}

}
