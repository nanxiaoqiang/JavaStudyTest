package com.nanxiaoqiang.test.javastudytest.util.concurrent.thread.demo1;

public class Calculator implements Runnable {

	private int number;

	public Calculator(int number) {
		this.number = number;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			System.out.printf("%s:%d * %d = %d\n", Thread.currentThread()
					.getName(), number, i, number * i);
		}
	}

}
