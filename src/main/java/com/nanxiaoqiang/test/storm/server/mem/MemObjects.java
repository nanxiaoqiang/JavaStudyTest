package com.nanxiaoqiang.test.storm.server.mem;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.nanxiaoqiang.test.storm.server.entity.IscsDic;

public class MemObjects implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(MemObjects.class
			.getName());
	/**
	 * 队列名称的数组
	 */
	private List<String> registerQueueName = Lists.newArrayList();

	/**
	 * 按照顺序读取数据库的DIC映射的List的数组！改为List抛弃使用Array数组，已经初始化好。
	 */
	public List<Map<Long, IscsDic>> discs = Lists.newArrayList();// newArrayList<>();

	public MemObjects() {
		logger.debug("MemObjects");
	}

}
