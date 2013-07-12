package lms.foodchainC.net;

import org.json.JSONObject;

/**
 * 
 * @author 李梦思
 * @description JSONParser
 * 
 */
public abstract class JSONParser {
	public String msg = "";
	public JSONObject data;

	protected abstract boolean parse(String s);
}
