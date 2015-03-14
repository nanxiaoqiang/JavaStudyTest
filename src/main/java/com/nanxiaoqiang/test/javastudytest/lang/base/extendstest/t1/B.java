package com.nanxiaoqiang.test.javastudytest.lang.base.extendstest.t1;

public abstract class B {
	String str = "fromB";
	int b = 22;

	public B() {
		System.out.println("constructor from b");
	}

	public abstract void doSomething();

	public void doAbstract() {
		System.out.println("this is from abstract method!");
	}
}
