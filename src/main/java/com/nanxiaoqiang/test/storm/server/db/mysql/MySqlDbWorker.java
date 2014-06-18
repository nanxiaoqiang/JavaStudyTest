package com.nanxiaoqiang.test.storm.server.db.mysql;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MySqlDbWorker {
	private static Logger logger = LogManager.getLogger(MySqlDbWorker.class
			.getName());

	/**
	 * MySQL数据库连接的DataSource
	 */
	private DataSource datasource = null;

	/**
	 * 数据库连接的初始化，不用while(true)
	 */
	public void initDatabaseConns() {
		logger.debug("Worker初始化，初始化MySQL数据库datasource");
		datasource = MySqlDbWorker.initDataSource();
	}

	public static DataSource initDataSource() {
		logger.debug("配置dbcp数据源");
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

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	private static MySqlDbWorker worker = null;

	private MySqlDbWorker() {
		// 直接初始化连接
		this.initDatabaseConns();
	}

	public static MySqlDbWorker getInstance() {
		if (worker == null) {
			worker = new MySqlDbWorker();
		}
		return worker;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// /**
	// * 读取数据到HashMap(线程安全)
	// *
	// * @deprecated
	// */
	// public synchronized void initHashMap(Map<Long, Integer> memValue) {
	// // 主循环
	// // 事务执行
	// while (true) {
	// String sql = null;
	// try {
	// Thread.sleep(1000);
	// sql = "SELECT iscs_id AS id,iscs_value AS VALUE FROM "
	// + MySQLConfig.MYSQL_TABLE_RE;
	// QueryRunner runner = new QueryRunner(this.getDatasource());
	// logger.debug("执行SQL:" + sql);
	//
	// ResultSetHandler<List<IscsRe>> handler = new BeanListHandler<IscsRe>(
	// IscsRe.class);
	// List<IscsRe> list = runner.query(sql, handler);
	// memValue = new HashMap<>();
	// for (IscsRe re : list) {
	// memValue.put(re.getId(), re.getValue());
	// }
	// logger.info("初始化MySQL表" + MySQLConfig.MYSQL_TABLE_RE
	// + "到内存Map映射memValue完成");
	// logger.debug("输出内存表memValue:" + memValue);
	// break;
	// } catch (SQLException e) {
	// logger.error("程序执行出错！未完成初始化！！SQL:" + sql);
	// } catch (InterruptedException e) {
	// logger.error(e.getMessage());
	// }
	// }
	// }
	//
	// public synchronized void updateRe(int[][] values) {
	// // 主循环
	// // 事务执行
	// while (true) {
	// String sql = null;
	// try {
	// sql = "UPDATE " + MySQLConfig.MYSQL_TABLE_RE
	// + " SET iscs_value = ? where iscs_Id = ?";
	//
	// // 如果插入的数据量大于1W 50000
	// if (values.length > 5000) {
	// int t = (int) Math.ceil((long) values.length / 1000);
	// for (int i = 0; i < t; i++) {
	// int len = i + 1 == t ? values.length - 1000 * i : 1000;
	// Integer[][] ivalues = new Integer[len][2];
	//
	// for (int j = 0; j < len; j++) {
	// // ivalues[j] = ArrayUtils.toObject(values[j]);
	// ivalues[j] = new Integer[] {
	// Integer.valueOf(values[i * 1000 + j][1]),
	// Integer.valueOf(values[i * 1000 + j][0]) };
	// }
	//
	// QueryRunner runner = new QueryRunner(
	// this.getDatasource());
	// logger.debug("执行batch SQL:" + sql);
	// runner.batch(sql, ivalues);
	// logger.info("更新MySQL数据库表" + MySQLConfig.MYSQL_TABLE_RE
	// + "完成");
	// }
	// }
	//
	// // // Thread.sleep(1000);
	// // Integer[][] ivalues = new Integer[values.length][2];
	// // for (int i = 0; i < values.length; i++) {
	// // // ivalues[i] = ArrayUtils.toObject(values[i]);
	// // ivalues[i] = new Integer[] { Integer.valueOf(values[i][1]),
	// // Integer.valueOf(values[i][0]) };
	// // }
	// // QueryRunner runner = new QueryRunner(this.getDatasource());
	// // logger.debug("执行batch SQL:" + sql);
	// // runner.batch(sql, ivalues);
	// // logger.info("更新MySQL数据库表" + MySQLConfig.MYSQL_TABLE_RE +
	// // "完成");
	// break;
	// } catch (SQLException e) {
	// logger.error("程序执行出错！未完成初始化！！SQL:" + sql);
	// }
	// }
	// }

}
