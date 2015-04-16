package com.nanxiaoqiang.test.javastudytest.util.concurrent.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年4月16日
 *
 */
public class SemaphoreTest {

	private static Logger LOGGER = LogManager.getLogger(SemaphoreTest.class
			.getName());

	private static final int THREAD_COUNT = 30;

	private static ExecutorService threadPool = Executors
			.newFixedThreadPool(THREAD_COUNT);

	private static Semaphore s = new Semaphore(10);

	public SemaphoreTest() {
	}

	public static void main(String[] args) {
		for (int i = 0; i < THREAD_COUNT; i++) {
			threadPool.execute(new Runnable() {

				@Override
				public void run() {
					try {
						s.acquire();
						long start = System.currentTimeMillis();
						LOGGER.info(new StringBuffer().append("Thread ")
								.append(Thread.currentThread().getName())
								.append(" 开始执行!").toString());
						TimeUnit.MILLISECONDS.sleep(RandomUtils.nextLong(1000,
								5000));
						long end = System.currentTimeMillis();
						LOGGER.info(new StringBuffer().append("Thread ")
								.append(Thread.currentThread().getName())
								.append(" 执行结束!").append(end - start)
								.toString());
						s.release();
					} catch (InterruptedException e) {
						LOGGER.error(e);
					}

				}
			});
		}
		threadPool.shutdown();
		while (true) {
			if (threadPool.isTerminated()) {
				LOGGER.info("程序结束");
				break;
			}
		}
	}

}
