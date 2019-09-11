package com.example.sample.base;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * LeetCode 题解
 *
 * @author zhangyonghong
 * @date 2018.8.30
 */
public class LeetCode {

    private static Logger logger = LoggerFactory.getLogger(LeetCode.class);

    private static int assignCookies(int[] children, int[] cookies) {
        Arrays.sort(children);
        logger.info(">>>>> SORTED CHILDREN: {}", children);
        Arrays.sort(cookies);
        logger.info(">>>>> SORTED COOKIES: {}", cookies);
        int result = 0;
        int i = 0, j = 0;
        while (i < children.length && j < cookies.length) {
            if (children[i] <= cookies[j]) {
                result += 1;
                i++;
                j++;
            } else {
                j++;
            }
        }
        return result;
    }

    /**
     * Generate combinations.
     *
     * @param result result list.
     * @param prefix current prefix.
     * @param open   parentheses that can be opened.
     * @param close  parentheses that needs to be closed.
     */
    private static void combination(List<String> result, String prefix, int open, int close) {
        // If cannot open or close then we are done
        if (open == 0 && close == 0) {
            result.add(prefix);
        } else {
            // If can open parentheses generate combinations from it
            if (open > 0) {
                combination(result, prefix + '(', open - 1, close + 1);
            }
            // If can close parentheses generate combinations from it
            if (close > 0) {
                combination(result, prefix + ')', open, close - 1);
            }
        }
    }

    /**
     * Implement int sqrt(int x).
     * Compute and return the square root of x, where x is guaranteed to be a non-negative integer.
     * Since the return type is an integer, the decimal digits are truncated and only the integer part of the result is returned.
     *
     * @param x 待求平方根的正整数
     * @return 去除小数位的平方根
     */
    private static int sqrt(int x) {
        long n = x;
        while (n * n > x) {
            n = (n + x / n) >> 1;
        }
        return (int) n;
    }

    /**
     * 封装校验数组中两数之和为指定值的测试方法
     *
     * @param count  数组中元素个数
     * @param target 两数之和目标值
     * @param max    数组中元素最大值
     * @return 给定数组中两数之和为指定值的元素的索引集合
     */
    private static List<int[]> doTwoSum(int count, int target, int max) {
        // 构造数组里面的元素
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int num = random.nextInt(max + 1);
            list.add(num);
        }

        Integer[] array = new Integer[count];
        list.toArray(array);

        List<int[]> indexPairs = twoSum(array, target);
        logger.info(">>>>> ------VALUE------");
        for (int[] indexArray : indexPairs) {
            logger.info(">>>>> [{}, {}]", array[indexArray[0]], array[indexArray[1]]);
        }
        return indexPairs;
    }

    // Given an array of integers, return indices of the two numbers such that they add up to a specific target.
    // You may assume that each input would have exactly one solution, and you may not use the same element twice.
    // Example:
    // Given nums = [2, 7, 11, 15], target = 9,
    // Because nums[0] + nums[1] = 2 + 7 = 9,
    //         return [0, 1].
    // 从指定数组中找出两数之和为指定值的元素的索引
    // 此方案扩展成找出所有满足条件的索引
    // 如 array = [1, 1, 3, 4, 6, 8], target =7, return [[0, 4], [1, 4], [2,3]]

    /**
     * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
     * You may assume that each input would have exactly one solution, and you may not use the same element twice.
     * Example:
     * Given nums = [2, 7, 11, 15], target = 9,
     * Because nums[0] + nums[1] = 2 + 7 = 9,
     * return [0, 1].
     * 从指定数组中找出两数之和为指定值的元素的索引
     * 此方案扩展成找出所有满足条件的索引
     * 如 array = [1, 1, 3, 4, 6, 8], target =7, return [[0, 4], [1, 4], [2,3]]
     *
     * @param array  给定的数组
     * @param target 两数之和目标值
     * @return 给定数组中两数之和为指定值的元素的索引集合
     */
    private static List<int[]> twoSum(Integer[] array, int target) {
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
        logger.info(">>>>> COST_TIME: [{}] ms", end - start);
        return returnList;
    }

    @Test
    public void twoSum() {
        int count = 100000;
        int target = 324234;
        int max = 1000000;
        List<int[]> list = LeetCode.doTwoSum(count, target, max);
        logger.info(">>>>> ------INDEX------");
        list.forEach(array -> logger.info(">>>>> [{}, {}]", array[0], array[1]));
    }

    @Test
    public void sqrt() {
        int n = LeetCode.sqrt(1058);
        logger.info(">>>>> n: {}", n);
    }

    @Test
    public void combination() {
        long begin = System.currentTimeMillis();
        List<String> result = new LinkedList<>();
        combination(result, "", 15, 0);
        long end = System.currentTimeMillis();
        // for (String string : list) {
        //     logger.info(">>>>> {}", string);
        // }
        logger.info(">>>>> SIZE: [{}]", result.size());
        logger.info(">>>>> COST: [{}] ms", end - begin);
    }

    @Test
    public void assignCookies() {
        // 测试时只需修改此值
        int max = 100;
        Random random = new Random((int) (Math.random() * max) + 1);

        int[] children = generateArray(random, max);
        logger.info(">>>>> CHILDREN: {}", children);

        int[] cookies = generateArray(random, max);
        logger.info(">>>>> COOKIES: {}", cookies);

        long start = System.currentTimeMillis();
        int result = assignCookies(children, cookies);
        logger.info(">>>>> COST: {} ms", System.currentTimeMillis() - start);
        logger.info(">>>>> RESULT: {}", result);
    }

    private int[] generateArray(Random random, int max) {
        int length = random.nextInt((int) (Math.random() * max) + 1);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt((int) (Math.random() * max) + 1);
            if (number > 0) {
                list.add(number);
            }
        }
        return list.stream().mapToInt(i -> i).toArray();
    }

}
