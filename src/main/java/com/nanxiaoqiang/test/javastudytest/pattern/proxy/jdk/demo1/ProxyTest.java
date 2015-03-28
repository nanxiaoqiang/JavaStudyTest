package com.nanxiaoqiang.test.javastudytest.pattern.proxy.jdk.demo1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.nanxiaoqiang.test.javastudytest.pattern.proxy.simple.Car;
import com.nanxiaoqiang.test.javastudytest.pattern.proxy.simple.Moveable;

/**
 * JDK动态代理
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年3月28日
 *
 */
public class ProxyTest {

	public ProxyTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Car car = new Car();
		InvocationHandler h = new CarProxy(car);
		Class<?> cls = car.getClass();
		Moveable m = (Moveable) Proxy.newProxyInstance(cls.getClassLoader(),
				cls.getInterfaces(), h);
		m.move();
	}
}
