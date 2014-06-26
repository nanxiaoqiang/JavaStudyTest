package com.nanxiaoqiang.test.javastudytest.util.concurrent.thread.demo2;

public class Calculator implements Runnable {

	private int number;

	public Calculator(int number) {
		this.number = number;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getId() + "|"
				+ Thread.currentThread().getName() + "|"
				+ Thread.currentThread().getState() + "|"
				+ Thread.currentThread().getPriority());
		for (int i = 0; i < 20; i++) {
			System.out.printf("%s:%d * %d = %d\n", Thread.currentThread()
					.getName(), number, i, number * i);
		}
	}

}
