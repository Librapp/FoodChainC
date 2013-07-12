package lms.foodchainC.net;

import org.json.JSONObject;

/**
 * 
 * @author 李梦思
 * @version 1.0
 * @createTime 2013-5-22
 * @description 请求响应基类
 * @changeLog
 */
public abstract class JSONResponse {
	protected JSONObject data = new JSONObject();

	public abstract String make();

}
