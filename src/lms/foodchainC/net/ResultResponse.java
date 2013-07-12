package lms.foodchainC.net;

import org.json.JSONException;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2013-6-18
 * @description 结果响应类
 * @changeLog
 */
public class ResultResponse extends JSONResponse {
	@Override
	public String make() {
		try {
			data.put("msg", "成功");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data.toString();
	}
}
