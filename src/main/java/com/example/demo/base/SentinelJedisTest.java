package com.example.demo.base;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

public class SentinelJedisTest {
	
	public static void main(String[] args) {
        Set<String> sentinels = new HashSet<String>();  
        String hostAndPort1 = "192.168.1.130:26379";  
        String hostAndPort2 = "192.168.1.130:26380";  
        sentinels.add(hostAndPort1);  
        sentinels.add(hostAndPort2);  
        String clusterName = "mymaster" ;   
		JedisSentinelPool redisSentinelJedisPool = new JedisSentinelPool(clusterName, sentinels, "foobared");
        try (Jedis jedis = redisSentinelJedisPool.getResource()) {
            //jedis.set("key", "aaa");
            System.out.println(jedis.get("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        redisSentinelJedisPool.close();  
	}

}
