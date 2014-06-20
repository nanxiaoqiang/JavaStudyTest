package com.nanxiaoqiang.test.javastudytest.util.concurrent.call;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.RandomUtils;

/**
 * 线程回调的测试
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月10日
 * 
 */
public class CallbackTest {

	public CallbackTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		// TODO Auto-generated method stub
		// 创建一个线程池
		ExecutorService pool = Executors.newFixedThreadPool(2);
		MyCallable a = new MyCallable("a");
		MyCallable b = new MyCallable("b");
		// 执行任务并获取Future对象
		// Future<T>的T是回调的返回值
		System.out.println("**********************");
		Future<String> f1 = pool.submit(a);
		Future<String> f2 = pool.submit(b);
		System.out.println("add to pool");
		System.out.println(f1.get());
		System.out.println(f2.get());
		System.out.println("当前进程的输出，但是得等到前两个输出完了才进行。。。╮(╯▽╰)╭ ");
		pool.shutdown();
	}
}

/**
 * 继承的Callable<T>的是回调返回的方法。
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月10日
 * 
 */
class MyCallable implements Callable<String> {
	private String str;

	public MyCallable(String str) {
		this.str = str;
	}

	@Override
	public String call() throws Exception {
		for (int i = 0; i < 10; i++) {
			Thread.sleep(RandomUtils.nextLong(500, 2000));
			System.out.println(Thread.currentThread().getName()
					+ " doing something. " + i);
		}
		return Thread.currentThread().getName() + "|" + str;
	}
}