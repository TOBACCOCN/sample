package com.example.demo.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author zhangyonghong
 * @date 2018.8.30
 *
 */
public class FindMinimumMinutes {
	
	public static void main(String[] args) {
		int input;
		try {
			input = Integer.parseInt(args[0]);
		} catch (Exception e) {
			System.out.println("only Integer supported!");
			return;
		}
		
		Random random = new Random();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < input; i++) {
			int hour = random.nextInt(25);
			int minute = random.nextInt(61);
			String hourStr = hour < 10 ? "0" + hour : hour + "";
			String minuteStr = minute < 10 ? "0" + minute : minute + "";
			list.add(hourStr + ":" + minuteStr);
		}
		System.out.println(list);
		
		String[] array = new String[list.size()];
		list.toArray(array);
		System.out.println(findMinimumMinutes(array));
	}
	
//	Given a list of 24-hour clock time points in "Hour:Minutes" format, find the minimum minutes difference between any two time points in the list.
//
//	1.The number of time points in the given list is at least 2 and won't exceed 20000.
//	2.The input time is legal and ranges from 00:00 to 23:59.
//
//	Input: ["23:59","00:00"]
//	Output: 1
	public static List<Integer> findMinimumMinutes(String[] array) {
		long start = System.currentTimeMillis();
		
		String[] newArray = new String[array.length];
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		for (int i = 0; i < array.length; i++) {
			String[] arr = array[i].split(":");
			newArray[i] = arr[0] + arr[1];
			List<Integer> list = new ArrayList<Integer>();
			if (map.containsKey(newArray[i])) {
				list = map.get(newArray[i]);
			}
			list.add(i);
			map.put(newArray[i], list);
		}
		Arrays.sort(newArray);
		List<Integer> returnList = map.get(newArray[0]);
		long end = System.currentTimeMillis();
		System.out.println("cost: " + (end - start) + "ms");
		return returnList;
	}

}
