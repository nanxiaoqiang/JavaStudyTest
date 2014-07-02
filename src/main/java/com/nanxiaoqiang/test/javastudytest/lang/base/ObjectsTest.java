package com.nanxiaoqiang.test.javastudytest.lang.base;

import java.util.Objects;

public class ObjectsTest {

	public ObjectsTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// java默认的Objects会提供带着顺序的hash方法。适用于Overri一个类的dehashCode方法
		// 如return Objects.hash(property1, property2 ...)
		System.out.println(Objects.hash(1, 2, 3));
		System.out.println(Objects.hash(2, 3, 1));
		System.out.println(Objects.hash(1, 2, 3));
	}

}
