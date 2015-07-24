package com.nanxiaoqiang.test.google.guava;

import com.google.common.base.Preconditions;

public class PreconditionsTest {

	public PreconditionsTest() {
	}

	public static void main(String[] args) {
		try {
			saySomething(0, "");
		} catch (Exception iae) {
			System.out.println("hehe, IllegalArgumentException");
		}
		try {
			saySomething(12, null);
		} catch (Exception npe) {
			System.out.println("hehe NullPointerException");
		}
	}

	private static void saySomething(int age, String name) {
		Preconditions.checkArgument(age > 0, "age must more than 0.");
		Preconditions.checkNotNull(name, "name can not be null.");
		System.out.println("age is " + age + ", name is " + name);
	}

}
