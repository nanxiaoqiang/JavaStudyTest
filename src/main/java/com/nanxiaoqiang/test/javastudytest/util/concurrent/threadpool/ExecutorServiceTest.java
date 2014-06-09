package com.nanxiaoqiang.test.javastudytest.util.concurrent.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 多线程测试，输出0~9，但是输出的顺序应当是不同的。
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月9日
 * 
 */
public class ExecutorServiceTest {
	private ExecutorService executorService;// 线程池
	private final int POOL_SIZE = 10;// 单个CPU线程池大小

	public ExecutorServiceTest() {
		// Runtime的availableProcessor()方法返回当前系统的CPU数目.
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors() * POOL_SIZE);
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ExecutorServiceTest a = new ExecutorServiceTest();
		for (int i = 0; i < 10; i++) {
			a.doSomeThing(i + "");
		}
		System.out.println("应该先输出。");
		// 等待子线程都结束后才执行下边的方法。
		a.getExecutorService().awaitTermination(0, TimeUnit.DAYS);
		System.out.println("AWAIT");
		// a.getExecutorService().awaitTermination(Long.MAX_VALUE,
		// TimeUnit.DAYS);// 设置一直等着子线程结束。
		// 线程池的关闭，如果不写线程池不会被关闭。
		a.getExecutorService().shutdown();
		System.out.println("End");
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public void doSomeThing(String str) {
		// 接收客户连接,只要客户进行了连接,就会触发accept();从而建立连接
		// socket = serverSocket.accept();
		executorService.execute(new Handler(str));
	}

}

class Handler implements Runnable {
	String str = null;
	String output = "";

	public Handler(String str) {
		this.str = str;
	}

	@Override
	public void run() {
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		synchronized (this) {
			// Random r = new Random();
			// int rn = r.nextInt(500);
			System.out.println(str);
		}
	}
}
