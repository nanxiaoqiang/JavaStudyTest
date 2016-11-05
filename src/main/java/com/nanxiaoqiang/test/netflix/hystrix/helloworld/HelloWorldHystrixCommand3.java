package com.nanxiaoqiang.test.netflix.hystrix.helloworld;

import java.util.concurrent.ExecutionException;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;

/**
 * Hystrix的不想异常被getFallback捕获，使用HystrixBadRequestException
 * 
 * @author nanxiaoqiang
 * 
 * @version 2016年10月15日
 */
public class HelloWorldHystrixCommand3 extends HystrixCommand<String> {

	private final String name;

	public HelloWorldHystrixCommand3(String name) {
		super(HystrixCommandGroupKey.Factory.asKey(name));
		this.name = name;
	}

	@Override
	protected String run() throws Exception {
		try {
			System.out.println(name.length());
			return Thread.currentThread().getName() + " - Hello " + this.name + " !";
		} catch (Throwable ex) {
			// 使用HystrixBadRequestException包装异常，一场不会被getFallback方法捕获
			throw new HystrixBadRequestException(ex.getMessage(), ex);
		}
	}
	
	@Override
	protected String getFallback() {
		System.out.println("getFailback");
		return Thread.currentThread().getName() + " - Hello " + this.name + " | noname !";
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		try {
			String str = new HelloWorldHystrixCommand3(null).execute();
			System.out.println(str);
		} catch (Throwable ex) {
			System.out.println("this is exception");
			ex.printStackTrace();
		}
	}
}
