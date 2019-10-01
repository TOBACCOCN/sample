package com.example.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.List;
import java.util.Map.Entry;

@Slf4j
public class RedisTests {

    // private static Logger logger = LoggerFactory.getLogger(RedisTests.class);

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
        log.info(">>>>> CURSOR: [{}]", result.getStringCursor());
        List<Entry<String, String>> list = result.getResult();
        for (Entry<String, String> entry : list) {
            log.info(">>>>> {}: {}", entry.getKey(), entry.getValue());
        }

        jedis.close();
    }


}
