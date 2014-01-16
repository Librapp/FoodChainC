package lms.foodchainC.net;

import lms.foodchainC.dao.Case_DBHelper;
import android.content.Context;
import android.os.AsyncTask;

/**
 * 
 * @author Luke
 * @description 获取菜单
 * @creatTime 2013/12/29
 */
public class GetMenuTask extends AsyncTask<Object, Void, String> {

	@Override
	protected String doInBackground(Object... params) {
		Context context = (Context) params[0];
		String url = (String) params[1];
		String result = NetUtil.executePost(context,
				JSONRequest.menuData(), url);
		return JSONParser.menuDataParse(result, new Case_DBHelper(context));
	}

}
