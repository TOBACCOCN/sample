package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * Example
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 *
 * @author zhangyonghong
 * @date 2020.3.2
 */
@Slf4j
public class AddTwoNumbers {

    public int addTwoNumbers(ListNode node1, ListNode node2) {
        int sum = 0;
        int n = 0;
        while (node1 != null || node2 != null) {
            if (node1 != null) {
                sum += node1.val * Math.pow(10D, n);
                node1 = node1.next;
            }

            if (node2 != null) {
                sum += node2.val * Math.pow(10D, n);
                node2 = node2.next;
            }

            n++;
        }
        return sum;
    }

    @Test
    public void addTwoNumbers() {
        ListNode node1 = new ListNode(0);
        node1.next =new ListNode(1, new ListNode(2, new ListNode(3)));
        ListNode node2 = new ListNode(4);
        node2.next =new ListNode(5, new ListNode(6, new ListNode(7)));
        log.info(">>>>> SUM:[{}]", addTwoNumbers(node1, node2));
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
    }

}
