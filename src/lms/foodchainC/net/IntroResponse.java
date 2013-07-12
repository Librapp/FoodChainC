package lms.foodchainC.net;

import lms.foodchainC.data.Self;

import org.json.JSONException;
import org.json.JSONObject;

public class IntroResponse extends JSONResponse {

	@Override
	public String make() {
		JSONObject holder = new JSONObject();

		try {
			holder.put("tel", Self.current().tel);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return holder.toString();
	}

}
