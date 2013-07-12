package lms.foodchainC.net;

import lms.foodchainC.data.RestaurantData;

import org.json.JSONException;
import org.json.JSONObject;

public class ResDetailParser extends JSONParser {

	@Override
	protected boolean parse(String s) {
		// TODO Auto-generated method stub
		return false;
	}

	public String parse(String result, RestaurantData current) {
		try {
			data = new JSONObject(result);
			current.name = data.optString("name");
			current.headPic = data.optString("headPic");
			current.address = data.optString("address");
			current.tel = data.optString("tel");
			current.sms = data.optString("sms");
			current.opentime = data.optString("opentime");
			current.email = data.optString("email");
			current.id = data.optString("id");
			current.credit = data.optInt("credit");
			current.state = data.optInt("state");
		} catch (JSONException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}

}
