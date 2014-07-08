package com.nanxiaoqiang.test.quartz.test1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job {

	private static Logger logger = LogManager.getLogger(HelloJob.class
			.getName());

	public HelloJob() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("Say Hello World!!!"
				+ DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
	}

}
