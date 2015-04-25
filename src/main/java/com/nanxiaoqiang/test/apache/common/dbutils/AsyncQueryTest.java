package com.nanxiaoqiang.test.apache.common.dbutils;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.dbutils.AsyncQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AsyncQueryTest {

	private static Logger LOGGER = LogManager.getLogger(AsyncQueryTest.class
			.getName());

	private static final String DRIVER = "com.mysql.jdbc.Driver";

	private static final String URL = "jdbc:mysql://localhost:3306/bjmetro?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";

	private static final String USERNAME = "bjmetro";
	private static final String PASSWORD = "bjmetro";

	public AsyncQueryTest() {

	}

	public static void main(String[] args) {
		// 创建一个线程池
		ExecutorService executorService = new ThreadPoolExecutor(8, 8, 0L,
				TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(100000),
				new ThreadPoolExecutor.CallerRunsPolicy());

		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(DRIVER);
		ds.setUrl(URL);
		ds.setUsername(USERNAME);
		ds.setPassword(PASSWORD);

		// Class.forName(DRIVER);
		// ConnectionFactory connectionFactory = new
		// DriverManagerConnectionFactory(
		// URL, null);
		//
		// PoolableConnectionFactory poolableConnectionFactory = new
		// PoolableConnectionFactory(
		// connectionFactory, null);
		//
		// ObjectPool<PoolableConnection> connectionPool = new
		// GenericObjectPool<>(
		// poolableConnectionFactory);
		//
		// poolableConnectionFactory.setPool(connectionPool);
		//
		// PoolingDataSource<PoolableConnection> dataSources = new
		// PoolingDataSource<>(
		// connectionPool);

		// 使用指定的线程池和QueryRunner构建AsyncQueryRunner
		AsyncQueryRunner asyncRun = new AsyncQueryRunner(executorService,
				new QueryRunner(ds));

		// 模拟一次长时间的数据库更新操作select sleep(1),name from sys_user where name='admin';
		Future<Map<String, Object>> updateCallable1;
		Future<Map<String, Object>> updateCallable2;
		Future<Map<String, Object>> updateCallable3;
		Future<Map<String, Object>> updateCallable4;
		try {
			updateCallable1 = asyncRun
					.query("select sleep(3), device_type, id from bjmetro_l7_iscs_dic where id=?",
							new MapHandler(), 70816003074L);
			updateCallable2 = asyncRun
					.query("select sleep(1), device_type, id from bjmetro_l7_iscs_dic where id=?",
							new MapHandler(), 70816003074L);
			updateCallable3 = asyncRun
					.query("select sleep(4), device_type, id from bjmetro_l7_iscs_dic where id=?",
							new MapHandler(), 70816003074L);
			updateCallable4 = asyncRun
					.query("select sleep(2), device_type, id from bjmetro_l7_iscs_dic where id=?",
							new MapHandler(), 70816003074L);
			long start = System.currentTimeMillis();

			System.out.println("中间的输出");
			// 输出操作结果
			Map<String, Object> result1 = updateCallable1.get();
			Map<String, Object> result2 = updateCallable2.get();
			Map<String, Object> result3 = updateCallable3.get();
			Map<String, Object> result4 = updateCallable4.get();
			long end = System.currentTimeMillis();
			LOGGER.info("执行中间可以操作的空闲时间:" + (end - start));
			// 限制该数据库操作的超时时间为5000毫秒
			// Integer result = updateCallable.get(5000, TimeUnit.SECONDS);
			LOGGER.info("数据库操作结果:\t"
					+ ToStringBuilder.reflectionToString(result1));
			LOGGER.info("数据库操作结果:\t"
					+ ToStringBuilder.reflectionToString(result2));
			LOGGER.info("数据库操作结果:\t"
					+ ToStringBuilder.reflectionToString(result3));
			LOGGER.info("数据库操作结果:\t"
					+ ToStringBuilder.reflectionToString(result4));
			// List<User> userList = queryCallable.get();
			// if(userList != null){
			// System.out.println(userList);
			// }
		} catch (SQLException e) {
			LOGGER.error(e);
		} catch (InterruptedException e) {
			LOGGER.error(e);
		} catch (ExecutionException e) {
			LOGGER.error(e);
		} finally {
			try {
				if (ds != null)
					ds.close();
				executorService.shutdown();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("测试结束");
		}
	}
}
