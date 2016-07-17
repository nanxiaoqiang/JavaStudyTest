package com.nanxiaoqiang.test.spring.data.redis.test1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	private static Logger logger = LogManager.getLogger(Test.class.getName());

	public Test() {
	}

	public static void main(String[] args) throws Throwable {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring/data/redis/spring-redis1.xml");

		RedisService redisService = context.getBean(RedisService.class);

//		redisService.addLink("nanxiaoqiang", new URL(
//				"https://github.com/nanxiaoqiang"));
		
		redisService.addValue("nanxiaoqiang", "https://github.com/nanxiaoqiang");
		
		logger.info(redisService.getValue("nanxiaoqiang"));
		
	}

}
