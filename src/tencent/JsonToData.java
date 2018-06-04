package tencent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonToData {
	public static JSONArray jSONStrToJSONObject(String COMPLEX_JSON_STR) {
		JSONObject jsonObject = JSON.parseObject(COMPLEX_JSON_STR);
		return jsonObject.getJSONArray("data");
	}
}
