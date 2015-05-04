package com.nanxiaoqiang.test.javastudytest.util.concurrent.threadlocal;

import org.apache.commons.lang3.RandomUtils;

public class Test2 {
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int data = RandomUtils.nextInt(0, 500);
					// 这里的data必须定义为局部变量，否则线程间不能实现数据独立
					ThreadLocalTest2.getInstance().setName("name" + data);
					ThreadLocalTest2.getInstance().setAge(data);
					new A().get();
					new B().get();
				}
			}).start();
		}
	}

	static class A {
		public void get() {
			ThreadLocalTest2 myData = ThreadLocalTest2.getInstance();
			System.out.println("A from " + Thread.currentThread().getName()
					+ " get MyData :" + myData.getName() + ","
					+ myData.getAge());
		}
	}

	static class B {
		public void get() {
			ThreadLocalTest2 myData = ThreadLocalTest2.getInstance();
			System.out.println("B from " + Thread.currentThread().getName()
					+ " get MyData :" + myData.getName() + ","
					+ myData.getAge());
		}
	}

	public Test2() {
		// TODO Auto-generated constructor stub
	}

}

class ThreadLocalTest2 {
	private static ThreadLocal<ThreadLocalTest2> threadMap = new ThreadLocal<>();

	private ThreadLocalTest2() {

	}

	public static ThreadLocalTest2 getInstance() {
		ThreadLocalTest2 myData = threadMap.get();
		if (myData == null) {
			myData = new ThreadLocalTest2();
			threadMap.set(myData);
		}
		return myData;
	}

	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
