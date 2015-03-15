package com.nanxiaoqiang.test.spring.aop.test1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nanxiaoqiang.test.spring.aop.test1.pojo.Car;

/**
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年3月15日
 *
 */
public class App {

	private static Logger logger = LogManager.getLogger(App.class.getName());

	public App() {
	}

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring/aop/test1/spring-test.xml");
		Car car = context.getBean(Car.class);
		logger.info(car);
		car.showMe();
	}

}
