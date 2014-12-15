package com.nanxiaoqiang.test.javastudytest.pattern.proxy.simple2;

public class Test {

	public Test() {
	}

	public static void main(String[] args) {
		IDbConn d = new DbConnProxy();
		System.out.println(d.request());
	}
}
