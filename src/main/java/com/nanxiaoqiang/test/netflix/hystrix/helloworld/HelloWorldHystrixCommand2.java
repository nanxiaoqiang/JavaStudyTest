package com.nanxiaoqiang.test.netflix.hystrix.helloworld;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import rx.Observable;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HelloWorldHystrixCommand2  extends HystrixCommand<String> {

	private final String name;

	public HelloWorldHystrixCommand2(String name) {
		super(HystrixCommandGroupKey.Factory.asKey(name));
		this.name = name;
	}

	@Override
	protected String run() throws Exception {
		Random r = new Random();
		int a = r.nextInt(10) + 1;
		if (a > 5) {
			throw new Exception(a + "");
		}
		return Thread.currentThread().getName() + " - " + a +" - Hello " + this.name + " !";
	}

	@Override
	protected String getFallback() {// 异常的处理
		Throwable ex = this.getExecutionException();
		System.out.println(ex.getClass().getName());
		String message = ex.getMessage();
		return Thread.currentThread().getName() + " - " + message +" - Hello Anonymous !";
		// return super.getFallback();
		// throw new UnsupportedOperationException("No fallback available.");
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String str = new HelloWorldHystrixCommand2("nanxiaoqiang").execute();
		Future<String> future = new HelloWorldHystrixCommand2("南肖墙").queue();
		Observable<String> observable = new HelloWorldHystrixCommand2("nxq").observe();
		System.out.println(str);
		System.out.println(future.get());
		System.out.println(observable.toBlocking().single());
	}

}
