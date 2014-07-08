package com.nanxiaoqiang.test.quartz.test3;

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
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		DateTime start = DateTime.now();
		JobKey jobKey = context.getJobDetail().getKey();
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("执行任务:" + jobKey + " | 开始执行时间:"
				+ start.toString("yyyy-MM-dd HH:mm:ss") + " |输出时间"
				+ DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
	}

}
