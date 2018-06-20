package zhongxinwang;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import common.MysqlOp;

import java.util.Scanner;

public class Match {
	public static void parserHtml(String content) throws MalformedURLException, IOException {
		Document doc = Jsoup.parse(new URL(content).openStream(), "GBK", content);
		Elements links = doc.getElementsByClass("content_list").select("li");
		

		for (Element e : links) {
			if (!e.select("div.dd_bt").text().equals("")) {
				if (e.select("div.dd_lm").toString() != null) {
					if (((e.select("div.dd_lm").text()).indexOf("图片") >= 0)
							|| ((e.select("div.dd_lm").text()).indexOf("视频") >= 0))
					{
						continue;
					}
					java.io.File file = new java.io.File("keyword.txt");
					Scanner input = new Scanner(file);
					while (input.hasNext()) {
						String word = input.nextLine();
						if ((e.select("div.dd_bt").text()).indexOf(word) != -1) {
							Show show = new Show();
							show.getContent(
									("http://www.chinanews.com" + e.select("div.dd_bt").select("a").attr("href")),word);
							System.out.println("=============================================================");
							break;
						}
					}
					input.close();
				}
			} else
				continue;
		}
	}
}
