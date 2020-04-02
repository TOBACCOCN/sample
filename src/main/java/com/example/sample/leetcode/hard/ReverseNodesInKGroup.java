package com.example.sample.leetcode.hard;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
 * k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.
 * Example:
 * Given this linked list: 1->2->3->4->5
 * For k = 2, you should return: 2->1->4->3->5
 * For k = 3, you should return: 3->2->1->4->5
 * Note:
 * Only constant extra memory is allowed.
 * You may not alter the values in the list's nodes, only nodes itself may be changed.
 *
 * @author zhangyonghong
 * @date 2020.3.12
 */
@Slf4j
public class ReverseNodesInKGroup {

    public ListNode reverseNodesInKGroup(ListNode head, int k) {
        if (head == null || k == 1) {
            return head;
        }
        ListNode p = new ListNode(0, head);
        for (int i = 1; head != null; ++i) {
            if (i == k) {
                reverse(p, head.next);
            } else {
                head = head.next;
            }
        }
        return p.next;
    }

    private void reverse(ListNode node, ListNode next) {
        ListNode head = node.next, move = head.next;
        while (move != next) {
            head.next = move.next;
            move.next = node.next;
            node.next = move;
            move = head.next;
        }
    }

    @Test
    public void reverseNodesInKGroup() {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
        log.info(">>>>> RESULT: [{}]", reverseNodesInKGroup(head, 2));
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
            List<Integer> list = new LinkedList<>();
            ListNode temp = new ListNode(0);
            temp.next = this;
            while (temp.next != null) {
                list.add(temp.next.val);
                temp = temp.next;
            }
            return list.toString();
        }
    }

}
