package com.example.sample.base;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * LRU 缓存
 *
 * @author zhangyonghong
 * @date 2020.4.16
 */
@Getter
public class LRUCache {

    Map<Object, EntryNode> map;
    EntryNode head;
    EntryNode tail;
    int cacheSize;

    public LRUCache(int cacheSize) {
        map = new ConcurrentHashMap<>(cacheSize);
        this.cacheSize = cacheSize;
    }

    /**
     * 获取缓存中对象，并把它挪到链表最前面
     *
     * @param key 键
     * @return 键值对
     */
    public EntryNode get(Object key) {
        EntryNode entryNode = map.get(key);
        if (entryNode != null) {
            removeToHead(entryNode);
        }
        return entryNode;
    }

    /**
     * 把键值对节点挪到链表最前面
     *
     * @param entryNode 键值对节点
     */
    private void removeToHead(EntryNode entryNode) {
        // 该键值对节点本来就在链表最前面
        if (entryNode == head) {
            return;
        }

        // 该键值对节点不在链表最前面，也不在最后面
        if (entryNode.next != null) {
            entryNode.next.pre = entryNode.pre;
            entryNode.pre.next = entryNode.next;
        }

        // 该键值对节点在链表最后面（此时链表头尾节点肯定不是一个节点）
        if (entryNode == tail) {
            entryNode.pre.next = null;
            tail = entryNode.pre;
        }

        // 将 entryNode 挪到链表最前面
        if (head != null) {
            entryNode.next = head;
            head.pre = entryNode;
        }
        head = entryNode;
        head.pre = null;
    }

    /**
     * 添加缓存对象，并把它挪到链表最前面
     */
    public void put(Object key, Object value) {
        EntryNode entryNode = map.get(key);
        // map 中不存在该键值对节点
        if (entryNode == null) {
            entryNode = new EntryNode(key, value);
            // 而此时 map 中缓存（键值对节点）数量已达到最大值
            if (map.size() >= cacheSize) {
                // 移除 map 中 key 为 tail.key 的缓存
                map.remove(tail.key);

                // 移除链表中原来的最后一个节点，设原来最后一个节点的前一个节点为最后节点
                if (tail.pre != null) {
                    tail.pre.next = null;
                    tail = tail.pre;
                }
            }
        } else {
            map.remove(key);
            entryNode.value = value;
        }

        // 将缓存（键值对节点）存到 map，键值对节点挪到链表最前面
        map.put(key, entryNode);
        removeToHead(entryNode);

        // 第一次放缓存时，链表头部和尾部节点是同一个节点
        if (tail == null) {
            tail = head;
        }
    }

    private static class EntryNode {
        Object key;
        Object value;
        EntryNode pre;
        EntryNode next;

        EntryNode(Object key, Object value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "EntryNode{" +
                    "key=" + key +
                    ", value=" + value +
                    ", next=" + next +
                    '}';
        }
    }

}
