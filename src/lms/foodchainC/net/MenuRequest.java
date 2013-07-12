package lms.foodchainC.net;

import org.json.JSONException;

public class MenuRequest extends JSONRequest {
	private int day = 0;

	public MenuRequest(int day) {
		this.method = NetMessageType.MENU;
		this.day = day;
	}

	@Override
	public String make() {
		try {
			holder.put("method", method);
			holder.put("day", day);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return holder.toString();
	}
}
