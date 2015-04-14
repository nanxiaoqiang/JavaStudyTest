package com.nanxiaoqiang.test.javastudytest.pattern.masterworker.test1;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Master-Worker实测
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年4月14日
 *
 */
public class TestMasterWorker {

	public TestMasterWorker() {
	}

	public class PlusWorker extends Worker {
		public Object handle(Object input) {
			Integer i = (Integer) input;
			return i * i * i;// 立方Math.pow(i, 3)但是返回的是double
		}
	}

	/**
	 * 计算1~100的立方和
	 */
	@Test
	public void testMasterWorker() {
		long start = System.currentTimeMillis();
		// 新建Master，带着Worker的5个进程
		Master m = new Master(new PlusWorker(), 5);
		for (int i = 1; i < 101; i++)
			m.submit(i);// 提交100个数据源
		m.execute();// 执行
		int re = 0;
		Map<String, Object> resultMap = m.getResultMap();
		while (resultMap.size() > 0 || !m.isComplete()) {
			Set<String> keys = resultMap.keySet();
			String key = null;
			for (String k : keys) {
				key = k;
				break;
			}
			Integer i = null;
			if (key != null)
				i = (Integer) resultMap.get(key);
			if (i != null)
				re += i;
			if (key != null)
				resultMap.remove(key);
		}
		long end = System.currentTimeMillis();
		System.out.println("testMasterWorker:" + re + "|used:" + (end - start));
	}

	@Test
	public void testPlus() {
		long start = System.currentTimeMillis();
		int re = 0;
		for (int i = 1; i < 101; i++) {
			re += i * i * i;
		}
		long end = System.currentTimeMillis();
		System.out.println("testPlus:" + re + "|used:" + (end - start));
	}
}
