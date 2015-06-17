package com.nanxiaoqiang.test.javastudytest.lang.base;

/**
 * 百度面试的时候遇到的一个问题，当时答错了。不论是如何，在方法内的改变如果不输出的话均不改变。
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2014年11月26日
 * 
 */
public class MethodValTest {

	public static String staticStr = "staticStr";

	public MethodValTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 直接更改传入值，但是实际上str不会改变
	 * 
	 * @param str
	 */
	public void changStr(String str) {
		System.out.println("before changStr:" + str);
		str = "abc";
		System.out.println("after changStr:" + str);
	}

	public static void changeStr(String str) {
		System.out.println("static before changStr:" + str);
		str = "abc";
		System.out.println("static after changStr:" + str);
	}

	public void changeInt(int i) {
		System.out.println("before changeInt:" + i);
		i = 100;
		System.out.println("after changeInt:" + i);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MethodValTest v = new MethodValTest();
		String str = "ab";
		v.changStr(str);
		System.out.println(str);
		changeStr(str);
		System.out.println(str);
		System.out.println("**************");
		System.out.println(staticStr);
		v.changStr(staticStr);
		System.out.println(staticStr);
		changeStr(staticStr);
		System.out.println(staticStr);
		System.out.println("**************");
		int i = 255;
		v.changeInt(i);
		System.out.println(i);
	}

}
