package com.nanxiaoqiang.test.spring.core.test2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nanxiaoqiang.test.spring.core.test2.Car;

/**
 * 自动扫描的测试
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年3月14日
 *
 */
public class SpringTest2 {

	private static Logger logger = LogManager.getLogger(SpringTest2.class
			.getName());

	public SpringTest2() {
	}

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring/test2/spring-test.xml");
		Car car = context.getBean(Car.class);
		logger.info(car);
	}

}
