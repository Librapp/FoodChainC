package lms.foodchainC.net;

import lms.foodchainC.data.CustomerData;

import org.json.JSONException;
import org.json.JSONObject;

public class SetSeatRequest extends JSONRequest {

	@Override
	public String make() {
		JSONObject holder = new JSONObject();
		try {
			holder.put("method", "Seat");
			holder.put("userId", CustomerData.current().id);
			holder.put("time", CustomerData.current().comeTime);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return holder.toString();
	}

}
