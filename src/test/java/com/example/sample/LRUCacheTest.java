package com.example.sample;

import com.example.sample.base.LRUCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class LRUCacheTest {

    @Test
    public void test() {
        LRUCache lruCache = new LRUCache(3);
        lruCache.put(1, 2);
        lruCache.put(1, 3);
        lruCache.put(2, 3);
        lruCache.put(3, 3);
        lruCache.put(2, 4);
        lruCache.get(3);
        log.info(">>>>> ENTRY_NODE: [{}]", lruCache.getHead());
    }

}
