package com.nanxiaoqiang.test.javastudytest.util.concurrent.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 提交超出线程池的Runnable会报RejectedExecutionException
 * 
 * @ClassName: ExecutorServiceTest3
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author nanxiaoqiang nanxiaoqiang_gmail_com
 * @date 2015年9月8日 上午9:05:39
 *
 */
public class ExecutorServiceTest3 {
	private ExecutorService executorService1;
	private ExecutorService executorService2;

	public ExecutorServiceTest3() {
		executorService1 = new ThreadPoolExecutor(2, 2, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1));// 定长为2

		executorService2 = new ThreadPoolExecutor(2, 2, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1));// 定长为2
	}

	public ExecutorService getExecutorService1() {
		return executorService1;
	}

	public void setExecutorService1(ExecutorService executorService1) {
		this.executorService1 = executorService1;
	}

	public ExecutorService getExecutorService2() {
		return executorService2;
	}

	public void setExecutorService2(ExecutorService executorService2) {
		this.executorService2 = executorService2;
	}

	public void executeSomeThing(String str) {
		executorService1.execute(new TimeHandler(str, 100));
	}

	public void submitSomeThing(String str) {
		executorService2.submit(new TimeHandler(str, 100));
	}

	public static void main(String[] args) {
		ExecutorServiceTest3 test = new ExecutorServiceTest3();
		for (int i = 0; i < 5; i++) {
			System.out.println("execute :" + i);
			test.executeSomeThing("execute" + i);// 超过线程池最大报RejectedExecutionException
		}
	}

}

class TimeHandler implements Runnable {
	String str = null;
	String output = "";
	long sleepTime = 0L;

	public TimeHandler(String str, long sleepTime) {
		this.str = str;
		this.sleepTime = sleepTime;
	}

	@Override
	public void run() {
		System.out.println(str + " start");
		try {
			TimeUnit.SECONDS.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (this) {
			System.out.println(str + " end");
		}
	}
}