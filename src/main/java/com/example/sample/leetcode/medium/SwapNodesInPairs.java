package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a linked list, swap every two adjacent nodes and return its head.
 * Example:
 * Given 1->2->3->4, you should return the list as 2->1->4->3.
 * Note:
 * Your algorithm should use only constant extra space.
 * You may not modify the values in the list's nodes, only nodes itself may be changed.
 *
 * @author zhangyonghong
 * @date 2020.3.5
 */
@Slf4j
public class SwapNodesInPairs {

    public ListNode swapNodesInPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode temp = head.next;
        head.next = swapNodesInPairs(temp.next);
        temp.next = head;
        return temp;
    }

    @Test
    public void swapNodesInPairs() {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4/*, new ListNode(5)*/))));
        log.info(">>>>> RESULT: [{}]", swapNodesInPairs(head));
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
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
