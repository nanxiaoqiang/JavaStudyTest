package com.nanxiaoqiang.test.javastudytest.util.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * List的测试，主要是看看contains
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月9日
 * 
 */
public class ArrayListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add("str" + i);
		}

		System.out.println("list contains 'str3'? " + list.contains("str3"));
		System.out.println("list indexof 'str3'? " + list.indexOf("str3"));
		System.out.println("list contains 'str11'? " + list.contains("str11"));
		System.out.println("list indexof 'str11'? " + list.indexOf("str11"));

		List<Object[]> objs = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			objs.add(new Object[] { 111, "asdasd" + i });
		}
		System.out.println(objs.toArray().getClass());

		// 此句话会报异常， java.lang.ClassCastException
		// 虽然实际上每个Object[] 中的Object又是一个Object[].
		// System.out.println(((Object[][]) (objs.toArray())).length);
		for (Object obj : objs) {
			Object[] o = (Object[]) obj;
			System.out.println(o.length);
		}

	}

}
