package com.example.sample;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

public class SentinelJedisTests {

    private static Logger logger = LoggerFactory.getLogger(SentinelJedisTests.class);

    @Test
    public void test() {
        Set<String> sentinels = new HashSet<String>();
        String hostAndPort1 = "192.168.1.130:26379";
        String hostAndPort2 = "192.168.1.130:26380";
        sentinels.add(hostAndPort1);
        sentinels.add(hostAndPort2);
        String clusterName = "mymaster";
        JedisSentinelPool redisSentinelJedisPool = new JedisSentinelPool(clusterName, sentinels, "foobared");
        try (Jedis jedis = redisSentinelJedisPool.getResource()) {
            //jedis.set("key", "aaa");
            logger.info(">>>>> NAME: [{}]", jedis.get("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        redisSentinelJedisPool.close();
    }

}
