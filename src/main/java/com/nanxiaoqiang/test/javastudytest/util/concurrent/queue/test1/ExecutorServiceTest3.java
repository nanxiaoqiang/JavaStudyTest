package com.nanxiaoqiang.test.javastudytest.util.concurrent.queue.test1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ExecutorService的BlockingQueue队列测试<br/>
 * 其实就是类似于MQ的一个例子
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月24日
 * 
 */
public class ExecutorServiceTest3 {
	private static Logger logger = LogManager
			.getLogger(ExecutorServiceTest3.class.getName());

	private ExecutorService executorService;// 线程池
	private final int POOL_SIZE = 10;// 单个CPU线程池大小

	public ExecutorServiceTest3() {
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors() * POOL_SIZE, new ThreadFactory() {
			private AtomicInteger i = new AtomicInteger(0);

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.concurrent.ThreadFactory#newThread(java.lang
			 * .Runnable)
			 */
			@Override
			public Thread newThread(Runnable r) {
				// 线程命名，其实那个AtomicInteger加不加都行。
				return new Thread(r, "Thread_Single_Executor_"
						+ this.i.incrementAndGet());
			}
		});
	}

	public static void main(String[] args) throws InterruptedException {

		ExecutorServiceTest3 a = new ExecutorServiceTest3();

		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(100);
		logger.debug("开始测试");
		Producer p1 = new Producer(queue);
		Producer p2 = new Producer(queue);
		Producer p3 = new Producer(queue);
		Producer p4 = new Producer(queue);
		Consumer c = new Consumer(queue);

		// 启动线程
		a.getExecutorService().execute(p1);
		a.getExecutorService().execute(p2);
		a.getExecutorService().execute(p3);
		a.getExecutorService().execute(p4);
		a.getExecutorService().execute(c);
		// 执行100s
		Thread.sleep(100 * 1000);
		logger.debug("执行100s，开始准备停止4个producer");
		p1.stop();
		p2.stop();
		p3.stop();
		p4.stop();

		logger.debug("关闭线程池子");
		// 停止线程池
		a.getExecutorService().shutdown();
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
