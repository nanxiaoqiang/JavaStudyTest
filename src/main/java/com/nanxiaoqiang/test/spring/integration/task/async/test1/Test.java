package com.nanxiaoqiang.test.spring.integration.task.async.test1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring异步的测试
 * 
 * @author nanxiaoqiang
 * 
 * @version 2016年7月11日
 */
public class Test {
	private static Logger logger = LogManager.getLogger(Test.class.getName());

	public Test() {
	}

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring/integration/task/spring-async.xml");
		AsyncService asyncService = context.getBean(AsyncService.class);
		asyncService.doSomeThing8sec();
		Future<String> future = asyncService.doSomeThing6Sec(1);
		asyncService.doSomeThing5sec();
		logger.info("Hehehehe");

		while (!future.isDone()) {
			try {
				logger.info(future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		logger.info("The FIN");
	}
}
