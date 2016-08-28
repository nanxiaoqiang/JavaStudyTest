package com.nanxiaoqiang.test.google.guava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 线程池最好要更改命名方式，在每个方法块内能够更改名称最好，这样好排查是哪个多线程哪个方法内出问题
 * @author nanxiaoqiang
 * 
 * @version 2016年8月29日
 */
public class ThreadFactoryBuilderTest {

	public static final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("myThread-%d").setDaemon(false).build();
	
	public static final ExecutorService executorService = Executors.newFixedThreadPool(3, threadFactory);

	public ThreadFactoryBuilderTest() {
	}

	public static void main(String[] args) {
		List<Future<String>> re = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			System.out.println("submit :" + i);
			Future<String> fre = executorService.submit(new MyCallable("submit " + i, 5));
			re.add(fre);
		}
		try {
			for (Future<String> f : re) {
				System.out.println(f.get());
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		executorService.shutdown();
		
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
		System.out.println(Thread.currentThread().getName() + " start");
		firstMethod();
		secondMethod();
		System.out.println(Thread.currentThread().getName() + " end");
		return "Result:" + Thread.currentThread().getName() + ":" + this.msg;
	}

	private void secondMethod() throws InterruptedException {
		Thread currentThread = Thread.currentThread();
		currentThread.setName(currentThread.getName() + "-secondMethod");
		TimeUnit.SECONDS.sleep(this.flag);
		System.out.println(Thread.currentThread().getName() + " sleep over");
	}

	private void firstMethod() throws InterruptedException {
		Thread currentThread = Thread.currentThread();
		currentThread.setName(currentThread.getName() + "-firstMethod");
		TimeUnit.SECONDS.sleep(this.flag);
		System.out.println(Thread.currentThread().getName() + " sleep over");
	}

}
