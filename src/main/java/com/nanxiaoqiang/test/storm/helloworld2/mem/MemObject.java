package com.nanxiaoqiang.test.storm.helloworld2.mem;

import java.util.Map;

import org.apache.commons.lang3.RandomUtils;

import com.google.common.collect.Maps;
import com.nanxiaoqiang.test.storm.helloworld2.entity.IscsDicValue;

public class MemObject {

	/**
	 * 内存值
	 */
	private Map<Long, IscsDicValue> map = Maps.newConcurrentMap();

	/**
	 * 报警内存值
	 */
	private Map<Long, IscsDicValue> alarmMap = Maps.newConcurrentMap();

	/**
	 * 监控客流内存值
	 */
	private Map<Long, IscsDicValue> listeners = Maps.newConcurrentMap();

	private MemObject() {
		// TODO Auto-generated constructor stub
		// 初始化map
		// 一个假的Map
		for (int i = 0; i < 5000; i++) {
			IscsDicValue idv = new IscsDicValue();
			idv.setId((long) (i + 1));
			idv.setValue(RandomUtils.nextInt(0, 2) + "");// 给一个随机的值
			// 如果是4倍数的id，那么isAlarm为true
			if ((i + 1) % 4 == 0) {
				idv.setIs_alarm(1);// 置为报警
				idv.setAlarm_level(RandomUtils.nextInt(1, 5));// 给一个随机的1~5的报警值。
				idv.setAlarm_value(RandomUtils.nextInt(0, 2) + "");// 给一个随机的报警值
				alarmMap.put((long) (i + 1), idv);
			}
			map.put((long) (i + 1), idv);
			if (i == 101 || i == 201 || i == 301 || i == 401 || i == 501) {
				listeners.put((long) (i - 1), idv);
			}
		}

	}

	private static class MemObjectHolder {
		public final static MemObject instance = new MemObject();

	}

	public static MemObject getInstance() {
		return MemObjectHolder.instance;
	}

	public Map<Long, IscsDicValue> getMap() {
		return map;
	}

	public Map<Long, IscsDicValue> getAlarmMap() {
		return alarmMap;
	}

	public Map<Long, IscsDicValue> getListeners() {
		return listeners;
	}

	// public void setMap(Map<Long, IscsDicValue> map) {
	// this.map = map;
	// }
}
