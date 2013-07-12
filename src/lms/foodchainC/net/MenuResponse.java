package lms.foodchainC.net;

import java.util.ArrayList;

import lms.foodchainC.data.CaseTimeData;

import org.json.JSONException;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2013-6-13
 * @description 菜单响应类
 * @changeLog
 */
public class MenuResponse extends JSONResponse {
	private ArrayList<CaseTimeData> lct;

	public MenuResponse(ArrayList<CaseTimeData> lct) {
		this.lct = lct;
	}

	@Override
	public String make() {
		try {
			data.put("caseTimeData", new CaseTimeDataListResponse(lct).make());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data.toString();
	}
}
