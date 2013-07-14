package lms.foodchainC.net;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author 李梦思
 * @version 1.0
 * @createTime 2013-5-22
 * @description 请求响应基类
 * @changeLog
 */
public class JSONResponse {
	private static JSONObject data = new JSONObject();

	public static String make() {
		try {
			data.put("msg", "成功");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data.toString();
	};

}
