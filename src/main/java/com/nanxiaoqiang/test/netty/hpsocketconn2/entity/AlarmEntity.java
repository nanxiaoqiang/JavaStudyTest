package com.nanxiaoqiang.test.netty.hpsocketconn2.entity;

import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

public class AlarmEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(AlarmEntity.class
			.getName());

	/**
	 * 命令
	 */
	private String cmd;

	/**
	 * 数据
	 */
	private List<IscsAlarmHistory> alarms = Lists.newArrayList();

	public AlarmEntity() {
		logger.debug("AlarmEntity");
	}

	public AlarmEntity(String cmd, List<IscsAlarmHistory> alarms) {
		logger.debug("AlarmEntity");
		this.cmd = cmd;
		this.alarms = alarms;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public List<IscsAlarmHistory> getAlarms() {
		return alarms;
	}

	public void setAlarms(List<IscsAlarmHistory> alarms) {
		this.alarms = alarms;
	}

}
