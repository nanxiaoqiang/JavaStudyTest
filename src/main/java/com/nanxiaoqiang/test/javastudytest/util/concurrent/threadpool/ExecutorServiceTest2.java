package com.nanxiaoqiang.test.javastudytest.util.concurrent.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 主要是加上了ThreadFactory，还有AtomicInteger。<br/>
 * 看RocketMQ的com.alibaba.rocketmq. remoting.netty.nettyRemotingServer<br/>
 * 给每个Thread加名字
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月10日
 * 
 */
public class ExecutorServiceTest2 {
	private ExecutorService executorService;// 线程池
	private final int POOL_SIZE = 10;// 单个CPU线程池大小

	public ExecutorServiceTest2() {
		// Runtime的availableProcessor()方法返回当前系统的CPU数目.
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors() * POOL_SIZE, new ThreadFactory() {
			private AtomicInteger threadIndex = new AtomicInteger(0);

			@Override
			public Thread newThread(Runnable r) {
				// 给每个Thread加上了名称Thread_Executor_X,X为数字，采用AtomicInteger位了保证i++的运算线程安全
				// getAndIncrement == i++
				// incrementAndGet == ++i
				return new Thread(r, "Thread_Executor_"
						+ this.threadIndex.getAndIncrement());
			}

		});
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
		executorService.execute(new Handler2(str));
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ExecutorServiceTest2 a = new ExecutorServiceTest2();
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

}

class Handler2 implements Runnable {
	String str = null;
	String output = "";

	public Handler2(String str) {
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
			// 输出当前Thread名称+str
			System.out.println(Thread.currentThread().getName() + "|" + str);
		}
	}
}