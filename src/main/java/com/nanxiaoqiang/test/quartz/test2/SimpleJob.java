package com.nanxiaoqiang.test.quartz.test2;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

public class SimpleJob implements Job {

	private static Logger logger = LogManager.getLogger(SimpleJob.class
			.getName());

	public SimpleJob() {
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobKey jobKey = context.getJobDetail().getKey();
		try {
			// 手动加了个延时，用来测试耗时Job的Interval
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Job:" + jobKey + " at "
				+ DateTime.now().toString("yyyy-MM-dd hh:mm:ss"));
	}

}
