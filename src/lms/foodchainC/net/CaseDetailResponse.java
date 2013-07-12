package lms.foodchainC.net;

import lms.foodchainC.data.CaseData;
import lms.foodchainC.service.MenuService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CaseDetailResponse extends JSONResponse {
	private int cid = -1;

	public CaseDetailResponse(int cid) {
		this.cid = cid;
	}

	@Override
	public String make() {
		JSONObject data = new JSONObject();
		JSONArray pic = new JSONArray();

		try {
			CaseData c = new CaseData();
			c.id = cid;
			if (MenuService.getCaseDetail(c)) {
				JSONObject cai = new JSONObject();
				cai.put("id", c.id);
				cai.put("pic", c.picPath);
				cai.put("cooktime", c.cookTime);
				cai.put("intro", c.intro);
				cai.put("special", c.special);
				cai.put("type", c.type);
				cai.put("price", c.price);
				cai.put("name", c.name);
				cai.put("family", c.family);

				pic.put(cai);
				data.put("pic", pic);
				data.put("err", 0);
				data.put("msg", "查询成功");
			} else {
				data.put("err", 1);
				data.put("msg", "查询失败");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data.toString();
	}

}
