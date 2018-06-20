package ifeng;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetifengUrls {
	public static void parserHtml(String content) throws MalformedURLException, IOException {
		Document doc = Jsoup.parse(new URL(content).openStream(), "GBK", content);
		Elements links = doc.getElementsByClass("newsList").first().select("li");
		for (Element e : links) {
			MatchAndShow.getContent(e.select("a").attr("href"));
		}
	}
}
