package com.example.sample;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.List;
import java.util.Map.Entry;

public class RedisTests {

    private static Logger logger = LoggerFactory.getLogger(RedisTests.class);

    @Test
    public void test() {
        String ip = "192.168.1.128";
        int port = 6379;
        Jedis jedis = new Jedis(ip, port);
        String PASSWORD = "water@abc123";
        jedis.auth(PASSWORD);
        ScanParams params = new ScanParams();
        params.count(2);
        // jedis.setex("name", 10, "zyh");
        ScanResult<Entry<String, String>> result = jedis.hscan("hash_test", "1", params);
        logger.info(">>>>> CURSOR: [{}]", result.getStringCursor());
        List<Entry<String, String>> list = result.getResult();
        for (Entry<String, String> entry : list) {
            logger.info(">>>>> {}: {}", entry.getKey(), entry.getValue());
        }

        jedis.close();
    }


}
