package com.nanxiaoqiang.test.storm.server.db.mysql;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MySQLClient {

	private static Logger logger = LogManager.getLogger(MySQLClient.class
			.getName());

	/**
	 * MySQL数据库连接的DataSource
	 */
	private DataSource datasource = null;

	public MySQLClient() {
		logger.debug("MySQLClient");
		this.datasource = initDataSource();
	}

	private DataSource initDataSource() {
		logger.debug("配置dbcp数据源，初始化MySQL数据库datasource");
		// 配置dbcp数据源
		BasicDataSource dbcpDataSource = new BasicDataSource();
		dbcpDataSource.setUrl("MySQLConfig.MYSQL_URL");
		dbcpDataSource.setDriverClassName("MySQLConfig.MYSQL_JDBC_DRIVER");
		dbcpDataSource.setUsername("MySQLConfig.MYSQL_USERNAME");
		dbcpDataSource.setPassword("MySQLConfig.MYSQL_PASSWORD");
		dbcpDataSource.setDefaultAutoCommit(true);// "MySQLConfig.MYSQL_DEFAULT_AUTO_COMMIT");
		dbcpDataSource.setMaxTotal(100);// "MySQLConfig.MYSQL_MAX_TOTAL");
		// dbcpDataSource.set.setMaxActive(100);
		dbcpDataSource.setMaxIdle(10);// "MySQLConfig.MYSQL_MAX_IDLE");
		dbcpDataSource.setInitialSize(5);// "MySQLConfig.MYSQL_INITIAL_SIZE");
		// 心跳
		dbcpDataSource.setTestOnBorrow(true);// "MySQLConfig.MYSQL_TEST_ON_BORROW");
		dbcpDataSource.setValidationQuery("select 1");// "MySQLConfig.MYSQL_VALIDATION_QUERY");

		return (DataSource) dbcpDataSource;
	}
}
