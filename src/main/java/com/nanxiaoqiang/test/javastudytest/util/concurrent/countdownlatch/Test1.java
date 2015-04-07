package com.nanxiaoqiang.test.javastudytest.util.concurrent.countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 还是搞不太明白
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年4月7日
 *
 */
public class Test1 {

	private final static int GROUP_SIZE = 5;

	private final static Random random = new Random();

	public Test1() {
		// TODO Auto-generated constructor stub
	}

	public static void processOneGroup(final String groupName)
			throws InterruptedException {
		final CountDownLatch preCountDown = new CountDownLatch(GROUP_SIZE);
		final CountDownLatch startCountDown = new CountDownLatch(1);
		final CountDownLatch endCountDown = new CountDownLatch(GROUP_SIZE);

		System.out.println("开始！*****************************");

		for (int i = 0; i < GROUP_SIZE; i++) {
			new Thread(String.valueOf(i)) {
				public void run() {
					preCountDown.countDown();// 准备
					System.out.println("[" + groupName + "],线程 "
							+ this.getName() + " 准备就绪");

					try {
						// 等待开始指令
						startCountDown.countDown();
						startCountDown.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					long time = Math.abs(random.nextInt()) % 1000;

					try {
						Thread.sleep(time);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("[" + groupName + "],线程 "
							+ this.getName() + " 执行完毕：time:" + time);
					endCountDown.countDown();
				}
			}.start();
		}
		preCountDown.await();// 等待所有线程就位
		System.out.println("开始执行:");
		startCountDown.await();// 所有线程执行
		endCountDown.await();// 等待多个进程结束
		System.out.println("所有程序执行完毕");
	}

	public static void main(String[] args) throws InterruptedException {
		processOneGroup("test");
	}

}
