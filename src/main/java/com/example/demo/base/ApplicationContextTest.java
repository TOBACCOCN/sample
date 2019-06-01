package com.example.demo.base;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.JedisCluster;

public class ApplicationContextTest {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		JedisCluster bean = (JedisCluster) context.getBean("jedisCluster");
		bean.set("name", "zyh");
		System.out.println(bean.get("name"));
		context.close();
	}

}
