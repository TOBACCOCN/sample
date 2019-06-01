package com.example.demo.utc;

import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

public class UtcTest {
	
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.setTime(TimeUtil.tzTime2Date(TimeUtil.getUtcTime()));
		System.out.println(TimeUtil.date2TzTime(calendar.getTime()));
		calendar.setTime(TimeUtil.tzTime2Date("20180601T071027Z"));
		calendar.add(Calendar.HOUR, 8);
		System.out.println(TimeUtil.date2TzTime(calendar.getTime()));
		System.out.println("2".compareTo("1"));
		Map<String, String> map = new TreeMap<String, String>();
		map.put("a", "a");
		map.put("b", "b");
		map.put("c", "c");
		map.put("d", "d");
		Collection<String> values = map.values();
		for (String string : values) {
			System.out.println(string);
		}
	}

}
