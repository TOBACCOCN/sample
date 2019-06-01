package com.example.demo.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author zhangyonghong
 * @date 2018.8.30
 *
 */
public class TwoSum {

	public static void main(String[] args) {
		int input;
		int target;
		try {
			input = Integer.parseInt(args[0]);
			target = Integer.parseInt(args[1]);
		} catch (Exception e) {
			System.out.println("only Integer supported!");
			return;
		}
		
		Random random = new Random();
		List<Integer> list = new ArrayList<Integer>();
		List<String> stringList = new ArrayList<String>();
		for (int i = 0; i < input; i++) {
			int num = random.nextInt(1000000);
			list.add(num);
			if (num < 10) {
				stringList.add("00000" + num);
			} else if (num < 100) {
				stringList.add("0000" + num);
			} else if (num < 1000) {
				stringList.add("000" + num);
			} else if (num < 10000) {
				stringList.add("00" + num);
			} else if (num < 100000) {
				stringList.add("0" + num);
			} else {
				stringList.add("" + num);
			}
		}
		System.out.println(stringList);
		
		Integer[] array = new Integer[input];
		list.toArray(array);
		
		for (int[] arr : doTwoSum(array, target)) {
			System.out.print("index: [" + arr[0] + ", " + arr[1] + "]======");
			System.out.println("value: [" + array[arr[0]] + ", " + array[arr[1]] + "]");
		}
	}

//	Given an array of integers, return indices of the two numbers such that they add up to a specific target.
//
//			You may assume that each input would have exactly one solution, and you may not use the same element twice.
//
//			Example:
//
//			Given nums = [2, 7, 11, 15], target = 9,
//
//			Because nums[0] + nums[1] = 2 + 7 = 9,
//			return [0, 1].
	private static List<int[]> doTwoSum(Integer[] array, int target) {
		long start = System.currentTimeMillis();
		
		Map<Integer,List<Integer>> map = new HashMap<Integer, List<Integer>>();
		List<int[]> returnList = new ArrayList<int[]>();
		for (int i = 0; i < array.length; i++) {
			List<Integer> list = new ArrayList<Integer>();
			if (map.containsKey(target - array[i])) {
				list = map.get(target - array[i]);
			}
			list.add(i);
			map.put(target - array[i], list);
			if (map.containsKey(array[i])) {
				for (Integer integer : map.get(array[i])) {
					returnList.add(new int[] {integer, i});
				}
			}
		}
		
		long end = System.currentTimeMillis();
		System.out.println("cost: " + (end - start) + "ms");
		return returnList;
	}
	
}
