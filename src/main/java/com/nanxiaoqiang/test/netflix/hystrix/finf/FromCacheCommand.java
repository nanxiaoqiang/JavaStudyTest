package com.nanxiaoqiang.test.netflix.hystrix.finf;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class FromCacheCommand extends HystrixCommand<Long> {

	private String pin;

	public FromCacheCommand(String pin) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleCacheGkey"), 1000);
		this.pin = pin;
	}

	@Override
	protected Long run() throws Exception {
		return Long.valueOf(this.pin.length());// 实际操作中可以在此处为从缓存中取数
	}

	@Override
	protected Long getFallback() {
		return null;
	}

}
