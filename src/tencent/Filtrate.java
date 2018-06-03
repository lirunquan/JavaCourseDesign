package tencent;

import java.io.FileNotFoundException;
import java.util.Scanner;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.MysqlOp;

public class Filtrate {
	public static void filtrate(String date) throws FileNotFoundException {
		String source = "腾讯新闻";
		String string1 = "http://api.shenjian.io/";
		String string2 = "appid=" + "4da480d6c051141c6c1545aa97be02a8" + "&date=";
		String COMPLEX_JSON_STR = GetJson.request(string1, string2 + date);
		JSONArray data = JsonToData.jSONStrToJSONObject(COMPLEX_JSON_STR);

		MysqlOp mysqlOp = new MysqlOp();
		String operation = "";

		operation = "CREATE DATABASE IF NOT EXISTS crawler";
		mysqlOp.Operation(operation);
		operation = "USE crawler";
		mysqlOp.Operation(operation);
		operation = "CREATE TABLE IF NOT EXISTS datas ( ID INT(8) NOT NULL AUTO_INCREMENT, word TINYTEXT, title TEXT, time DATETIME, url VARCHAR(255) NOT NULL, source TINYTEXT, article TEXT, PRIMARY KEY (ID))";
		mysqlOp.TableOperation("crawler", operation);

		if (data != null) {
			int size = data.size();
			for (int i = 0; i < size; i++) {
				JSONObject jsonObject = data.getJSONObject(i);
				if (jsonObject.getString("column").indexOf("军事") >= 0
						|| jsonObject.getString("column").indexOf("图片") >= 0
						|| jsonObject.getString("column").indexOf("国际") >= 0
						|| GetContent.getContent(jsonObject.getString("url")).indexOf("匹配不成功") >= 0) {
					continue;
				} else {
					System.out.println("标题:" + jsonObject.getString("title"));

					java.io.File file = new java.io.File("keyword.txt");
					Scanner input = new Scanner(file);
					while (input.hasNext()) {
						String word = input.nextLine();
						if (jsonObject.getString("title").indexOf(word) != -1) {
							
							System.out.println("关键词:" + word);
							System.out.println("url:" + jsonObject.getString("url"));
							System.out.println("发布时间:" + jsonObject.getString("time"));
							System.out.println("来源:" + source);
							System.out.print("正文:" + "\n" + GetContent.getContent(jsonObject.getString("url")));
							System.out.println("=============================================================");

							 if(mysqlOp.isDataExists("crawler ", "SELECT url FROM datas ",
							 jsonObject.getString("url"), "url")) {
							 operation = "UPDATE datas SET title = '"+jsonObject.getString("title")+"' ,"+
							 "time = '"+jsonObject.getString("time")+"' , "+
							 "url = '"+jsonObject.getString("url")+"' , "+
							 "word = '"+word+"' , "+
							 "source = '"+source+"' , "+
							 "article = '"+GetContent.getContent(jsonObject.getString("url"))+"' "+
							 "WHERE url = '"+jsonObject.getString("url")+"' ";
							 mysqlOp.TableOperation("crawler ", operation);
							 }
							 else {
							 operation = "INSERT INTO datas ( word, title, time, url, source, article) VALUES "+
							 "( \'"+word+"\'"+", "+
							 "\'"+jsonObject.getString("title")+"\'"+","+
							 "\'"+jsonObject.getString("time")+"\'"+", "+
							 "\'"+jsonObject.getString("url")+"\'"+", "+
							 "\'"+source+"\'"+", "+
							 "\'"+GetContent.getContent(jsonObject.getString("url"))+"\')";
							 mysqlOp.TableOperation("crawler ", operation);
							 }
							break;
						}
					}
					input.close();
				}
			}
		}
	}
}