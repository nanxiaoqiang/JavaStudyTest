package com.nanxiaoqiang.test.redis.client.jredis.test2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 一个jedis pool的例子
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年4月22日
 *
 */
public class App {
	private static JedisPool pool = null;

	private static Logger LOGGER = LogManager.getLogger(App.class.getName());

	private static final int THREAD_COUNT = 30;

	private static ExecutorService threadPool = Executors
			.newFixedThreadPool(THREAD_COUNT);

	public static JedisPool getPool() {
		if (pool == null) {
			JedisPoolConfig config = new JedisPoolConfig(); // 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
			// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
			config.setMaxTotal(500);
			// config.setMaxActive(500);
			// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
			config.setMaxIdle(5);
			// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
			// config.setMaxWait(1000 * 100);
			// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
			config.setTestOnBorrow(true);
			pool = new JedisPool(config, "localhost");
		}
		return pool;
	}

	/**
	 * 返还到连接池
	 * 
	 * @param pool
	 * @param redis
	 */
	public static void returnResourceObject(JedisPool pool, Jedis redis) {
		if (redis != null) {
			pool.returnResourceObject(redis);// .re.returnResource(redis);
		}
	}

	public App() {
	}

	public static void main(String[] args) {
		pool = getPool();
		for (int i = 0; i < THREAD_COUNT; i++) {
			threadPool.execute(new Runnable() {
				Jedis jedis = null;
				String keyname = null;

				@Override
				public void run() {
					keyname = new StringBuffer().append("Thread ")
							.append(Thread.currentThread().getName())
							.append(".date").toString();
					for (int i = 0; i < 100; i++) {
						long start = System.currentTimeMillis();
						LOGGER.info(new StringBuffer(keyname).append(" 开始执行!")
								.toString());
						jedis = pool.getResource();
						// jedis.auth("pasword");没有设置密码
						jedis.set(keyname, start + "");
						// jedis.quit();
						pool.returnResourceObject(jedis);
						long end = System.currentTimeMillis();
						LOGGER.info(new StringBuffer(keyname).append(" 执行结束!")
								.append(end - start).toString());
						try {
							long sleeptime = RandomUtils.nextLong(300, 550);
							TimeUnit.MILLISECONDS.sleep(sleeptime);
						} catch (InterruptedException e) {
							LOGGER.error(e);
						}
					}
				}

			});
		}
		threadPool.shutdown();
		pool.destroy();
		while (true) {
			if (threadPool.isTerminated()) {
				LOGGER.info("程序结束");
				break;
			}
		}
	}
}
