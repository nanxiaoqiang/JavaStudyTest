package com.nanxiaoqiang.test.google.guava;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * 测试Google的EventBus事件注册
 * 
 * @author nanxiaoqiang
 * 
 * @version 2015年7月27日
 */
public class EventBusTest {

	public EventBusTest() {
	}

	public static void main(String[] args) {
		EventBus eb = new EventBus("test");

		MyWork1 work1 = new MyWork1();
		MyWork2 work2 = new MyWork2();

		eb.register(work1);
		eb.register(work2);

		for (int i = 0; i < 5; i++) {
			eb.post(new OrientClass(i));
		}
		// 是单线程执行的
		// @Subscribe方法是按照注册的顺序执行的
		// 如果每个@Subscribe耗时，那么会阻碍后边的@Subscribe和post方法执行。
	}

}

class OrientClass {
	private static Logger logger = LogManager.getLogger(OrientClass.class
			.getName());
	private final int messageCode;

	public OrientClass(int msgCode) {
		this.messageCode = msgCode;
		logger.info("OrientClass:constructor:MSG_CODE:" + this.messageCode);
	}

	public int getMessageCode() {
		return this.messageCode;
	}

}

class MyWork1 {
	private static Logger logger = LogManager
			.getLogger(MyWork1.class.getName());
	private int msgCode;

	@Subscribe
	public void doSomething(OrientClass oc) {
		logger.info("MyWork1 start!" + System.currentTimeMillis());
		try {
			this.msgCode = oc.getMessageCode() + 100;
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("MyWork1 end!msgCode is " + this.msgCode + "|"
				+ System.currentTimeMillis());
	}
}

class MyWork2 {
	private static Logger logger = LogManager
			.getLogger(MyWork2.class.getName());
	private int msgCode;

	@Subscribe
	public void doSomething(OrientClass oc) {
		logger.info("MyWork2 start!" + System.currentTimeMillis());
		try {
			this.msgCode = oc.getMessageCode() + 200;
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("MyWork2 end!msgCode is " + this.msgCode + "|"
				+ System.currentTimeMillis());
	}
}