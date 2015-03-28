package com.nanxiaoqiang.test.javastudytest.lang.base.reflect.demo1;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ClassMethodUtils {

	public static void getClassInfo(Object o) {
		Class<?> c = o.getClass();
		getClassName(c);
		getClassMethods(c);
		getConstructors(c);
	}

	private static void getConstructors(Class<?> c) {
		System.out.println("构造方法");
		Constructor<?>[] cs = c.getConstructors();
		for (Constructor<?> ct : cs) {
			System.out.print(Modifier.toString(ct.getModifiers()) + " ");
			System.out.print(ct.getName() + "(");
			Class<?>[] pts = ct.getParameterTypes();
			for (Class<?> clazz : pts) {
				System.out.print(clazz.getName() + ",");
			}
			System.out.print(")");
			Class<?>[] cts = ct.getExceptionTypes();
			if (cts.length > 0) {
				System.out.print(" throws ");
				for (Class<?> class1 : cts) {
					System.out.print(class1.getName() + ",");
				}
			}
			System.out.println("");
		}
	}

	private static void getClassMethods(Class<?> c) {
		System.out.println("方法");
		Method[] ms = c.getMethods();
		for (Method m : ms) {
			System.out.print(Modifier.toString(m.getModifiers()) + " ");
			Class<?> c1 = m.getReturnType();
			System.out.print(c1.getName() + " ");
			System.out.print(m.getName() + "(");
			Class<?>[] cs = m.getParameterTypes();
			for (Class<?> clazz : cs) {
				System.out.print(clazz.getName() + ",");
			}
			System.out.print(")");
			Class<?>[] cts = m.getExceptionTypes();
			if (cts.length > 0) {
				System.out.print(" throws ");
				for (Class<?> class1 : cts) {
					System.out.print(class1.getName() + ",");
				}
			}
			System.out.println("");
		}
	}

	private static void getClassName(Class<?> c) {
		System.out.println("类名:" + c.getName());
	}

	public ClassMethodUtils() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try {
			ClassMethodUtils.getClassInfo(String.class.newInstance());
			// newInstance()会报错java.lang.InstantiationException
			// Caused by: java.lang.NoSuchMethodException: void.<init>()
			ClassMethodUtils.getClassInfo(int.class.newInstance());
			// newInstance()会报错java.lang.InstantiationException
			// Caused by: java.lang.NoSuchMethodException: void.<init>()
			ClassMethodUtils.getClassInfo(void.class.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
