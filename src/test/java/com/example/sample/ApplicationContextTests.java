package com.example.sample;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisCluster;

public class ApplicationContextTests {

	private static Logger logger = LoggerFactory.getLogger(ApplicationContextTests.class);

	@Test
	public void test() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		JedisCluster bean = (JedisCluster) context.getBean("jedisCluster");
		bean.set("name", "zyh");
		logger.info(">>>>> NAME: {}", bean.get("name"));
		context.close();
	}

}
