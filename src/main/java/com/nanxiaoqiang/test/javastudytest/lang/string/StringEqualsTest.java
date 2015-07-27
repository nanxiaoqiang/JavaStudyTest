package com.nanxiaoqiang.test.javastudytest.lang.string;

/**
 * String的==的测试
 * 
 * @author nanxiaoqiang
 * 
 * @version 2015年1月18日
 */
public class StringEqualsTest {

	final String a1 = "a";
	String a2 = "a";
	String b1 = a1 + "b";
	String b2 = a2 + "b";
	String b = "ab";
	static String c = "ab";
	String d = null;

	public StringEqualsTest() {
	}

	public String getA2() {
		return a2;
	}

	public void test() {
		String b3 = getA2() + "b";
		System.out.println(a1 == a2);// true
		System.out.println(a1 == "a");// true
		System.out.println(a2 == "a");// true
		System.out.println(b1 == b);// true
		System.out.println(b2 == b);// false
		System.out.println(b3 == b);// false
	}

	public static void main(String[] args) {
		StringEqualsTest t = new StringEqualsTest();
		t.test();
		String x = "ab";
		String x1 = t.getA2() + "b";
		System.out.println("*************");
		System.out.println(x1 == x);// false
		System.out.println(x1 == t.b);// false
		System.out.println(x1 == t.b1);// false
		System.out.println(x1 == t.b2);// false
		System.out.println("*************");
		System.out.println(c == x);
		System.out.println(c == x1);
		System.out.println(c == t.b);
		System.out.println(c == t.b1);
		System.out.println(c == t.b2);
		t.d = "ab";
		System.out.println("*************");
		System.out.println(t.d == x);
		System.out.println(t.d == x1);
		System.out.println(t.d == t.b);
		System.out.println(t.d == t.b1);
		System.out.println(t.d == t.b2);

	}
}
