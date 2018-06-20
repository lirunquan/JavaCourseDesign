package ifeng;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import common.MysqlOp;

public class MatchAndShow {

	public static String changeDateType(String date) {
		String dates = date.replace("年", "-").replace("月", "-").replace("日", " ").replaceAll("  ", " ").replaceAll("/",
				"-");
		Pattern p = Pattern.compile("(\\d{4})-(\\d{1,2})-(\\d{1,2})[ ](\\d{1,2})[:](\\d{1,2})");
		Matcher m = p.matcher(dates);
		if (m.find()) {
			return m.group().toString();
		} else
			return "";
	}

	public static void getContent(String url) {
		
		
		
		MysqlOp mysqlOp = new MysqlOp();
		String operation = "";

		operation = "CREATE DATABASE IF NOT EXISTS crawler";
		mysqlOp.Operation(operation);
		operation = "USE crawler";
		mysqlOp.Operation(operation);
		operation = "CREATE TABLE IF NOT EXISTS datas ( ID INT(8) NOT NULL AUTO_INCREMENT, word TINYTEXT, title TEXT, time DATETIME, url VARCHAR(255) NOT NULL, source TINYTEXT, article TEXT, PRIMARY KEY (ID))";
		mysqlOp.TableOperation("crawler", operation);
		
		
		

		String source = "凤凰资讯";
		try {
			if(Jsoup.connect(url).get()!=null) {
			Document doc = Jsoup.connect(url).get();
			if (doc.getElementsByClass("left").first() != null) {
				Elements left = doc.getElementsByClass("left");
				if (left.first().getElementsByTag("h1").first() != null) {
					String title = left.first().getElementsByTag("h1").first().text();
					String time = left.first().getElementsByClass("ss01").first().text();
					Elements contents = left.first().getElementsByClass("js_selection_area").select("p");
					StringBuffer buffer = new StringBuffer();
					for (Element e : contents) {
						buffer.append(e.text() + "\n");
					}
					String content = buffer.toString();

					System.out.println(title); // 标题
				
					java.io.File file = new java.io.File("keyword.txt");
					Scanner input = new Scanner(file);
					while (input.hasNext()) {
						String word = input.nextLine();
						if (title.indexOf(word) != -1) {

							System.out.println(word); // 关键词
							System.out.println(title); // 标题
							System.out.println(url); // url
							System.out.println(changeDateType(time)); // 发布时间
							System.out.println(source);// 来源
							System.out.print(content); // 正文
							System.out.println("=============================================================");
							
							
							
							 if(mysqlOp.isDataExists("crawler ", "SELECT url FROM datas ",
									 url, "url")) {
									 operation = "UPDATE datas SET title = '"+title+"' ,"+
									 "time = '"+changeDateType(time)+"' , "+
									 "url = '"+url+"' , "+
									 "word = '"+word+"' , "+
									 "source = '"+source+"' , "+
									 "article = '"+content+"' "+
									 "WHERE url = '"+url+"' ";
									 mysqlOp.TableOperation("crawler ", operation);
									 }
									 else {
									 operation = "INSERT INTO datas ( word, title, time, url, source, article) VALUES "+
									 "( \'"+word+"\'"+", "+
									 "\'"+ title +"\'"+","+
									 "\'"+ changeDateType(time) +"\'"+", "+
									 "\'"+url+"\'"+", "+
									 "\'"+source+"\'"+", "+
									 "\'"+content+"\')";
									 mysqlOp.TableOperation("crawler ", operation);
									 }
							 break;
						}
					}
					input.close();

				}
			}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
