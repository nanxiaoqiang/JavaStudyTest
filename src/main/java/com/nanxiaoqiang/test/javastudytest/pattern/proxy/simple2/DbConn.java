package com.nanxiaoqiang.test.javastudytest.pattern.proxy.simple2;

import java.util.concurrent.TimeUnit;

public class DbConn implements IDbConn {

	public DbConn() {
		try {
			System.out.println("开始初始化");
			TimeUnit.SECONDS.sleep(3);// 模拟长时间操作
			System.out.println("初始化结束");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String request() {
		return "request back!";
	}

}
