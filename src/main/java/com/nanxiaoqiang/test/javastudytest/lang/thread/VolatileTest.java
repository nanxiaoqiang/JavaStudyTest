package com.nanxiaoqiang.test.javastudytest.lang.thread;

/**
 * 虽然使用了volatile，但是increase函数中的自增仍然不是一个原子操作，所以结果不会是200000。
 * volatile只能保证内存可见性，却没有保证对变量操作的原子性。
 * 
 * @description:
 * @author: nanxiaoqiang
 * @version: V1.00
 * @create Date: 2015年6月12日上午10:05:20
 */
public class VolatileTest {

	public static volatile int race = 0;

	public static void increase() {
		race++;
	}

	private static final int THREADS_COUNT = 20;

	public static void main(String[] args) {
		Thread[] threads = new Thread[THREADS_COUNT];
		for (int i = 0; i < THREADS_COUNT; i++) {
			threads[i] = new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 10000; i++) {
						increase();
					}
				}
			});
			threads[i].start();
		}

		while (Thread.activeCount() > 1)
			Thread.yield();

		System.out.println(race);
	}

}
