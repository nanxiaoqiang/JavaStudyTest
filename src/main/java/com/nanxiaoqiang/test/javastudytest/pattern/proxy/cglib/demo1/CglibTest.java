package com.nanxiaoqiang.test.javastudytest.pattern.proxy.cglib.demo1;

import com.nanxiaoqiang.test.javastudytest.pattern.proxy.simple.Car;

public class CglibTest {

	public CglibTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		CglibProxy proxy = new CglibProxy();
		Car c = (Car) proxy.getProxy(Car.class);
		c.move();
	}

}
