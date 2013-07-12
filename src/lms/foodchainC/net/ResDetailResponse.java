package lms.foodchainC.net;

import lms.foodchainC.data.Self;

import org.json.JSONException;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2013-5-22
 * @description 餐厅详情响应类
 * @changeLog
 */
public class ResDetailResponse extends JSONResponse {
	@Override
	public String make() {
		try {
			data.put("name", Self.current().name);
			data.put("id", Self.current().id);
			data.put("headPic", Self.current().headPic);
			data.put("address", Self.current().address);
			data.put("tel", Self.current().tel);
			data.put("credit", Self.current().credit);
			data.put("state", Self.current().state);
			data.put("email", Self.current().email);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data.toString();
	}
}
