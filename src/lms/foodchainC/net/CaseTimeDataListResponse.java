package lms.foodchainC.net;

import java.util.ArrayList;

import lms.foodchainC.data.CaseTimeData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2013-6-14
 * @description 时段菜单响应类
 * @changeLog
 */
public class CaseTimeDataListResponse {
	private ArrayList<CaseTimeData> lct;

	public CaseTimeDataListResponse(ArrayList<CaseTimeData> lct) {
		this.lct = lct;
	}

	public JSONArray make() {
		JSONArray array = new JSONArray();
		for (CaseTimeData ctd : lct) {
			JSONObject item = new JSONObject();
			try {
				item.put("id", ctd.id);
				item.put("name", ctd.name);
				item.put("startTime", ctd.startTime);
				item.put("endTime", ctd.endTime);
				item.put("weekday", ctd.weekday);
				item.put("caseList", new CaseListResponse(ctd.getList()).make());
				array.put(item);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return array;
	}

}
