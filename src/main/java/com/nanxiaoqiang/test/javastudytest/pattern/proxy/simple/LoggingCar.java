package com.nanxiaoqiang.test.javastudytest.pattern.proxy.simple;

public class LoggingCar implements Moveable {

	private Moveable m;

	public LoggingCar(Moveable m) {
		this.m = m;
	}

	@Override
	public void move() {
		System.out.println("日志开始");
		m.move();
		System.out.println("日志结束");
	}

}
