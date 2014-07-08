package com.nanxiaoqiang.test.quartz.test1;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {

	private static Logger logger = LogManager.getLogger(QuartzTest.class
			.getName());

	public QuartzTest() {
		logger.debug("QuartzTest");
	}

	public static void main(String[] args) {
		QuartzTest a = new QuartzTest();
		a.run();
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			// DateTime d = DateTime.now();

			// and start it off
			scheduler.start();
			// define the job and tie it to our HelloJob class
			JobDetail job = newJob(HelloJob.class).withIdentity("myJob",
					"group1").build();

			// Trigger the job to run now, and then every 40 seconds
			Trigger trigger = newTrigger()
					.withIdentity("myTrigger", "group1")
					.startNow()
					.withSchedule(
							simpleSchedule().withIntervalInSeconds(5)
									.repeatForever()).build();

			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);
			TimeUnit.SECONDS.sleep(300);
			scheduler.shutdown();

		} catch (SchedulerException se) {
			se.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
