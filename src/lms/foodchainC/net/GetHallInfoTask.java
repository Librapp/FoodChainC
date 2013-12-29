package lms.foodchainC.net;

import java.util.List;

import lms.foodchainC.data.TableStyleData;
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
		List<TableStyleData> tableStyleList = (List<TableStyleData>) params[2];
		String result = NetUtil.executeGet(context,
				JSONRequest.hallInfoRequest(), url);
		return JSONParser.hallInfoParse(result, tableStyleList);
	}

}
