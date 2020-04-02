package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a sorted linked list, delete all duplicates such that each element appear only once.
 * Example 1:
 * Input: 1->1->2
 * Output: 1->2
 * Example 2:
 * Input: 1->1->2->3->3
 * Output: 1->2->3
 *
 * @author zhangyonghong
 * @date 2020.2.27
 */
@Slf4j
public class RemoveDuplicatesFromSortedList {

    public ListNode removeDuplicates(ListNode listNode) {
        if (listNode == null || listNode.next == null) {
            return listNode;
        }
        ListNode cur = listNode;
        while (cur.next != null) {
            if (cur.next.val == cur.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return listNode;
    }

    @Test
    public void removeDuplicates() {
        ListNode listNode = new ListNode(1);
        ListNode listNode2 = new ListNode(1);
        listNode2.next = new ListNode(2);
        listNode.next = listNode2;
        log.info(">>>>> RESULT: [{}]", removeDuplicates(listNode));
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            List<Integer> list = new ArrayList<>();
            ListNode cur = this;
            list.add(cur.val);
            while (cur.next != null) {
                list.add(cur.next.val);
                cur = cur.next;
            }
            return list.toString();
        }
    }

}
