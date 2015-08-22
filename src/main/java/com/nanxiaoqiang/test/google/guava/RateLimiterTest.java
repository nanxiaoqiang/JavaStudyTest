package com.nanxiaoqiang.test.google.guava;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;

/**
 * 
 * @ClassName: RateLimiterTest
 * @Description: RateLimiter的测试类,RateLimiter并不提供公平性的保证。
 * @author nanxiaoqiang nanxiaoqiang_gmail_com
 * @date 2015年8月22日 下午7:14:24
 *
 */
public class RateLimiterTest {
	// 速率是每秒两个许可

	// 根据指定的稳定吞吐率创建RateLimiter，这里的吞吐率是指每秒多少许可数（通常是指QPS，每秒多少查询）
	final RateLimiter rateLimiter = RateLimiter.create(2.0);

	void submitTasks(List<RateLimiterTestTask> tasks, ExecutorService executor) {
		for (RateLimiterTestTask task : tasks) {
			System.out.println("now rate is " + rateLimiter.getRate());
			// 从RateLimiter获取一个许可，该方法会被阻塞直到获取到请求
			rateLimiter.acquire(); // 也许需要等待
			executor.submit(task);
		}
	}

	// 再举另外一个例子，
	// 想象下我们制造了一个数据流，并希望以每秒5kb的速率处理它。
	// 可以通过要求每个字节代表一个许可，然后指定每秒5000个许可来完成：

	// 每秒5000个许可
	final RateLimiter netRateLimiter = RateLimiter.create(5000.0);

	void submitPacket(byte[] packet) {
		// 从RateLimiter获取指定许可数，该方法会被阻塞直到获取到请求
		netRateLimiter.acquire(packet.length);
		// 具体的处理
		// networkService.send(packet);
	}

	public static void main(String[] args) {
		RateLimiterTest rlt = new RateLimiterTest();
		List<RateLimiterTestTask> tasks = Lists.newArrayList();
		for (int i = 0; i < 20; i++) {
			tasks.add(new RateLimiterTestTask("is " + i));
		}
		rlt.submitTasks(tasks, Executors.newCachedThreadPool());
	}

}

class RateLimiterTestTask implements Callable<Integer> {
	String str;

	public RateLimiterTestTask(String str) {
		this.str = str;
	}

	@Override
	public Integer call() throws Exception {
		System.out.println("call execute.." + str);
		TimeUnit.SECONDS.sleep(1);
		System.out.println("call execute.." + str + " end");
		return 7;
	}
}