package com.nanxiaoqiang.test.javastudytest.pattern.proxy.cglib.demo1;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {

	private Enhancer enhancer = new Enhancer();

	public CglibProxy() {

	}

	public Object getProxy(Class clazz) {
		// 设置创建子类的类
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);

		return enhancer.create();
	}

	/**
	 * obj 目标类子类 method 目标方法反射 args 蚕食 proxy代理类实例
	 */
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		// 代理类调用父类的方法
		System.out.println("before method");
		proxy.invokeSuper(obj, args);
		System.out.println("after method");
		return null;
	}

}
