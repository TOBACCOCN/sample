package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a linked list, remove the n-th node from the end of list and return its head.
 * Example:
 * Given linked list: 1->2->3->4->5, and n = 2.
 * After removing the second node from the end, the linked list becomes 1->2->3->5.
 * Note:
 * Given n will always be valid.
 *
 * @author zhangyonghong
 * @date 2020.3.5
 */
@Slf4j
public class RemoveNthNodeOfEndOfList {

    public ListNode removeNthNodeOfEndOfList(ListNode head, int n) {
        ListNode slow= head, fast = slow;
        while (--n >= 0) {
            fast = fast.next;
        }

        if (fast == null) {
            return head.next;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return head;
    }

    @Test
    public void removeNthNodeOfEndOfList() {
        ListNode head = new ListNode(1,
                new ListNode(2,
                        new ListNode(3, new ListNode(4, new ListNode(5)))));
        log.info(">>>>> RESULT: [{}]", removeNthNodeOfEndOfList(head, 3));
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
