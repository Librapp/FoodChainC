package lms.foodchainC.net;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lms.foodchainC.dao.Case_DBHelper;
import lms.foodchainC.dao.Table_DBHelper;
import lms.foodchainC.data.CaseData;
import lms.foodchainC.data.CaseStyleData;
import lms.foodchainC.data.MessageData;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.data.SeatData;
import lms.foodchainC.data.TableData;
import lms.foodchainC.data.TableStyleData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author 李梦思
 * @description JSONParser
 * 
 */
public class JSONParser {
	private static String msg = "";
	public static final String NODATARETURN = "获取数据失败";

	/** 通用解析 */
	public static String result(String result) {
		msg = "";
		try {
			if (result != null) {
				JSONObject data = new JSONObject(result);
				msg = data.getString("msg");
			} else {
				msg = NODATARETURN;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}

	/**
	 * 解析MenuData
	 * 
	 * @param cdb
	 */
	public static String menuDataParse(String result, Case_DBHelper cdb) {
		msg = "";
		try {
			if (result != null) {
				JSONObject data = new JSONObject(result);
				int code = data.getInt("code");
				msg = data.getString("msg");
				if (code == 0) {
					JSONArray array = data.optJSONArray("caseStyleList");
					ArrayList<CaseStyleData> list = new ArrayList<CaseStyleData>();
					for (int i = 0; i < array.length(); i++) {
						JSONObject item = array.getJSONObject(i);
						CaseStyleData csd = new CaseStyleData();
						msg = caseStyleDataParse(item.toString(), csd);
						if (msg.equals("")) {
							list.add(csd);
						} else
							return msg;
					}
					cdb.insertCaseStyleList(list);
				}
			} else {
				msg = NODATARETURN;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}

	/** 解析CaseStyleData */
	public static String caseStyleDataParse(String result, CaseStyleData csd) {
		msg = "";
		try {
			if (result != null) {
				JSONObject data = new JSONObject(result);
				csd.id = data.optInt("id");
				csd.name = data.optString("name");
				csd.startTime = data.optString("startTime");
				csd.endTime = data.optString("endTime");
				csd.weekday = data.optInt("weekday");
				JSONArray array = data.optJSONArray("caseList");
				List<CaseData> list = new ArrayList<CaseData>();
				for (int i = 0; i < array.length(); i++) {
					JSONObject item = array.getJSONObject(i);
					CaseData c = new CaseData();
					msg = caseDataParse(item.toString(), c);
					if (msg.equals("")) {
						list.add(c);
					} else
						return msg;
				}
				csd.setList(list);
			} else {
				msg = NODATARETURN;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}

	/** 解析CaseData */
	public static String caseDataParse(String result, CaseData cd) {
		msg = "";
		try {
			if (result != null) {
				JSONObject data = new JSONObject(result);
				cd.id = data.optInt("id");
				cd.caseId = data.optInt("caseId");
				cd.billId = data.optInt("billId");
				cd.family = data.optInt("family");
				cd.cookerId = data.optInt("cookerId");
				cd.waiterId = data.optInt("waiterId");
				cd.type = data.optInt("type");
				cd.state = data.optInt("state");
				cd.style = data.optInt("style");
				cd.cookTime = data.optInt("cookTime");
				cd.orderId = data.optInt("orderId");
				cd.name = data.optString("name");
				cd.picPath = data.optString("picPath");
				cd.intro = data.optString("intro");
				cd.orderTime = data.optString("orderTime");
				cd.waitTime = data.optString("waitTime");
				cd.message = data.optString("message");
				cd.mark = data.optDouble("mark");
				cd.price = data.optDouble("price");
				cd.special = data.optDouble("special");
			} else
				msg = NODATARETURN;
		} catch (JSONException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}

	/** 解析restaurantInfo */
	public static String restaurantInfoParse(String result,
			RestaurantData current) {
		msg = "";
		if (result != null)
			try {
				JSONObject data = new JSONObject(result);
				current.name = data.optString("name");
				current.headPic = data.optString("headPic");
				current.address = data.optString("address");
				current.tel = data.optString("tel");
				current.sms = data.optString("sms");
				current.opentime = data.optString("opentime");
				current.email = data.optString("email");
				current.id = data.optInt("id");
				current.credit = data.optInt("credit");
				current.state = data.optInt("state");
			} catch (JSONException e) {
				e.printStackTrace();
				msg = e.getMessage();
			}
		else
			msg = NODATARETURN;
		return msg;
	}

	/** 解析hallInfo */
	public static String hallInfoParse(String result, Table_DBHelper tdb) {
		msg = "";
		try {
			JSONObject data = new JSONObject(result);
			List<TableStyleData> tableStyleList = new ArrayList<TableStyleData>();
			JSONArray array = data.optJSONArray("tableStyleList");
			for (int i = 0; i < data.length(); i++) {
				JSONObject item = array.getJSONObject(i);
				TableStyleData ts = new TableStyleData();
				msg = tableStyleDataParse(item.toString(), ts);
				if (msg.equals(""))
					tableStyleList.add(ts);
				else
					return msg;
			}
			tdb.insertTableStyleList(tableStyleList);
		} catch (JSONException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}

	/** 解析tableStyleData */
	public static String tableStyleDataParse(String result, TableStyleData ts) {
		msg = "";
		try {
			JSONObject data = new JSONObject(result);
			ts.id = data.optString("id");
			ts.icon = data.optString("icon");
			ts.pic = data.optString("pic");
			ts.seatCount = data.optInt("seatCount");
			ts.tableCount = data.optInt("tableCount");
			JSONArray array = data.optJSONArray("tableList");
			ArrayList<TableData> list = new ArrayList<TableData>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject item = array.getJSONObject(i);
				TableData td = new TableData();
				msg = tableDataParse(item.toString(), td);
				if (msg.equals(""))
					list.add(td);
				else
					return msg;
			}
			ts.setTable(list);
		} catch (JSONException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}

	/** 解析tableData */
	public static String tableDataParse(String result, TableData td) {
		msg = "";
		try {
			JSONObject data = new JSONObject(result);
			td.id = data.optString("id");
			td.icon = data.optString("icon");
			td.seatCount = data.optInt("seatCount");
			JSONArray array = data.optJSONArray("seatList");
			ArrayList<SeatData> list = new ArrayList<SeatData>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject item = array.getJSONObject(i);
				SeatData sd = new SeatData();
				msg = seatDataParse(item.toString(), sd);
				if (msg.equals(""))
					list.add(sd);
				else
					return msg;
			}
			td.setSeat(list);
		} catch (JSONException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}

	/** 解析seatData */
	private static String seatDataParse(String result, SeatData sd) {
		msg = "";
		try {
			JSONObject data = new JSONObject(result);
			sd.seatId = data.optString("seatId");
			sd.tableId = data.optString("tableId");
			sd.styleId = data.optString("styleId");
			sd.state = data.optInt("state");
			sd.customerId = data.optInt("customerId");
			sd.customerName = data.optString("customerName");
			sd.customerPic = data.optString("customerPic");
			sd.customerURL = data.optString("customerURL");
		} catch (JSONException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}

	/** 解析messageData */
	public static String messageDataParse(String result, MessageData md) {
		msg = "";
		String content = "";
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			JSONObject jData = new JSONObject(result);
			jData.getInt("id");
			jData.getInt("type");
			content = jData.getString("content");
			MessageData c = new MessageData();
			c.content = content;
			c.time = sdf.format(d);
			// switch (type) {
			// case 0:
			// Self.current().getCustomer().get(Self.current().cMap.get(id))
			// .getMessage().add(c);
			// break;
			// case 1:
			// Self.current().getWaiter().get(Self.current().wMap.get(id))
			// .getMessage().add(c);
			// break;
			// case 2:
			// Self.current().getCooker()
			// .get(Self.current().cookerMap.get(id)).getMessage()
			// .add(c);
			// break;
			// case 3:
			// Self.current().getCustomer().get(Self.current().cMap.get(id));
			// break;
			// case 4:
			// Self.current().getCustomer().get(Self.current().cMap.get(id))
			// .getMessage().add(c);
			// break;
			// default:
			// break;
			// }
		} catch (JSONException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}

}
