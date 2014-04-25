package lms.foodchainC.net;

import java.util.List;

import lms.foodchainC.dao.Bill_DBHelper;
import lms.foodchainC.data.CaseData;
import lms.foodchainC.data.SeatData;
import lms.foodchainC.data.UserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-7-8
 * @description JSON请求工具类
 */
public class JSONRequest {
	public static final String METHOD = "method";
	public static final String RESTAURANTINFO = "restaurantInfo";
	public static final String MENU = "menu";
	public static final String MESSAGE = "message";
	public static final String CASEDETAIL = "caseDetail";
	public static final String HALLINFO = "hallInfo";
	public static final String ORDER = "order";
	public static final String SETSEAT = "setSeat";

	public static String restaurantInfo() {
		JSONObject holder = new JSONObject();
		try {
			holder.put(METHOD, RESTAURANTINFO);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return holder.toString();
	}

	public static String caseDetail() {
		JSONObject holder = new JSONObject();
		try {
			holder.put(METHOD, CASEDETAIL);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return holder.toString();
	}

	public static String hallInfo() {
		JSONObject holder = new JSONObject();
		try {
			holder.put(METHOD, HALLINFO);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return holder.toString();
	}

	public static String menuData() {
		JSONObject holder = new JSONObject();
		try {
			holder.put(METHOD, MENU);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return holder.toString();
	}

	public static String order(Bill_DBHelper bdb) {
		JSONObject data = new JSONObject();
		try {
			data.put(METHOD, ORDER);
			data.putOpt("id", UserData.self().id);
			data.putOpt("name", UserData.self().name);
			data.putOpt("address", UserData.self().address);
			List<CaseData> list = bdb.getOrderList();
			JSONArray array = new JSONArray();
			for (CaseData c : list) {
				array.put(caseData(c));
			}
			data.putOpt("caseList", array);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data.toString();
	}

	private static JSONObject caseData(CaseData c) {
		JSONObject data = new JSONObject();
		try {
			data.putOpt("id", c.id);
			data.putOpt("name", c.name);
			data.putOpt("message", c.message);
			data.putOpt("orderTime", c.orderTime);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;
	}

	/** 选座位 */
	public static String setSeat(SeatData seatData) {
		JSONObject data = new JSONObject();
		try {
			data.put(METHOD, SETSEAT);
			data.putOpt("userId", UserData.self().id);
			data.putOpt("userName", UserData.self().name);
			data.putOpt("peopleCount", UserData.self().peoplecount);
			data.putOpt("seatId", seatData.seatId);
			data.putOpt("tableId", seatData.tableId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data.toString();
	};
}
