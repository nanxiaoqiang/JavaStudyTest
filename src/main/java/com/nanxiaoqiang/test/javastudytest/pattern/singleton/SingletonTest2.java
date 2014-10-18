package com.nanxiaoqiang.test.javastudytest.pattern.singleton;

import java.util.concurrent.TimeUnit;

/**
 * 线程不安全的单例模式，会无限循环哦！
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年10月18日
 */
public class SingletonTest2 {
	static SingletonTest2 instance = new SingletonTest2();

	private static SingletonTest2 instance2 = new SingletonTest2();

	private static SingletonTest2 instance3;

	private SingletonTest2() {
		try {
			System.out.println("SingletonTest2! constractor. start!");
			TimeUnit.SECONDS.sleep(2);
			if (instance3 == null) {
				instance3 = new SingletonTest2();
				System.out.println("instance3 created:" + instance3);
			}
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static SingletonTest2 getInstance2() {
		return instance2;
	}

	public static SingletonTest2 getInstance3() {
		return instance3;
	}

	public static void main(String[] args) {
		System.out.println(SingletonTest2.instance);
		System.out.println(SingletonTest2.instance);
		SingletonTest2 a1 = SingletonTest2.getInstance3();
		SingletonTest2 a2 = SingletonTest2.getInstance3();
		System.out.println(a1);
		System.out.println(a2);
	}
}
