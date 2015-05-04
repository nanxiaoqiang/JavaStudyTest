package com.nanxiaoqiang.test.javastudytest.util.concurrent.threadlocal;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;

public class Test1 {

	public static class MyThread implements Runnable {
		private ThreadLocal<Integer> threadlocal = new ThreadLocal<>();

		private Integer notlocal = 0;

		@Override
		public void run() {
			Integer integer = RandomUtils.nextInt(0, 500);
			threadlocal.set(integer);
			notlocal = integer.intValue();
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("threadlocal value is: " + threadlocal.get()
					+ " and not ThreadLocal value is " + notlocal);
		}
	}

	public Test1() {
	}

	public static void main(String[] args) {
		MyThread myrunnable = new MyThread();
		Thread t1 = new Thread(myrunnable);
		Thread t2 = new Thread(myrunnable);
		Thread t3 = new Thread(myrunnable);
		t1.start();
		t2.start();
		t3.start();
	}

}
