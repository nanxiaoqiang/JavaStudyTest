package com.nanxiaoqiang.test.spring.core.test2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
		// @SuppressWarnings("resource")
		// ApplicationContext 
		// 为了要销毁Spring的context要使用AbstractApplicationContext
		AbstractApplicationContext context = new ClassPathXmlApplicationContext(
				"spring/test2/spring-test.xml");
		Car car = context.getBean(Car.class);
		logger.info(car);
		car.setColor("玫瑰金");
		logger.info(car);
		car = null;// 手动置为null，但是没有用，Spring容器中仍然存在着一个Car的bean
		logger.info(context.getBean(Car.class));// 而且先前对Car对象的修改在此时仍然能够看到
		context.close();// 此时才是销毁各个Bean的时候
	}

}
