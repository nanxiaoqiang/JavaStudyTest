package com.nanxiaoqiang.test.javastudytest.util.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @ClassName: ExecutorServiceTest4
 * @Description: submit用来执行Callable
 * @author nanxiaoqiang nanxiaoqiang_gmail_com
 * @date 2015年9月8日 下午9:27:43
 *
 */
public class ExecutorServiceTest4 {
	private ExecutorService executorService;

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public ExecutorServiceTest4() {
		executorService = new ThreadPoolExecutor(2, 8, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1));// 定长为2会报异常RejectedExecutionException
	}

	public Future<String> submitSomeThing(String str) {
		return executorService.submit(new MyCallable(str, 5));
	}

	public static void main(String[] args) {
		ExecutorServiceTest4 t = new ExecutorServiceTest4();
		List<Future<String>> re = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			System.out.println("submit :" + i);
			Future<String> fre = t.submitSomeThing("submit " + i);
			re.add(fre);// 超过线程池最大报RejectedExecutionException
		}
		try {
			for (Future<String> f : re) {
				System.out.println(f.get());
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		t.getExecutorService().shutdown();
	}

}

class MyCallable implements Callable<String> {
	private long flag = 0;
	private String msg = "";

	public MyCallable(String msg, long flag) {
		this.flag = flag;
		this.msg = msg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public String call() throws Exception {
		System.out.println(Thread.currentThread().getName() + "start");
		TimeUnit.SECONDS.sleep(this.flag);
		System.out.println(Thread.currentThread().getName() + "end");
		return "Result:" + Thread.currentThread().getName() + ":" + this.msg;
	}

}
