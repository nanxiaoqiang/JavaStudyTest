package com.nanxiaoqiang.test.storm.server.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 持久话的属性对象
 * 
 * @author nanxiaoqiang
 * 
 * @version v0.1 2014年6月18日
 */
public class Constants {

	private static Logger logger = LogManager.getLogger(Constants.class
			.getName());

	private Constants() {
		logger.debug("can not use constructor!");
	}

	// MQ
	public static final String MQ_URL = "mq_url";
	public static final String MQ_QUEUE = "mq_queue";
	public static final String MQ_USR = "mq_usr";
	public static final String MQ_PWD = "mq_pwd";
	// database:oracle、mysql、postgresql、sqlserver
	public static final String DATABASE_JDBC_DRIVER = "database_jdbc_driver";
	public static final String DATABASE_URL = "database_url";
	public static final String DATABASE_USR = "database_usr";
	public static final String DATABASE_PWD = "database_pwd";
}
