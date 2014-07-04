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
		Integer num5 = new Integer(128);
		Integer num6 = new Integer(128);
		System.out.println(num1 == num2);
		System.out.println(num3 == num4);// 注意！两个Integer的比较仍然是比较指针的内存值（栈）。所以会不一样！
		System.out.println(num5 == num6);// 不论是new Integer(int)还是直接附值两个对象都不想等
		System.out.println(num1 == num5);
		System.out.println(num3 == num5);
		System.out.println(num3.intValue() == num4);
		System.out.println(num3.intValue() == num4.intValue());
		System.out.println(num1 == num3);
		System.out.println("******** 我是分隔符 **********");
		int num01 = 32;
		int num02 = 32;
		Integer num03 = 32;
		Integer num04 = 32;
		Integer num05 = new Integer(32);
		Integer num06 = new Integer(32);
		System.out.println(num01 == num02);
		System.out.println(num03 == num04);// 居然相等了！在[-128,127]之间的数字直接附值内存里就是数字！
		System.out.println(num05 == num06);// 但是用new的方法附初始值。全部指向的是栈内存！
		System.out.println(num01 == num03);
		System.out.println(num01 == num05);
		System.out.println(num03 == num05);
		System.out.println(num03.intValue() == num05.intValue());
	}

}
