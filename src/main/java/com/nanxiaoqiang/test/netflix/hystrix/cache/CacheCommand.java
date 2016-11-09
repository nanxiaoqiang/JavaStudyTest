package com.nanxiaoqiang.test.netflix.hystrix.cache;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * Hystrix缓存
 * 
 * @author nanxiaoqiang
 * 
 * @version 2016年11月9日
 */
public class CacheCommand extends HystrixCommand<String> {

	private String name;

	public CacheCommand(String name) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(CacheCommand.class.getSimpleName() + "Group")));
		this.name = name;
	}

	@Override
	protected String run() throws Exception {
		return name;
	}

	@Override
	protected String getCacheKey() {
		return name;
	}

	public static void main(String[] args) {
		// 实现缓存必须声明Request
		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		try {
			for (int i = 0; i < 10; i++) {
				CacheCommand c = new CacheCommand("key");
				String str = c.execute();
				System.out.println(str + "|" + c.isResponseFromCache);
				if (i == 5) {
					// 清除某个缓存
					HystrixRequestCache.getInstance(
							HystrixCommandKey.Factory.asKey(CacheCommand.class.getSimpleName()),
							HystrixConcurrencyStrategyDefault.getInstance()).clear("key");
				}
			}
		} finally {
			context.shutdown();
		}
	}

}
