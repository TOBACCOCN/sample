package com.example.demo.base;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.List;
import java.util.Map.Entry;

public class RedisTest {

    public static void main(String[] args) {
		// try {
		// 	Jedis jedis = new Jedis(IP, PORT);
		// 	jedis.auth(PASSWORD);
		// 	for (int i = 1; i <= 5000; i++) {
		// 		jedis.del("sn:" + i);
		// 	}
		// 	jedis.close();
		// } catch (Exception e) {
		// 	System.out.println(e.getMessage());
		// }

        String IP = "192.168.1.128";
        int PORT = 6379;
        Jedis jedis = new Jedis(IP, PORT);
        String PASSWORD = "water@abc123";
        jedis.auth(PASSWORD);
        ScanParams params = new ScanParams();
        params.count(2);
        // jedis.setex("name", 10, "zyh");
        ScanResult<Entry<String, String>> result = jedis.hscan("hash_test", "1", params);
        System.out.println(result.getStringCursor());
        List<Entry<String, String>> list = result.getResult();
        for (Entry<String, String> entry : list) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        jedis.close();
    }

}
