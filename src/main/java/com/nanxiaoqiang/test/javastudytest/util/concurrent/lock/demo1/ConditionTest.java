package com.nanxiaoqiang.test.javastudytest.util.concurrent.lock.demo1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConditionTest {

	private static final Logger LOGGER = LogManager
			.getLogger(ConditionTest.class.getName());

	public ConditionTest() {
	}

	public static void main(String[] args) {
		final Lock lock = new ReentrantLock();
		final Condition condition = lock.newCondition();

		Thread threadwait = new Thread((Runnable) () -> {
			try {
				lock.lock();
				LOGGER.info("我在等信号");
				condition.await();
			} catch (InterruptedException e) {
				LOGGER.error(e);
			}
			LOGGER.info("拿到信号了！");
			lock.unlock();
		}, "threadwait");

		threadwait.start();

		Thread threadopen = new Thread((Runnable) () -> {
			try {
				lock.lock();
				LOGGER.info("我在找钥匙");
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				LOGGER.error(e);
			}
			condition.signalAll();
			LOGGER.info("拿到钥匙了！");
			lock.unlock();
		}, "threadopen");

		threadopen.start();
	}
}
