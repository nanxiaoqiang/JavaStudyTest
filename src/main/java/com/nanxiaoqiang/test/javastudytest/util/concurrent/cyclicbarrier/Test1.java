package com.nanxiaoqiang.test.javastudytest.util.concurrent.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class Test1 {
	private final static int THREAD_COUNT = 5;

	private final static CyclicBarrier CYLIC_BARRIER = new CyclicBarrier(
			THREAD_COUNT, new Runnable() {
				@Override
				public void run() {
					System.out.println("等待到一波进程任务完结了，可以干干自己的事情，休息半秒钟");
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("休息半秒钟结束，开始下一波任务了");
				}
			});

	public Test1() {
	}

	public static void main(String[] args) {
		System.out.println("开始");
		for (int i = 0; i < THREAD_COUNT; i++) {
			new Thread(String.valueOf(i)) {
				public void run() {
					try {
						System.out.println("线程[" + this.getName() + "]准备");
						CYLIC_BARRIER.await();
						System.out.println("线程[" + this.getName() + "]第一件事");
						CYLIC_BARRIER.await();
						System.out.println("线程[" + this.getName() + "]第二件事");
						CYLIC_BARRIER.await();
						System.out.println("线程[" + this.getName() + "]第三件事");
						CYLIC_BARRIER.await();
						System.out.println("线程[" + this.getName() + "]第四件事");
						CYLIC_BARRIER.await();
						System.out.println("线程[" + this.getName() + "]第五件事");
						CYLIC_BARRIER.await();
						System.out.println("线程[" + this.getName() + "]完毕");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

}
