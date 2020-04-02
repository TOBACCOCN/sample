package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Given a collection of intervals, merge all overlapping intervals.
 * Example 1:
 * Input: [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
 * Example 2:
 * Input: [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considerred overlapping.
 *
 * @author zhangyonghong
 * @date 2020.3.6
 */
@Slf4j
public class MergeIntervals {

    public List<List<Integer>> mergeIntervals(List<List<Integer>> o) {
        o.sort(Comparator.comparingInt(l -> l.get(0)));
        int i = -1;
        while (++i < o.size() - 1) {
            List<Integer> before = o.get(i);
            List<Integer> after = o.get(i + 1);
            if (after.get(0) < before.get(1)) {
                o.remove(after);
                before.set(1, Math.max(before.get(1), after.get(1)));
                --i;
            }
        }
        return o;
    }

    @Test
    public void mergerIntervals() {
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(1, 2));
        // list.add(Arrays.asList(6, 9));
        // list.add(Arrays.asList(3, 8));
        list.add(Arrays.asList(4, 5));
        list.add(Arrays.asList(7, 10));
        log.info(">>>>> RESULT: [{}]", mergeIntervals(list));
    }

}
