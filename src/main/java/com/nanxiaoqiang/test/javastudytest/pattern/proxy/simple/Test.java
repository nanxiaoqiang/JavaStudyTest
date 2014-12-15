package com.nanxiaoqiang.test.javastudytest.pattern.proxy.simple;

/**
 * 简单的代理模式
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2014年12月15日
 * 
 */
public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Moveable m = new Car();
		LoggingCar c = new LoggingCar(m);
		c.move();
	}

}
