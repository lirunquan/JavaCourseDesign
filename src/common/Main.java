package common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import tencent.Filtrate;
import zhongxinwang.Match;

public class Main {
	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start = sdf.parse("2014-01-01");
		Date end = sdf.parse("2014-04-09");
		List<Date> lists = DateSplit.dateSplit(start, end);   //腾讯到14-04-20
		
		if (!lists.isEmpty()) {
			for (Date date : lists) {
				System.out.println(sdf.format(date).toString());
//				String[] array = sdf.format(date).toString().split("-");
//				String content = "http://www.chinanews.com/scroll-news/" + array[0] + "/" + array[1] + array[2]
//						+ "/news.shtml";
//				Match match = new Match();
//				match.parserHtml(content); // 中国新闻网
				Filtrate.filtrate(sdf.format(date).toString()); // 腾讯新闻

			}
		}
	}
}