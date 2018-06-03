package zhongxinwang;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import common.MysqlOp;
import tencent.GetContent;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Show {
	public String changeDateType(String date) {
		String dates = date.replace("年", "-").replace("月", "-").replace("日", " ").replaceAll("  ", " ").replaceAll("/",
				"-");
		Pattern p = Pattern.compile("(\\d{4})-(\\d{1,2})-(\\d{1,2})[ ](\\d{1,2})[:](\\d{1,2})");
		Matcher m = p.matcher(dates);
		if (m.find()) {
			return m.group().toString();
		} else
			return "";
	}
	

	
	
	
	
	
	
	public void getContent(String url,String keyword) {
		
		MysqlOp mysqlOp = new MysqlOp();
		String operation = "";

		operation = "CREATE DATABASE IF NOT EXISTS crawler";
		mysqlOp.Operation(operation);
		operation = "USE crawler";
		mysqlOp.Operation(operation);
		operation = "CREATE TABLE IF NOT EXISTS datas ( ID INT(8) NOT NULL AUTO_INCREMENT, word TINYTEXT, title TEXT, time DATETIME, url VARCHAR(255) NOT NULL, source TINYTEXT, article TEXT, PRIMARY KEY (ID))";
		mysqlOp.TableOperation("crawler", operation);
		
		
		
		String source = "中国新闻网";
		try {
			Document doc = Jsoup.connect(url).get();
			Elements con_left = doc.getElementsByClass("con_left");
			String title = con_left.first().getElementsByTag("h1").first().text();
			String time = con_left.first().getElementsByClass("left-t").first().text();
			Elements contents = con_left.first().getElementsByClass("left_zw").select("p");
			StringBuffer buffer = new StringBuffer();
			for (Element e : contents) {
				buffer.append(e.text() + "\n");
			}
			String content = buffer.toString();
            
			
			System.out.println(keyword); // 关键词
			System.out.println(title); // 标题
			System.out.println(url); // url
			System.out.println(changeDateType(time)); // 发布时间
			System.out.println(source);// 来源
			System.out.print(content); // 正文
			
			
			
			
			
			 if(mysqlOp.isDataExists("crawler ", "SELECT url FROM datas ",
			 url, "url")) {
			 operation = "UPDATE datas SET title = '"+title+"' ,"+
			 "time = '"+changeDateType(time)+"' , "+
			 "url = '"+url+"' , "+
			 "word = '"+keyword+"' , "+
			 "source = '"+source+"' , "+
			 "article = '"+content+"' "+
			 "WHERE url = '"+url+"' ";
			 mysqlOp.TableOperation("crawler ", operation);
			 }
			 else {
			 operation = "INSERT INTO datas ( word, title, time, url, source, article) VALUES "+
			 "( \'"+keyword+"\'"+", "+
			 "\'"+ title +"\'"+","+
			 "\'"+ changeDateType(time) +"\'"+", "+
			 "\'"+url+"\'"+", "+
			 "\'"+source+"\'"+", "+
			 "\'"+content+"\')";
			 mysqlOp.TableOperation("crawler ", operation);
			 }
			
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}