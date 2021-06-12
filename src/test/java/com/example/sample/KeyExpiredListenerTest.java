package com.example.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

@Slf4j
public class KeyExpiredListenerTest {

    // private static Logger logger = LoggerFactory.getLogger(KeyExpiredListenerTests.class);

    @Test
    public void test() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("test");
        jedis.psubscribe(new KeyExpiredListener(), "__keyevent@0__:expired");
        jedis.close();
    }

    static class KeyExpiredListener extends JedisPubSub {

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {
            log.info(">>>>> onPSubscribe {} {}", pattern, subscribedChannels);
        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {
            log.info(">>>>> pattern = [{}], channel = [{}], message = [{}]", pattern, channel, message);
        }

    }

}
