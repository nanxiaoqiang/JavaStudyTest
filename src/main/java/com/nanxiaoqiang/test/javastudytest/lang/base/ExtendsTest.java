package com.nanxiaoqiang.test.javastudytest.lang.base;

/**
 * 只要是用来记忆类的继承时候每个代码块的执行顺序
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年11月4日
 */
public class ExtendsTest extends A {

	public ExtendsTest() {
		System.out.println("B Constructor.");
	}

	{
		System.out.println("B non.");
	}
	static {
		System.out.println("B static.");
	}

	public static void main(String[] args) {
		new ExtendsTest();
		// 实际执行顺序是：
		// A static.
		// B static.
		// A non.
		// A Constructor.
		// B non.
		// B Constructor.
	}

}

class A {

	public A() {
		System.out.println("A Constructor.");
	}

	{
		System.out.println("A non.");
	}

	static {
		System.out.println("A static.");
	}

}
