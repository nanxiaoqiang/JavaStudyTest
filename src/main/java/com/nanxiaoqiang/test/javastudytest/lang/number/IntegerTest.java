package com.nanxiaoqiang.test.javastudytest.lang.number;

/**
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月9日
 * 
 */
public class IntegerTest {

	public IntegerTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 输出最大最小值
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Long.MAX_VALUE);
		int num1 = 128;
		int num2 = 128;
		Integer num3 = 128;
		Integer num4 = 128;
		System.out.println(num1 == num2);
		System.out.println(num3 == num4);// 注意！两个Integer的比较仍然是比较指针的内存值（栈）。所以会不一样！
		System.out.println(num3.intValue() == num4);
		System.out.println(num3.intValue() == num4.intValue());
		System.out.println(num1 == num3);
	}

}
