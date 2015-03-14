package com.nanxiaoqiang.test.javastudytest.lang.base.extendstest.t1;

/**
 * 如果abstract和interface都有怎么办
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年3月14日
 *
 */
public class C extends B implements A {

	protected int b = 33;

	public C() {
		System.out.println("constructor from c");
	}

	public static void main(String[] args) {
		C c = new C();
		// System.out.println("str is:" + c.str);// 编译器会提示ambiguous，有歧义
		System.out.println("b is:" + c.b);
		c.doSomething();
		c.doAbstract();
	}

	@Override
	public void doSomething() {
		System.out.println("hehe");
	}
}
