package com.nanxiaoqiang.test.apache.common.dbcp;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PoolingDataSourceTest {
	private static Logger logger = LogManager
			.getLogger(PoolingDataSourceTest.class.getName());

	private String driver = "com.mysql.jdbc.Driver";

	private String url = "jdbc:mysql://172.20.98.208:3306/bjmetro?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";

	private String username = "bjmetro";
	private String password = "bjmetro";

	private ConnectionFactory connectionFactory;
	private PoolableConnectionFactory poolableConnectionFactory;
	private ObjectPool<PoolableConnection> connectionPool;

	public PoolingDataSourceTest() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connectionFactory = new DriverManagerConnectionFactory(url, username,
				password);
		poolableConnectionFactory = new PoolableConnectionFactory(
				connectionFactory, null);

		connectionPool = new GenericObjectPool<>(poolableConnectionFactory);

		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(100);
		config.setMaxIdle(10);
		// config.setTestOnBorrow(true);
		// config.

		poolableConnectionFactory.setPool(connectionPool);
		logger.info("初始化");
	}

	public static void main(String[] args) {
		PoolingDataSourceTest t = new PoolingDataSourceTest();
		// DataSource dataSource = t.setupDataSource();

		for (int i = 0; i < 20; i++) {
			new Thread(new QueryThread(t.setupDataSource())).start();
		}
	}

	public DataSource setupDataSource() {
		PoolingDataSource<PoolableConnection> dataSource = new PoolingDataSource<>(
				connectionPool);
		return dataSource;
	}
}

class QueryThread implements Runnable {
	private static Logger logger = LogManager.getLogger(QueryThread.class
			.getName());
	private DataSource dt;

	public QueryThread(DataSource dt) {
		this.dt = dt;
	}

	@Override
	public void run() {
		while (true) {
			QueryRunner runner = new QueryRunner(dt);
			try {
				logger.info("开始执行" + Thread.currentThread().getName());
				int sleep = RandomUtils.nextInt(1, 5);
				TimeUnit.SECONDS.sleep(sleep);
				@SuppressWarnings({ "rawtypes", "unchecked" })
				Object value = runner.query(
						"select count(*) from bjmetro_l7_iscs_dic_v",
						new ScalarHandler());
				logger.info(Thread.currentThread().getName() + ":" + sleep
						+ ":" + value.getClass().getName() + "|" + value);
				break;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
