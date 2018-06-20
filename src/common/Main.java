package common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ifeng.GetifengUrls;
import ifeng.MatchAndShow;
import tencent.Filtrate;
import zhongxinwang.Match;

public class Main {
	public static void main(String[] args) throws Exception {
		
				
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start = sdf.parse("2015-01-01");
		Date end = sdf.parse("2016-06-17");
		List<Date> lists = DateSplit.dateSplit(start, end);   
		
		if (!lists.isEmpty()) {
			for (Date date : lists) {
				
//				System.out.println(sdf.format(date).toString());
				//凤凰网
				for(int i=1;i<=10;i++) {
					System.out.println(sdf.format(date).toString());
					System.out.println(i);
					String[] array = sdf.format(date).toString().split("-");
					
					
					String content = "http://news.ifeng.com/listpage/11502/"+array[0]+array[1]+array[2]+"/"+i+"/rtlist.shtml";
					GetifengUrls.parserHtml(content); 
					
					
				}
//				String[] array = sdf.format(date).toString().split("-");
//				String content = "http://www.chinanews.com/scroll-news/" + array[0] + "/" + array[1] + array[2]
//						+ "/news.shtml";
//				Match match = new Match();
//				match.parserHtml(content); // 中国新闻网
//				Filtrate.filtrate(sdf.format(date).toString()); // 腾讯新闻

			}
		}
	}
}
	

