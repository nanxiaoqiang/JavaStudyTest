package com.nanxiaoqiang.test.netflix.hystrix.helloworld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Future;

import org.junit.Test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;

/**
 * Hystrix的HelloWorld测试类
 * 
 * @author nanxiaoqiang
 * 
 * @version 2016年11月5日
 */
public class HelloWorldHystrixCommandTest extends HystrixCommand<String>{

	private String name;
	
	public HelloWorldHystrixCommandTest(String name) {
		super(HystrixCommandGroupKey.Factory.asKey("HelloWorldExampleGroup"));
		this.name = name;
	}

	@Override
	protected String run() throws Exception {
		int nameLength = this.name.length();// 专门用于抛出异常
		if (nameLength == 3) {
			try {
				@SuppressWarnings("unused")
				long l = Long.parseLong(this.name);
			} catch (NumberFormatException e) {
				throw new HystrixBadRequestException("Hystrix不会捕获的异常", e);
			}
		}
		return "Hello " + this.name + "!";
	}

	@Override
	protected String getFallback() {
		return "Hello noname " + this.name;
	}
	
	public static class JUnitTestClass {
		
		@Test
		public void test() throws Exception {
			// 同步
			assertEquals("Hello World!", new HelloWorldHystrixCommandTest("World").execute());
			// 异步
			Future<String> fWorld = new HelloWorldHystrixCommandTest("AsyncWorld").queue();
			assertEquals("Hello AsyncWorld!", fWorld.get());
			
			// Hystrix会捕获异常
			String fallbackStr = new HelloWorldHystrixCommandTest(null).execute();
			assertEquals("Hello noname null", fallbackStr);
			
			// Hystrix不会捕获异常，需要手动捕获
			try {
				new HelloWorldHystrixCommandTest("南肖墙").execute();
			} catch (Throwable e) {
				assertTrue(e instanceof RuntimeException);// HystrixBadRequestException继承了RuntimeException 
				assertTrue(e instanceof HystrixBadRequestException);
				assertTrue(e.getCause() instanceof NumberFormatException);
			}
		}
	}
}
