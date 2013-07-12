package lms.foodchainC.net;

import java.text.SimpleDateFormat;
import java.util.Date;

import lms.foodchainC.data.MessageData;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageParser extends JSONParser {

	@Override
	protected boolean parse(String s) {
		JSONObject jData = null;
		int id = -1;
		int type = -1;
		String content = "";
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			jData = new JSONObject(s);
			id = jData.getInt("id");
			type = jData.getInt("type");
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
		}
		return true;
	}

}
