package tencent;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetContent {
	public static String getContent(String url) {
		String[] rule = new String[4];
		rule[0] = "p.text";
		rule[1] = "p[style=\"TEXT-INDENT:2em\"]";
		rule[2] = "p[style=\"TEXT-INDENT: 2em\"]";
		rule[3] = "p";
		try {
			Document doc = Jsoup.connect(url).get();
			int count = 0;
			Elements contents = doc.select(rule[0]);
			for (int i = 0; i < rule.length; i++) {
				contents = doc.select(rule[i]);
				if (!contents.isEmpty())
					break;
				count++;
			}
			if (count == rule.length)
				return "匹配不成功";
			else {
				StringBuffer buffer = new StringBuffer();
				for (Element e : contents) {
					buffer.append(e.text() + "\n");
				}
				String content = buffer.toString();
				return content;
			}
		} catch (IOException e) {
			return "获取正文内容异常！";
		}
	}
}
