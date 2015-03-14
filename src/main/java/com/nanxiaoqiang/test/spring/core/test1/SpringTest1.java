package com.nanxiaoqiang.test.spring.core.test1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring的最简单的例子
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年3月14日
 *
 */
public class SpringTest1 {

	private static Logger logger = LogManager.getLogger(SpringTest1.class
			.getName());

	public SpringTest1() {
	}

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring/test1/spring-test1.xml");
		Car car = context.getBean("car", Car.class);
		logger.info(car);
	}

}
