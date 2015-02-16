package com.nanxiaoqiang.test.javastudytest.util.collection;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.storm.guava.collect.Lists;

/**
 * 一个比较大的数组怎么加到List中？
 * 
 * @author nanxiaoqiang
 * 
 * @version 2015年1月29日
 */
public class ArrayAndListTest {

	public ArrayAndListTest() {
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Integer[] s;
		int i;
		s = new Integer[10000];
		for (i = 0; i < 10000; i++) {
			s[i] = RandomUtils.nextInt(20000);
		}

		// 默认的add会很慢
		List<Integer> int_list = Lists.newArrayList();
		long start1 = System.currentTimeMillis();
		for (i = 0; i < 10000; i++) {
			int_list.add(s[i]);
		}
		long end1 = System.currentTimeMillis();
		System.out.println("time:" + (end1 - start1));

		// 直接用Arrays.asList相当于new ArrayList(s)
		// 再方法中s是直接赋值给ArrayListzhongde数组a的！
		List<Integer> int_list2 = Lists.newArrayList();
		long start2 = System.currentTimeMillis();
		int_list2 = Arrays.asList(s);
		long end2 = System.currentTimeMillis();
		System.out.println("time:" + (end2 - start2));

		// 同理，List转Array
		Integer[] xx = int_list.toArray(new Integer[10000]);

		System.out.println("***************************");

		int[] datas1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
		// 如果是基本类型int、double...int[]是一个类型
		// 在List中的E[] a会变成int[]类型
		@SuppressWarnings("rawtypes")
		List tt1 = Arrays.asList(datas1);
		// 必须经过Apache commons lang3的ArrayUtils的toObject处理一下换成Integer[]才行
		@SuppressWarnings("rawtypes")
		List tt2 = Arrays.asList(ArrayUtils.toObject(datas1));
		System.out.println(tt1.size() + "|" + tt2.size());
		System.out.println(tt1.get(0).getClass().getName());// [I 就是数组
		System.out.println(tt2.get(0).getClass().getName());// java.lang.Integer

	}
}
