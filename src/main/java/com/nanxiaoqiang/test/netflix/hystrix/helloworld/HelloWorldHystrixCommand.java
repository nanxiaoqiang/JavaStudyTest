package com.nanxiaoqiang.test.netflix.hystrix.helloworld;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import rx.Observable;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Hystrix的HelloWorld调用
 * 
 * @author nanxiaoqiang
 * 
 * @version 2016年10月15日
 */
public class HelloWorldHystrixCommand extends HystrixCommand<String> {

	private final String name;

	public HelloWorldHystrixCommand(String name) {
		super(HystrixCommandGroupKey.Factory.asKey(name));
		this.name = name;
	}

	@Override
	protected String run() throws Exception {
		return "Hello " + this.name + " !";
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String str = new HelloWorldHystrixCommand("nanxiaoqiang").execute();
		Future<String> future = new HelloWorldHystrixCommand("南肖墙").queue();
		Observable<String> observable = new HelloWorldHystrixCommand("nxq").observe();
		System.out.println(str);
		System.out.println(future.get());
		System.out.println(observable.toBlocking().single());
	}
}
