package com.nanxiaoqiang.test.netflix.hystrix.finf;

import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * getFallback中getFallback，模拟通过pin取得id
 * 
 * @author nanxiaoqiang
 * 
 * @version 2016年11月6日
 */
public class CommandInFallbackCommang extends HystrixCommand<Long> {

	private String pin;

	public CommandInFallbackCommang(String pin) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroupKey"));// 没设置超时，因为默认是1000
		this.pin = pin;
	}

	@Override
	protected Long getFallback() {
		return new FromCacheCommand(this.pin).execute();// 模拟取得Id失败后从Cache中取
	}

	@Override
	protected Long run() throws Exception {
		TimeUnit.SECONDS.sleep(2);// 模拟超时
		return Long.valueOf(this.pin.length());
	}

	public static void main(String[] args) {
		// 模拟取得Id出错，从缓存中取
		Long l = new CommandInFallbackCommang("CachedPin").execute();
		System.out.println(l);
		// 模拟取得Id出错，缓存也木有，返回null
		Long err = new CommandInFallbackCommang(null).execute();
		System.out.println(err);
	}

}
