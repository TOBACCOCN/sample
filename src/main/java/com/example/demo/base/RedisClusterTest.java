package com.example.demo.base;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RedisClusterTest {
    public static void main(String[] args) throws IOException {
        // System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
        JedisPoolConfig config = new JedisPoolConfig();
        // 最大连接数
        config.setMaxTotal(30);
        // 最大连接空闲数
        config.setMaxIdle(2);

        // 集群结点
        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
        jedisClusterNode.add(new HostAndPort("192.168.19.130", 7001));
        jedisClusterNode.add(new HostAndPort("192.168.19.130", 7002));
        jedisClusterNode.add(new HostAndPort("192.168.19.130", 7003));
        jedisClusterNode.add(new HostAndPort("192.168.19.130", 7004));
        jedisClusterNode.add(new HostAndPort("192.168.19.130", 7005));
        jedisClusterNode.add(new HostAndPort("192.168.19.130", 7006));

		// JedisCluster jc = new JedisCluster(jedisClusterNode, config);
        JedisCluster jcd = new JedisCluster(jedisClusterNode);
        jcd.set("name", "zhangsan");
        String value = jcd.get("name");
        jcd.close();
        System.out.println(value);
    }
}
