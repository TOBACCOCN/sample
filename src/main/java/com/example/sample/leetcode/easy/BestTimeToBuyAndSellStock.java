package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author zhangyonghong
 * @date 2020.2.29
 */
@Slf4j
public class BestTimeToBuyAndSellStock {

    /**
     * Say you have an array for which the ith element is the price of a given stock on day i.
     * If you were only permitted to complete at most one transaction (i.e., buy one and sell one share of the stock), design an algorithm to find the maximum profit.
     * Note that you cannot sell a stock before you buy one.
     * Example 1:
     * Input: [7,1,5,3,6,4]
     * Output: 5
     * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
     * Not 7-1 = 6, as selling price needs to be larger than buying price.
     * Example 2:
     * Input: [7,6,4,3,1]
     * Output: 0
     * Explanation: In this case, no transaction is done, i.e. max profit = 0.
     *
     * @param nums array of stock price of each day
     * @return max profit
     */
    public int bestTimeToBuyAndSelStock(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int profit = 0, buy = nums[0];
        for (int i = 1; i < nums.length; ++i) {
            if (nums[i] - buy > profit) {
                profit = nums[i] - buy;
            } else {
                if (nums[i] < buy) {
                    buy = nums[i];
                }
            }
        }
        return profit;
    }

    /**
     * Say you have an array for which the ith element is the price of a given stock on day i.
     * Design an algorithm to find the maximum profit. You may complete as many transactions as you like (i.e., buy one and sell one share of the stock multiple times).
     * Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
     * Example 1:
     * Input: [7,1,5,3,6,4]
     * Output: 7
     * Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
     * Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
     * Example 2:
     * Input: [1,2,3,4,5]
     * Output: 4
     * Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
     * Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
     * engaging multiple transactions at the same time. You must sell before buying again.
     * Example 3:
     * Input: [7,6,4,3,1]
     * Output: 0
     * Explanation: In this case, no transaction is done, i.e. max profit = 0.
     *
     * @param nums array of stock price of each day
     * @return max profit
     */
    public int bestTimeToBuyAndSelStockII(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int profit = 0, buy = nums[0], totalProfit = 0;
        for (int i = 1; i < nums.length; ++i) {
            if (nums[i] - buy > profit) {
                profit = nums[i] - buy;
            }
            if (nums[i] < nums[i - 1]) {
                buy = nums[i];
                totalProfit += profit;
                profit = 0;
            }
        }
        totalProfit += profit;
        return totalProfit;
    }

    @Test
    public void bestTimeToBuyAndSelStock() {
        // int[] nums = {2, 3, 0, 4, 9, 1, 3, 9, 40, 5};
        int[] nums = {7, 6, 4, 3, 1};
        log.info(">>>>> PROFIT: [{}]", bestTimeToBuyAndSelStock(nums));
    }

    @Test
    public void bestTimeToBuyAndSelStockII() {
        int[] nums = {2, 3, 0, 4, 9, 1, 3, 9, 40, 5};
        // int[] nums = {7, 1, 5, 3, 6, 4};
        log.info(">>>>> PROFIT: [{}]", bestTimeToBuyAndSelStockII(nums));
    }

}
