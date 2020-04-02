package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

/**
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 * Example:
 * Given nums = [2, 7, 11, 15], target = 9,
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 *
 * @author zhangyonghong
 * @date 2020.2.25
 */
@Slf4j
public class TwoSum {

    /**
     * 封装校验数组中两数之和为指定值的测试方法
     *
     * @param count  数组中元素个数
     * @param target 两数之和目标值
     * @param max    数组中元素最大值
     * @return 给定数组中两数之和为指定值的元素的索引集合
     */
    private List<int[]> twoSum(int count, int target, int max) {
        // 构造数组里面的元素
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int num = random.nextInt(max + 1);
            list.add(num);
        }

        Integer[] array = new Integer[count];
        list.toArray(array);

        List<int[]> indexPairs = doTwoSum(array, target);
        log.info(">>>>> ------VALUE------");
        for (int[] indexArray : indexPairs) {
            log.info(">>>>> [{}, {}]", array[indexArray[0]], array[indexArray[1]]);
        }
        return indexPairs;
    }

    /**
     * @param array  给定的数组
     * @param target 两数之和目标值
     * @return 给定数组中两数之和为指定值的元素的索引集合
     */
    private List<int[]> doTwoSum(Integer[] array, int target) {
        long start = System.currentTimeMillis();

        // map 中 key 为目标和值 target 与遍历数组时当前元素值之差，value 为遍历时元素索引集合
        // 比如数组前三个元素是 1，1，4，target 是 5，遍历完第二个元素时，map 中数据为 {4: [0, 1]}
        // 遍历第三个元素时，即是针对 map 中已有 key 的符合条件（和值为 target）的第二个元素
        // 那么 returnList 就可以将 [0, 2] 与 [1, 2] 存入其中
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<int[]> returnList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            List<Integer> list = new ArrayList<>();
            if (map.containsKey(target - array[i])) {
                list = map.get(target - array[i]);
            }
            list.add(i);
            map.put(target - array[i], list);

            if (map.containsKey(array[i])) {
                for (Integer index : map.get(array[i])) {
                    returnList.add(new int[]{index, i});
                }
            }
        }

        long end = System.currentTimeMillis();
        log.info(">>>>> COST_TIME: [{}] MS", end - start);
        return returnList;
    }

    @Test
    public void twoSum() {
        int count = 100000;
        int target = 324234;
        int max = 1000000;
        List<int[]> list = twoSum(count, target, max);
        log.info(">>>>> ------INDEX------");
        list.forEach(array -> log.info(">>>>> [{}, {}]", array[0], array[1]));
    }

}
