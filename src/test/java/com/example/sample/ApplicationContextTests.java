package com.example.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisCluster;

@Slf4j
public class ApplicationContextTests {

	// private static Logger logger = LoggerFactory.getLogger(ApplicationContextTests.class);

	@Test
	public void test() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		JedisCluster bean = (JedisCluster) context.getBean("jedisCluster");
		bean.set("name", "zyh");
		log.info(">>>>> NAME: [{}]", bean.get("name"));
		context.close();
	}

}
