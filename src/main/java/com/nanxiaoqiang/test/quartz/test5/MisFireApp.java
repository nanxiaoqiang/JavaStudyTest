package com.nanxiaoqiang.test.quartz.test5;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Quartz的过期处理<br/>
 * quartz有个全局的参数misfireThreshold设置可以允许的超时时间，超过了就不执行，未超过就执行。
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年7月9日
 */
public class MisFireApp {
	private static Logger logger = LogManager.getLogger(MisFireApp.class
			.getName());

	public MisFireApp() {
		// TODO Auto-generated constructor stub
	}

	public void run() {
		try {
			logger.info("初始化");
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			Date start = DateBuilder.nextGivenSecondDate(null, 5);

			// 什么都不设置的话基本上就是前一个Job刚刚结束新的Job就运行
			JobDetail job = JobBuilder.newJob(MisFireJob.class)
					.withIdentity("Job1", "Group1").build();

			SimpleTrigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity("Trigger1", "Group1")
					.startAt(start)
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withIntervalInSeconds(3).repeatForever())
					.build();

			Date ft = scheduler.scheduleJob(job, trigger);

			logger.info(job.getKey() + " 运行时间: " + ft + " 。运行次数: "
					+ trigger.getRepeatCount() + " 。运行间隔： "
					+ trigger.getRepeatInterval() / 1000 + " 秒");

			JobDetail job2 = JobBuilder.newJob(MisFireJob.class)
					.withIdentity("Job2", "Group1").build();

			SimpleTrigger trigger2 = TriggerBuilder
					.newTrigger()
					.withIdentity("Trigger2", "Group1")
					.startAt(start)
					.withSchedule(
							SimpleScheduleBuilder
									.simpleSchedule()
									.withIntervalInSeconds(3)
									.repeatForever()
									.withMisfireHandlingInstructionNowWithExistingCount())
					.build();

			Date ft2 = scheduler.scheduleJob(job2, trigger2);

			logger.info(job2.getKey() + " 运行时间: " + ft2 + " 。运行次数: "
					+ trigger2.getRepeatCount() + " 。运行间隔： "
					+ trigger2.getRepeatInterval() / 1000 + " 秒");

			scheduler.start();
			TimeUnit.MINUTES.sleep(3);
			scheduler.shutdown(true);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MisFireApp app = new MisFireApp();
		app.run();
	}

}
