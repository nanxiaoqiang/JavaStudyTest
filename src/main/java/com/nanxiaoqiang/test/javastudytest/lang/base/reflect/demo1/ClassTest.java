package com.nanxiaoqiang.test.javastudytest.lang.base.reflect.demo1;

/**
 * 只要类一样，得到的Class对象就是一样的
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年3月28日
 *
 */
public class ClassTest {

	public ClassTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		ClassTest ct = new ClassTest();
		Class<?> c1 = ClassTest.class;
		Class<?> c2 = ct.getClass();
		Class<?> c3 = null;
		try {
			c3 = Class
					.forName("com.nanxiaoqiang.test.javastudytest.lang.base.reflect.demo1.ClassTest");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("c1 == c2:" + (c1 == c2));
		System.out.println("c1 == c3:" + (c1 == c3));
		System.out.println("c3 == c2:" + (c3 == c2));
	}
}
