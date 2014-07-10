package com.nanxiaoqiang.test.quartz.test5;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
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
							SimpleScheduleBuilder.simpleSchedule()
									.withIntervalInSeconds(3).repeatForever()
									// .withMisfireHandlingInstructionFireNow()
									// ——以当前时间为触发频率立即触发执行
									// ——执行至FinalTIme的剩余周期次数
									// ——以调度或恢复调度的时刻为基准的周期频率，FinalTime根据剩余次数和当前时间计算得到
									// ——调整后的FinalTime会略大于根据starttime计算的到的FinalTime值
									//
									// .withMisfireHandlingInstructionIgnoreMisfires()
									// ——以错过的第一个频率时间立刻开始执行
									// ——重做错过的所有频率周期
									// ——当下一次触发频率发生时间大于当前时间以后，按照Interval的依次执行剩下的频率
									// ——共执行RepeatCount+1次
									//
									// .withMisfireHandlingInstructionNextWithExistingCount()
									// ——不触发立即执行
									// ——等待下次触发频率周期时刻，执行至FinalTime的剩余周期次数
									// ——以startTime为基准计算周期频率，并得到FinalTime
									// ——即使中间出现pause，resume以后保持FinalTime时间不变
									//
									//
									// .withMisfireHandlingInstructionNowWithExistingCount()//
									// （默认）
									// ——以当前时间为触发频率立即触发执行
									// ——执行至FinalTIme的剩余周期次数
									// ——以调度或恢复调度的时刻为基准的周期频率，FinalTime根据剩余次数和当前时间计算得到
									// ——调整后的FinalTime会略大于根据starttime计算的到的FinalTime值
									//
									// withMisfireHandlingInstructionNextWithRemainingCount
									// ——不触发立即执行
									// ——等待下次触发频率周期时刻，执行至FinalTime的剩余周期次数
									// ——以startTime为基准计算周期频率，并得到FinalTime
									// ——即使中间出现pause，resume以后保持FinalTime时间不变
									//
									// withMisfireHandlingInstructionNowWithRemainingCount
									// ——以当前时间为触发频率立即触发执行
									// ——执行至FinalTIme的剩余周期次数
									// ——以调度或恢复调度的时刻为基准的周期频率，FinalTime根据剩余次数和当前时间计算得到
									//
									// ——调整后的FinalTime会略大于根据starttime计算的到的FinalTime值
									//
									// MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT
									// ——此指令导致trigger忘记原始设置的starttime和repeat-count
									// ——触发器的repeat-count将被设置为剩余的次数
									// ——这样会导致后面无法获得原始设定的starttime和repeat-count值
									// 默认就是withMisfireHandlingInstructionNowWithExistingCount
									// 如果是SimpleTrigger为直接运行。
									.withMisfireHandlingInstructionNowWithExistingCount())
					.build();

			Date ft2 = scheduler.scheduleJob(job2, trigger2);

			logger.info(job2.getKey() + " 运行时间: " + ft2 + " 。运行次数: "
					+ trigger2.getRepeatCount() + " 。运行间隔： "
					+ trigger2.getRepeatInterval() / 1000 + " 秒");

			JobDetail job3 = JobBuilder.newJob(MisFireJob.class)
					.withIdentity("Job3", "Group1").build();

			CronTrigger trigger3 = TriggerBuilder
					.newTrigger()
					.withIdentity("Trigger3", "Group1")
					.startAt(start)
					.withSchedule(
							CronScheduleBuilder.cronSchedule("0/3 * * * * ?")
							// ——以错过的第一个频率时间立刻开始执行
							// ——重做错过的所有频率周期后
							// ——当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
							// .withMisfireHandlingInstructionIgnoreMisfires()
							// ——以当前时间为触发频率立刻触发一次执行
							// ——然后按照Cron频率依次执行
							// 默认是这个
							// .withMisfireHandlingInstructionFireAndProceed()
							// 直接执行！
							// 等到下次频率执行
									.withMisfireHandlingInstructionDoNothing())
					.build();
			Date ft3 = scheduler.scheduleJob(job3, trigger3);

			logger.info(job3.getKey() + " 运行时间: " + ft3 + " 。Cron周期: "
					+ trigger3.getCronExpression());

			scheduler.start();
			TimeUnit.MINUTES.sleep(3);
			scheduler.shutdown(true);
			SchedulerMetaData metaData = scheduler.getMetaData();
			logger.info("执行Job次数：" + metaData.getNumberOfJobsExecuted());
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
