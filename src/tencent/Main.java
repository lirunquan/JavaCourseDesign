package tencent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Main {
	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start = sdf.parse("2018-02-28");
		Date end = sdf.parse("2018-03-29");
		List<Date> lists = DateSplit.dateSplit(start, end);
		if (!lists.isEmpty()) {
			for (Date date : lists) {
//				System.out.println(sdf.format(date).toString());
				System.out.println(sdf.format(date).toString());
				Filtrate.filtrate(sdf.format(date).toString());
			}
		}
	}
}
