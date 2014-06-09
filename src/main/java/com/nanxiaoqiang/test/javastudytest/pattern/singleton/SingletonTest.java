package com.nanxiaoqiang.test.javastudytest.pattern.singleton;

/**
 * 单例模式测试
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月10日
 */
public class SingletonTest {
	private static SingletonTest instance = null;

	/**
	 * 默认的单例模式，有个危险是线程不安全的！
	 * 
	 * @return
	 */
	public SingletonTest getInstance() {
		if (instance == null) {
			instance = new SingletonTest();
		}
		return instance;
	}

	/**
	 * 线程安全的单例模式，但是可能影响性能
	 * 
	 * @return
	 */
	public synchronized SingletonTest getInstanceThreadSafe() {
		if (instance == null) {
			instance = new SingletonTest();
		}
		return instance;
	}

	/**
	 * 双重检查，只在instance为null的时候初始化，线程安全。但是据说是Anti-Pattern。
	 * 
	 * @return
	 */
	public SingletonTest getInstanceThreadSafe2() {
		if (instance == null) {
			synchronized (SingletonTest.class) {
				if (instance == null) {
					instance = new SingletonTest();
				}
			}
		}
		return instance;
	}

	// 直接初始化
	private static final SingletonTest INSTANCE = new SingletonTest();

	public SingletonTest getInstanceEagerInit() {
		return INSTANCE;
	}

	private SingletonTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	// 真正的安全

	private static class SingletonHolder {
		public final static SingletonTest instance = new SingletonTest();
	}

	/**
	 * Initialization on Deamon Holder。使用内部类做到延迟加载对象，利用Java虚拟机特性保证线程安全
	 * 
	 * @return
	 */
	public static SingletonTest getInstanceWithHolder() {
		return SingletonHolder.instance;
	}

	// 号称最直接直接用enum不用class直接就写instance;属性就行,调用Est.instance;
	public enum Est {
		instance;
		public void dosomething() {

		}
	}
}
