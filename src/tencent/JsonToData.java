package tencent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonToData {
	public static JSONArray testComplexJSONStrToJSONObject(String COMPLEX_JSON_STR) {
		JSONObject jsonObject = JSON.parseObject(COMPLEX_JSON_STR);
		return jsonObject.getJSONArray("data");
	}
}
