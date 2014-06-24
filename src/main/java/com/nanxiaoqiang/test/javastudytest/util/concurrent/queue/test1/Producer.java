package com.nanxiaoqiang.test.javastudytest.util.concurrent.queue.test1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 生产者的线程
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月24日
 * 
 */
public class Producer implements Runnable {
	private static Logger logger = LogManager.getLogger(Producer.class
			.getName());

	private BlockingQueue<String> queue;

	private boolean isRunning = true;

	private String data = "";

	public Producer(BlockingQueue<String> queue) {
		this.queue = queue;
		logger.debug("Producer初始化");
	}

	@Override
	public void run() {
		logger.debug("Producer启动!");
		RandomUtils.nextInt(0, 10);
		try {
			while (isRunning) {
				logger.debug("生产数据ing");
				int r = RandomUtils.nextInt(100, 5000);

				Thread.sleep(r);
				data = "随机出来一个从及格到满分的数字:" + RandomUtils.nextInt(60, 101);
				if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
					logger.info("放入数据失败：" + data);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();// 强制中断
		} finally {
			logger.debug("退出生产者线程！");
		}
	}

	public void stop() {
		isRunning = false;
	}

}
