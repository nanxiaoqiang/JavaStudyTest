package com.nanxiaoqiang.test.quartz.test2;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Quartz学习（二）：简单的Job和Trigger的应用！！！
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2014年7月8日
 * 
 */
public class App {

	private static Logger logger = LogManager.getLogger(App.class.getName());

	public App() {
		// TODO Auto-generated constructor stub
	}

	public void run() {
		try {
			logger.info("初始化...");
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			Date startTime = DateBuilder.nextGivenSecondDate(null, 10);

			logger.info("初始化执行时间..." + startTime.toString());

			// Job1只运行一次
			logger.info("初始化Job...");
			JobDetail job = JobBuilder.newJob(SimpleJob.class)
					.withIdentity("job1", "group1").build();

			logger.info("初始化Trigger...");
			SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
					.withIdentity("trigger1", "group1").startAt(startTime)
					.build();
			Date ft = scheduler.scheduleJob(job, trigger);
			logger.info(job.getKey() + " 运行时间: " + ft + " 。运行次数: "
					+ trigger.getRepeatCount() + " 。运行间隔： "
					+ trigger.getRepeatInterval() / 1000 + " 秒");

			// Job2只运行一次
			logger.info("初始化Job2...");
			JobDetail job2 = JobBuilder.newJob(SimpleJob.class)
					.withIdentity("job2", "group1").build();

			logger.info("初始化Trigger2...");
			SimpleTrigger trigger2 = (SimpleTrigger) TriggerBuilder
					.newTrigger().withIdentity("trigger2", "group1")
					// .startAt(startTime).withSchedule(SimpleScheduleBuilder.simpleSchedule().with)
					.build();
			Date ft2 = scheduler.scheduleJob(job2, trigger2);

			logger.info(job2.getKey() + " 运行时间: " + ft2 + " 。运行次数: "
					+ trigger2.getRepeatCount() + " 。运行间隔： "
					+ trigger2.getRepeatInterval() / 1000 + " 秒");

			// Job3运行11次，每次间隔15秒
			// withIntervalInSeconds中的计算时间是按照每次trigger的开始时间计算的，不是trigger完成到现在的间隔
			// withRepeatCount是再运行次数！不算第一次运行！！！
			logger.info("初始化Job3...");
			JobDetail job3 = JobBuilder.newJob(SimpleJob.class)
					.withIdentity("job3", "group1").build();

			logger.info("初始化Trigger3...");
			SimpleTrigger trigger3 = (SimpleTrigger) TriggerBuilder
					.newTrigger()
					.withIdentity("trigger3", "group1")
					.startAt(startTime)
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withIntervalInSeconds(15)
									.withRepeatCount(10)).build();
			Date ft3 = scheduler.scheduleJob(job3, trigger3);

			logger.info(job3.getKey() + " 运行时间: " + ft3 + " 。运行次数: "
					+ trigger3.getRepeatCount() + " 。运行间隔： "
					+ trigger3.getRepeatInterval() / 1000 + " 秒");
			// Job4 使用的是Job3的内容，运行3次，每次2秒
			// logger.info("初始化Job4...");
			// JobDetail job4 = JobBuilder.newJob(SimpleJob.class)
			// .withIdentity("job4", "group1").build();
			logger.info("初始化Trigger4...");
			SimpleTrigger trigger4 = (SimpleTrigger) TriggerBuilder
					.newTrigger()
					.withIdentity("trigger4", "group1")
					.startAt(startTime)
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withIntervalInSeconds(2)
									.withRepeatCount(2)).forJob(job3).build();
			Date ft4 = scheduler.scheduleJob(trigger4);

			logger.info(job3.getKey() + " 运行时间: " + ft4 + " 。运行次数: "
					+ trigger4.getRepeatCount() + " 。运行间隔： "
					+ trigger4.getRepeatInterval() / 1000 + " 秒");

			// Job5只运行一次，但是运行时间是一分钟之后
			logger.info("初始化Job5...");
			JobDetail job5 = JobBuilder.newJob(SimpleJob.class)
					.withIdentity("job5", "group1").build();
			logger.info("初始化Trigger5...");
			SimpleTrigger trigger5 = (SimpleTrigger) TriggerBuilder
					.newTrigger().withIdentity("trigger5", "group1")
					.startAt(DateBuilder.futureDate(1, IntervalUnit.MINUTE))
					.build();
			Date ft5 = scheduler.scheduleJob(job5, trigger5);

			logger.info(job5.getKey() + " 运行时间: " + ft5 + " 。运行次数: "
					+ trigger5.getRepeatCount() + " 。运行间隔： "
					+ trigger5.getRepeatInterval() / 1000 + " 秒");

			// Job6,每隔20秒运行一次，一直运行到死
			logger.info("初始化Job6...");
			JobDetail job6 = JobBuilder.newJob(SimpleJob.class)
					.withIdentity("job6", "group1").build();
			logger.info("初始化Trigger6...");
			SimpleTrigger trigger6 = (SimpleTrigger) TriggerBuilder
					.newTrigger()
					.withIdentity("trigger6", "group1")
					.startAt(startTime)
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withIntervalInSeconds(20).repeatForever())
					.build();
			Date ft6 = scheduler.scheduleJob(job6, trigger6);

			logger.info(job6.getKey() + " 运行时间: " + ft6 + " 。运行次数: "
					+ trigger6.getRepeatCount() + " 。运行间隔： "
					+ trigger6.getRepeatInterval() / 1000 + " 秒");

			logger.info("Jobs开始执行！");
			scheduler.start();
			// 先休息20秒
			TimeUnit.SECONDS.sleep(20);

			// 启动之后也可以加入Job
			// Job7,每隔5秒运行一次，一直运行到死
			logger.info("初始化Job6...");
			JobDetail job7 = JobBuilder.newJob(SimpleJob.class)
					.withIdentity("job7", "group1").build();
			logger.info("初始化Trigger7...");
			SimpleTrigger trigger7 = (SimpleTrigger) TriggerBuilder
					.newTrigger()
					.withIdentity("trigger7", "group1")
					.startAt(startTime)
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withIntervalInSeconds(5).repeatForever())
					.build();
			Date ft7 = scheduler.scheduleJob(job7, trigger7);

			logger.info(job7.getKey() + " 运行时间: " + ft7 + " 。运行次数: "
					+ trigger7.getRepeatCount() + " 。运行间隔： "
					+ trigger7.getRepeatInterval() / 1000 + " 秒");

			// Job8，创建一个Job加入到scheduler中。暂不调用
			logger.info("初始化Job8...");
			// 采用storeDurably()，不调用的Job仍然可以存储在scheduler中。
			JobDetail job8 = JobBuilder.newJob(SimpleJob.class)
					.withIdentity("job8", "group1").storeDurably().build();
			// 后一个boolean值指的是如果有相同的Job则替换(replace)
			scheduler.addJob(job8, true);
			// 再休息20秒
			TimeUnit.SECONDS.sleep(20);

			logger.info("手动执行Job:job8:group1");
			// 手动的执行Trigger
			scheduler.triggerJob(JobKey.jobKey("job8", "group1"));

			logger.info("再次执行trigger4！也就是Job3：" + trigger4.getKey());
			// 测试，再一次执行以下job1
			scheduler.rescheduleJob(trigger4.getKey(), trigger4);

			TimeUnit.MINUTES.sleep(5);
			scheduler.shutdown(true);
			SchedulerMetaData metaData = scheduler.getMetaData();
			logger.info("总共执行了 " + metaData.getNumberOfJobsExecuted()
					+ " 次job!");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		App app = new App();
		app.run();
	}

}
