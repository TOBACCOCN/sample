package com.example.demo.base;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class KeyExpiredListenerTest {
    
    public static void main(String[] args) throws InterruptedException {
    	Jedis jedis = new Jedis("192.168.1.128", 6379);
		jedis.auth("water@abc123");
        jedis.psubscribe(new KeyExpiredListener(), "__keyevent@0__:expired");
        jedis.close();
    }

}

class KeyExpiredListener extends JedisPubSub {
	
    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe " + pattern + " " + subscribedChannels);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
    System.out.println(
        "pattern = [" + pattern + "], channel = [" + channel + "], message = [" + message + "]");
    //收到消息 key的键值，处理过期提醒
    }
    
}
