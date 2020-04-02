package com.example.sample.leetcode.hard;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
 * You may assume that the intervals were initially sorted according to their start times.
 * Example 1:
 * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
 * Output: [[1,5],[6,9]]
 * Example 2:
 * Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * Output: [[1,2],[3,10],[12,16]]
 * Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
 *
 * @author zhangyonghong
 * @date 2020.3.14
 */
@Slf4j
public class InsertInterval {

    public List<Interval> insertInterval(List<Interval> intervals, Interval insert) {
        int i = -1;
        boolean insertMerged = false;
        while (++i < intervals.size() - 1) {
            Interval interval = intervals.get(i);
            if (!insertMerged) {
                insertMerged = merge(insert, interval, false);
                if (insertMerged) {
                    --i;
                }
            } else {
                if (merge(intervals.get(++i), interval, true)) {
                    intervals.remove(i);
                    i = i - 2;
                }
            }
        }
        return intervals;
    }

    private boolean merge(Interval insert, Interval interval, boolean mergeBro) {
        if (insert.begin <= interval.begin && insert.end >= interval.begin && insert.end <= interval.end) {
            interval.begin = insert.begin;
            return true;
        } else if (insert.begin >= interval.begin && insert.begin <= interval.end && insert.end >= interval.end) {
            interval.end = insert.end;
            return true;
        } else if (insert.begin <= interval.begin && insert.end >= interval.end) {
            interval.begin = insert.begin;
            interval.end = insert.end;
            return true;
        }
        return mergeBro && insert.begin >= interval.begin && insert.end <= interval.end;
    }

    @Test
    public void insertInterval() {
        List<Interval> lists = new ArrayList<>();
        lists.add(new Interval(1, 2));
        lists.add(new Interval(3, 5));
        lists.add(new Interval(6, 7));
        lists.add(new Interval(8, 10));
        lists.add(new Interval(12, 16));
        Interval insert = new Interval(4, 8);
        log.info(">>>>> RESULT: [{}]", insertInterval(lists, insert));
    }

    private static class Interval {
        int begin;
        int end;

        Interval(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        public String toString() {
            return Arrays.asList(begin, end).toString();
        }
    }

}
