package com.nanxiaoqiang.test.spring.integration.task.async.test1;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

	private static Logger logger = LogManager.getLogger(AsyncService.class.getName());

	public AsyncService() {
	}

	@Async("myexecutor")
	public void doSomeThing5sec() {
		try {
			logger.info("begin doSomething5sec method");
			TimeUnit.SECONDS.sleep(5);
			logger.info("end doSomething5sec method");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Async("myexecutor")
	public void doSomeThing8sec() {
		try {
			logger.info("begin doSomething8sec method");
			TimeUnit.SECONDS.sleep(8);
			logger.info("end doSomething8sec method");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Async("myexecutor")
	public Future<String> doSomeThing6Sec(int i) {
		try {
			logger.info("begin doSomeThing6Sec method:" + i);
			i = i + 8;
			TimeUnit.SECONDS.sleep(8);
			logger.info("end doSomeThing6Sec method:" + i);
			return new AsyncResult<String>("Result is " + i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
