package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.
 * In Pascal's triangle, each number is the sum of the two numbers directly above it.
 * Example:
 * Input: 5
 * Output:
 * [
 * [1],
 * [1,1],
 * [1,2,1],
 * [1,3,3,1],
 * [1,4,6,4,1]
 * ]
 *
 * @author zhangyonghong
 * @date 2020.2.29
 */
@Slf4j
public class PascalTriangle {

    /**
     * Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.
     * In Pascal's triangle, each number is the sum of the two numbers directly above it.
     * Example:
     * Input: 5
     * Output:
     * [
     * [1],
     * [1,1],
     * [1,2,1],
     * [1,3,3,1],
     * [1,4,6,4,1]
     * ]
     *
     * @param n num of pascalTriangle rows
     * @return two dimensional arrays of pascalTriangle
     */
    public List<List<Integer>> pascalTriangle(int n) {
        List<List<Integer>> list = new LinkedList<>();
        for (int i = 0; i < n; ++i) {
            List<Integer> sub = new LinkedList<>();
            for (int j = 0; j <= i; ++j) {
                if (j == 0 || j == i) {
                    sub.add(1);
                } else {
                    List<Integer> last = list.get(i - 1);
                    sub.add(last.get(j - 1) + last.get(j));
                }
            }
            list.add(sub);
        }
        return list;
    }

    /**
     * Given a non-negative index k where k ≤ 33, return the kth index row of the Pascal's triangle.
     * Note that the row index starts from 0.
     * img In Pascal's triangle, each number is the sum of the two numbers directly above it.
     * Example:
     * Input: 3
     * Output: [1,3,3,1]
     * Follow up:
     * Could you optimize your algorithm to use only O(k) extra space?
     *
     * @param n nth row of pascalTriangle(start from 0)
     * @return array of pascalTriangle nth row
     */
    public List<Integer> pascalTriangleII(int n) {
        // List<Integer> list = new ArrayList<>();
        // for (int i = 0; i <= n; ++i) {
        //     list.add(1);
        //     for (int j = i - 1; j > 0; --j) {
        //         list.set(j, list.get(j - 1) + list.get(j));
        //     }
        // }
        // return list;

        List<Integer> list = new ArrayList<>();
        int index = 1;
        for (int i = 0; i <= n; ++i) {
            list.add(index);
            // 对于第 n 行（行数从 0 开始），第 ( i + 1) 项是第 i 项的 ( n - i ) /( i + 1 ) 倍。
            // https://github.com/MisterBooo/LeetCodeAnimation/blob/master/notes/LeetCode%E7%AC%AC119%E5%8F%B7%E9%97%AE%E9%A2%98%EF%BC%9A%E6%9D%A8%E8%BE%89%E4%B8%89%E8%A7%92II.md
            index = index * (n - i) / (i + 1);
        }
        return list;
    }

    @Test
    public void pascalTriangle() {
        int n = 2;
        log.info(">>>>> PASCAL_TRIANGLE: [{}]", pascalTriangle(n));
    }

    @Test
    public void pascalTriangleII() {
        int n = 20;
        log.info(">>>>> [{}] TH_OF_PASCAL_TRIANGLE: [{}]", n, pascalTriangleII(n));
    }

}
