package com.nanxiaoqiang.test.storm.helloworld2.mem;

import java.util.Map;

import com.google.common.collect.Maps;
import com.nanxiaoqiang.test.storm.helloworld2.entity.IscsDicValue;

public class MemObject {

	private Map<Long, IscsDicValue> map = Maps.newConcurrentMap();

	private MemObject() {
		// TODO Auto-generated constructor stub
		// 初始化map
		// 一个假的Map
		for (int i = 0; i < 5000; i++) {
			IscsDicValue idv = new IscsDicValue();
			idv.setId((long) (i + 1));
			map.put((long) (i + 1), idv);
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

	// public void setMap(Map<Long, IscsDicValue> map) {
	// this.map = map;
	// }
}
