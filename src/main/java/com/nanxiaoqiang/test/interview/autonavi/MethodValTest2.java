package com.nanxiaoqiang.test.interview.autonavi;

/**
 * 阿里高德的一个笔试题目
 * 
 * @author nanxiaoqiang
 * 
 * @version 2015年9月9日
 */
public class MethodValTest2 {
	private static int i = 0;

	public MethodValTest2() {
	}

	private static int add() {
		return i++;
	}

	public static int tryDoing() {
		i = 0;
		try {
			return add();
		} finally {
			System.out.println(i++);
		}
	}

	public static void main(String[] args) {
		System.out.println(tryDoing());// 程序输出结果：1\n0
	}

}
