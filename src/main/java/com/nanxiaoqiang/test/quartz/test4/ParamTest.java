package com.nanxiaoqiang.test.quartz.test4;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class ParamTest {
	private static Logger logger = LogManager.getLogger(ParamTest.class
			.getName());

	public ParamTest() {
	}

	public void run() {
		String color = "Red";
		int count = 1;
		try {
			logger.info("初始化");
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// 10s后统一运行
			Date start = DateBuilder.nextGivenSecondDate(null, 10);

			JobDetail job = JobBuilder.newJob(ParamJob.class)
					.withIdentity("Job1", "Group1").build();

			SimpleTrigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity("Trigger1", "Group1")
					.startAt(start)
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withIntervalInSeconds(5)
									.withRepeatCount(10)).build();

			job.getJobDataMap().put(ParamJob.COLOR, color);
			job.getJobDataMap().put(ParamJob.NUM, count);

			Date ft = scheduler.scheduleJob(job, trigger);

			logger.info(job.getKey() + " 运行时间: " + ft + " 。运行次数: "
					+ trigger.getRepeatCount() + " 。运行间隔： "
					+ trigger.getRepeatInterval() / 1000 + " 秒");

			scheduler.start();

			TimeUnit.MINUTES.sleep(5);

			scheduler.shutdown(true);
			SchedulerMetaData metaData = scheduler.getMetaData();
			logger.info("执行Jobc次数：" + metaData.getNumberOfJobsExecuted());
			logger.info("最后的结果:color:" + color + "|count:" + count);
		} catch (SchedulerException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ParamTest app = new ParamTest();
		app.run();
	}

}
