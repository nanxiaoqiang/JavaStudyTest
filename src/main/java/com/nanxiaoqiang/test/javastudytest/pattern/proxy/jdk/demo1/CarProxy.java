package com.nanxiaoqiang.test.javastudytest.pattern.proxy.jdk.demo1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CarProxy implements InvocationHandler {

	private Object target;

	private CarProxy() {
	}

	public CarProxy(Object target) {
		super();
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("before method");
		method.invoke(target, args);
		System.out.println("after method");
		return null;
	}

}
