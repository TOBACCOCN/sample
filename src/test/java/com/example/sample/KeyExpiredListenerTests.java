package com.example.sample;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class KeyExpiredListenerTests {

    private static Logger logger = LoggerFactory.getLogger(KeyExpiredListenerTests.class);

    @Test
    public void test() {
        Jedis jedis = new Jedis("192.168.1.128", 6379);
        jedis.auth("water@abc123");
        jedis.psubscribe(new KeyExpiredListener(), "__keyevent@0__:expired");
        jedis.close();
    }

    static class KeyExpiredListener extends JedisPubSub {

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {
            logger.info("onPSubscribe {} {}", pattern, subscribedChannels);
        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {
            logger.info("pattern = [{}], channel = [{}], message = [{}]", pattern, channel, message);
        }

    }

}
