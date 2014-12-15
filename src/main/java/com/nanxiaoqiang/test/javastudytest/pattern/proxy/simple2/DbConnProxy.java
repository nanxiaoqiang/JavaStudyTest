package com.nanxiaoqiang.test.javastudytest.pattern.proxy.simple2;

public class DbConnProxy implements IDbConn {
	private DbConn dbconn = null;

	public DbConnProxy() {
	}

	@Override
	public String request() {
		if (dbconn == null) {
			dbconn = new DbConn();// 这里可以用一个单例！？
		}
		return dbconn.request();// 现场环境中可能是一个回调的Callback或者Future
	}

}
