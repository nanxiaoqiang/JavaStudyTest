package com.nanxiaoqiang.test.javastudytest.util.concurrent.queue.test1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 消费者
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月24日
 * 
 */
public class Consumer implements Runnable {
	private static Logger logger = LogManager.getLogger(Consumer.class
			.getName());

	private BlockingQueue<String> queue;

	private boolean isRunning = true;

	public Consumer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		logger.debug("消费者线程");
		try {
			while (isRunning) {
				logger.debug("从队列中取出并展示数据");

				String data = queue.poll(10, TimeUnit.SECONDS);

				if (StringUtils.isNotBlank(data)) {
					logger.info("拿到数据!" + data);
				} else {
					// 超过10s都没有新数据，说明队列拿完了，退出
					logger.debug("超过10s没数据，退出。");
					isRunning = false;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
