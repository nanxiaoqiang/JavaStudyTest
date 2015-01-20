package com.nanxiaoqiang.test.javastudytest.lang.base;

/**
 * 百度面试的时候遇到的一个问题，当时答错了。
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2014年11月26日
 * 
 */
public class MethodValTest {

	public MethodValTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 直接更改传入值，但是实际上str不会改变
	 * 
	 * @param str
	 */
	public void changStr(String str) {
		System.out.println("before changStr:"+str);
		str = "abc";
		System.out.println("after changStr:"+str);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MethodValTest v = new MethodValTest();
		String str = "ab";
		v.changStr(str);
		System.out.println(str);
	}

}
