package com.example.demo.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapTest {
	
	public static void main(String[] args) {
//		Map<String,String> hashMap = new HashMap<String, String>();
//		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//		list.add(hashMap);
//		System.out.println(list);
//		System.out.println(list.get(0));
//		hashMap.put("a", "b");
//		System.out.println(list);
//		System.out.println(list.get(0));
		Map<String, String> map = new HashMap<String, String>();
		map.put("20180813", "a");
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("20180813", "b");
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("20180813", "c");
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("20180814", "d");
		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("20180814", "e");
		Map<String, String> map6 = new HashMap<String, String>();
		map6.put("20180814", "f");
		List<Map<String,String>> arrayList = new ArrayList<Map<String, String>>();
		arrayList.add(map6);
		arrayList.add(map5);
		arrayList.add(map4);
		arrayList.add(map3);
		arrayList.add(map2);
		arrayList.add(map);
		List<Map<String,Object>> list = method(arrayList);
		System.out.println(list);

	}
	
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> method(List<Map<String, String>> lisT) {
		List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
		Map<String, Map<String, Object>> tableMap = new HashMap<String, Map<String, Object>>();
		for (Map<String, String> map : lisT) {
			for (String tableName : map.keySet()) {
				Map<String, Object> innerMap = null;
				if (tableMap.keySet().contains(tableName)) {
					innerMap = tableMap.get(tableName);
					innerMap.put("tableName", tableName);
					List<String> list = (List<String>) innerMap.get("inserts");
					list.add(map.get(tableName));
					innerMap.put("inserts",list);
				} else {
					innerMap = new HashMap<String, Object>();
					innerMap.put("tableName", tableName);
					ArrayList<String> list = new ArrayList<String>();
					list.add(map.get(tableName));
					innerMap.put("inserts", list);
					tableMap.put(tableName, innerMap);
					paramList.add(innerMap);
				}
			}
		}
		return paramList;
	}

}
