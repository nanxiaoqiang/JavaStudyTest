package com.nanxiaoqiang.test.javastudytest.util.concurrent.thread.demo2;

/**
 * 创建多个线程。根据优先级运行（不过看效果不是特别明显）<br/>
 * 看《Java7 Concurrency Cookbook》敲的代码
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月26日
 */
public class App {

	public App() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Thread threads[] = new Thread[10];
		Thread.State status[] = new Thread.State[10];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new Calculator(i));
			if (i % 2 == 0) {
				threads[i].setPriority(Thread.MAX_PRIORITY);
				// threads[i].setPriority(11);
				// 优先级必须在1～10之间。
				// 如果设置了太大的数会发生IllegalArgumentException 异常
			} else {
				threads[i].setPriority(Thread.MIN_PRIORITY);
			}
			status[i] = threads[i].getState();
		}
		for (int i = 0; i < 10; i++) {
			threads[i].start();
		}
	}
}
