package com.nanxiaoqiang.test.netflix.hystrix.helloworld;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Hystrix的超时调用
 * 
 * @author nanxiaoqiang
 * 
 * @version 2016年10月15日
 */
public class HelloWorldHystrixCommand1Timeout extends HystrixCommand<String> {

	private final String name;

	public HelloWorldHystrixCommand1Timeout(String name) {
		// 第二个是超时时间，单位毫秒
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"), 2000);
		this.name = name;
	}

	@Override
	protected String run() throws Exception {
		TimeUnit.SECONDS.sleep(3);// 强制sleep 3秒超时
		return "Hello " + this.name + " !";
	}
	
	@Override
	protected String getFallback() {// 异常的处理
		return "Fail " + this.name + " !";
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String str = new HelloWorldHystrixCommand1Timeout("nanxiaoqiang").execute();
		System.out.println(str);// 返回fallback
	}
}
