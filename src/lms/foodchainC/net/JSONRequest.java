package lms.foodchainC.net;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-7-8
 * @description JSON请求工具类
 */
public class JSONRequest {
	private static final String METHOD = "method";
	private static final String RESTAURANTINFO = "restaurantInfo";
	private static final String MENU = "menu";
	public static final String MESSAGE = "message";
	private static final String CASEDETAIL = "caseDetail";
	public static final String HALLINFO = "hallInfo";

	public static String menuRequest(int styleId) {
		JSONObject holder = new JSONObject();
		try {
			holder.put(METHOD, MENU);
			holder.put("styleId", styleId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return holder.toString();
	}

	public static String restaurantInfoRequest() {
		JSONObject holder = new JSONObject();
		try {
			holder.put(METHOD, RESTAURANTINFO);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return holder.toString();
	}

	public static String caseDetailRequest() {
		JSONObject holder = new JSONObject();
		try {
			holder.put(METHOD, CASEDETAIL);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return holder.toString();
	}

	public static String hallInfoRequest() {
		JSONObject holder = new JSONObject();
		try {
			holder.put(METHOD, HALLINFO);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return holder.toString();
	}

	public static String menuDataRequest() {
		JSONObject holder = new JSONObject();
		try {
			holder.put(METHOD, MENU);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return holder.toString();
	}
}
