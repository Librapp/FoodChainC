package lms.foodchainC.net;

import org.json.JSONObject;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-7-8
 * @description JSON请求基类
 */
public abstract class JSONRequest {
	public String method;
	public JSONObject holder = new JSONObject();

	public abstract String make();
}
