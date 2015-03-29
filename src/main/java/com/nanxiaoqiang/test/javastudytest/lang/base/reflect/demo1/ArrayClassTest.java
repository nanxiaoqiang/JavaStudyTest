package com.nanxiaoqiang.test.javastudytest.lang.base.reflect.demo1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 集合中的泛型，用invoke可以绕过泛型检查，但是还是不能遍历了。╮(￣▽￣")╭
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年3月29日
 *
 */
public class ArrayClassTest {

	public ArrayClassTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		@SuppressWarnings("rawtypes")
		ArrayList list1 = new ArrayList();
		ArrayList<String> list2 = new ArrayList<>();
		Class<?> c1 = list1.getClass();
		Class<?> c2 = list2.getClass();
		System.out.println("c1 == c2?:" + (c1 == c2));
		Method m21 = c2.getMethod("add", Object.class);
		m21.invoke(list2, 123);
		System.out.println("list2 size : " + list2.size());
		System.out.println(list2);
		for (int i = 0; i < list2.size(); i++) {
			// get(i)会报错java.lang.ClassCastException: java.lang.Integer cannot
			// be cast to java.lang.String
			System.out.println(list2.get(i).getClass().getName() + "|"
					+ list2.get(i));
		}

	}
}
