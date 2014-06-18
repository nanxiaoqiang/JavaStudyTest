package com.nanxiaoqiang.test.javastudytest.lang.base;

/**
 * final的测试
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月18日
 * 
 */
public class FinalTest {
	/**
	 * 此属性加入了final，必须提前声明值。而且不可改变
	 */
	private final String str = "asdasdasd";

	private String str2;

	public String getStr() {
		return str;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public FinalTest() {
		str2 = "测试用的Str2";
	}

	public void change(String ss) {
		ss = "HOHO";
	}

	public void change2(final String ss) {
		// ss = "HEHE";
		// final的参数在方法中不能更改。
	}

	public static void main(String[] args) {
		FinalTest f = new FinalTest();
		System.out.println(f.getStr());// 只有get方法能用
		System.out.println(f.getStr2());
		f.setStr2("更改后的str2");
		System.out.println(f.getStr2());
		String str = "test";
		f.change(str);
		System.out.println(str);// 不会改变！str的值还是test
	}

}
