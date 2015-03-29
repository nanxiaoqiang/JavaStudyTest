package com.nanxiaoqiang.test.javastudytest.lang.base.reflect.demo1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 通过反射得到方法并运行
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年3月29日
 *
 */
public class MethodInvokeTest {

	public MethodInvokeTest() {
	}

	public static void main(String[] args) {
		TestA t = new TestA();
		Class<?> c = t.getClass();// TestA.class;
		try {
			Method m = c.getMethod("showAdd", int.class, int.class);
			Object o = m.invoke(t, 123, 456);
			System.out.println(o);
			Method m2 = c.getMethod("getShowAdd", String.class, int.class,
					int.class);
			Object o2 = m2.invoke(t, "a+b is ", 123, 456);
			System.out.println("o2 is " + o2.getClass().getName() + "|" + o2);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class TestA {
	public void showAdd(int a, int b) {
		System.out.println("a+b=" + (a + b));
	}

	public String getShowAdd(String prefix, int a, int b) {
		return prefix + (a + b);
	}
}
