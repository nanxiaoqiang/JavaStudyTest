package com.nanxiaoqiang.test.javastudytest.util.concurrent.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;

public class CompletionServiceTest {

	public static void main(String[] args) {

		ExecutorService executorService = Executors.newFixedThreadPool(3);
		CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executorService);
		try {
			for (int i = 0; i < 20; i++) {
				Task t = new Task(i);
				// completionService.submit(new CompletionServiceTest.Task(i));
				completionService.submit(t);
			}

			for (int i = 0; i < 20; i++) {
				// take 方法等待下一个结果并返回 Future 对象。
				// poll 不等待，有结果就返回一个 Future 对象，否则返回 null。
				System.out.println("A: " + completionService.take().get());
			}

		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} finally {
			executorService.shutdown();
			System.out.println("close");
		}

	}

	public static class Task implements Callable<Integer> {

		private Integer num;

		public Task(Integer num) {
			this.num = num;
		}

		@Override
		public Integer call() throws Exception {
			int sleep = RandomUtils.nextInt(0, 5);
			System.out.println(Thread.currentThread().getName() + "|" + this.num + "   sleep " + sleep);
			TimeUnit.SECONDS.sleep(sleep);
			return this.num;
		}

	}

}
