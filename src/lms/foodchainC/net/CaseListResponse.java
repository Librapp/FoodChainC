package lms.foodchainC.net;

import java.util.ArrayList;

import lms.foodchainC.data.CaseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2013-6-14
 * @description 菜肴列表响应类
 * @changeLog
 */
public class CaseListResponse {

	private ArrayList<CaseData> list;

	public CaseListResponse(ArrayList<CaseData> list) {
		this.list = list;
	}

	public JSONArray make() {
		JSONArray array = new JSONArray();
		for (CaseData c : list) {
			JSONObject item = new JSONObject();
			try {
				item.put("id", c.id);
				item.put("caseId", c.caseId);
				item.put("name", c.name);
				item.put("billId", c.billId);
				item.put("cookerId", c.cookerId);
				item.put("cookTime", c.cookTime);
				item.put("count", c.count);
				item.put("family", c.family);
				item.put("orderId", c.orderId);
				item.put("state", c.state);
				item.put("style", c.style);
				item.put("time", c.time);
				item.put("type", c.type);
				item.put("waiterId", c.waiterId);
				item.put("weekday", c.weekday);
				item.put("waitTime", c.waitTime);
				item.put("intro", c.intro);
				item.put("mark", c.mark);
				item.put("message", c.message);
				item.put("orderTime", c.orderTime);
				item.put("picPath", c.picPath);
				item.put("price", c.price);
				item.put("special", c.special);
				array.put(item);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return array;
	}

}
