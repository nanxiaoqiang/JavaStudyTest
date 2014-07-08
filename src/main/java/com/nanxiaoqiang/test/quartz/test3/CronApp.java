package com.nanxiaoqiang.test.quartz.test3;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * CronTrigger<br/>
 * 原来一直都是通过程序生成trigger，现在使用Cron。就是最早接触Quartz的*,*,1之类的配置<br/>
 * 
 * <pre>
 * 解析：每个cron都有7个，分别对应：秒 分钟 小时 日 月 年 周
 * 通配符：
 * 【*】星号表示所有值
 *   ?  问号表示此值忽略:如每月10号触发一个任务，但是无所谓是周几，那么设置为:"* * * 10 * * ?"
 *   -  表示区间，如"0 0 1 11-22 * * ?"表示11~22号这12天每天早上1点触发任务
 *   ,  表示多个值，如"0,12,44 * * * * * ?"表示但凡是0,12,44秒的时候执行。
 *   /  表示递增触发，如"5/22 * * * * * ?"表示从5秒后每隔22秒触发。"0 0 1 1/3 * * ?"表示从每月1号开始，每隔3天的凌晨1点执行
 *   L  表示最后，放到日表示本月最后一天（会自动推断闰年），放到周表示周六！周上位6L表示本月最后一个周六
 *   W  表示离指定日期最后最近的一个，在日上设置，如1W，表示每月1号后最近的工作日，如果1号就是工作日，那么就是1号，，如果1号是周六日，那么就是下个周一。
 *   LW 在日段上，表示本月最后一个工作日
 *   #  周上，6#2表示每月的第二个周五
 * 需要注意的！
 * 时分秒都是从0开始的:0~59,0~59,0~23
 * 月是1~12，周是1~7，要注意1是周日，7是周六！！！
 * 例子：
 * 0 11 11 11 11 ? 每年11月11日11点11分-光棍节
 * </pre>
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2014年7月8日
 * 
 */
public class CronApp {

	private static Logger logger = LogManager
			.getLogger(CronApp.class.getName());

	public CronApp() {
		// TODO Auto-generated constructor stub
	}

	public void run() {
		try {
			logger.info("初始化");
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// 就弄一个Job暂时先
			JobDetail job = JobBuilder.newJob(SimpleJob.class)
					.withIdentity("Job1", "Gourp1").build();

			// CronTrigger
			// 每个20秒开始运行一次
			CronTrigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity("CronTrigger1", "Group1")
					.withSchedule(
							CronScheduleBuilder.cronSchedule("0/20 * * * * ?"))
					.build();
			Date ft = scheduler.scheduleJob(job, trigger);

			logger.info(job.getKey() + " 运行时间: " + ft + " 。运行设置Cron为: "
					+ trigger.getCronExpression());

			// Job2
			JobDetail job2 = JobBuilder.newJob(SimpleJob.class)
					.withIdentity("Job2", "Gourp1").build();

			// CronTrigger
			// 15秒后，每1分钟运行一次
			CronTrigger trigger2 = TriggerBuilder
					.newTrigger()
					.withIdentity("CronTrigger2", "Group1")
					.withSchedule(
							CronScheduleBuilder.cronSchedule("15 0/1 * * * ?"))
					.build();
			Date ft2 = scheduler.scheduleJob(job2, trigger2);

			logger.info(job2.getKey() + " 运行时间: " + ft2 + " 。运行设置Cron为: "
					+ trigger2.getCronExpression());

			// Job3
			JobDetail job3 = JobBuilder.newJob(SimpleJob.class)
					.withIdentity("Job3", "Gourp1").build();

			// CronTrigger
			// 立即执行，早上9到15点，每隔1分钟执行一次。
			CronTrigger trigger3 = TriggerBuilder
					.newTrigger()
					.withIdentity("CronTrigger3", "Group1")
					.withSchedule(
							CronScheduleBuilder
									.cronSchedule("0 0/1 9-15 * * ?")).build();
			Date ft3 = scheduler.scheduleJob(job3, trigger3);

			logger.info(job3.getKey() + " 运行时间: " + ft3 + " 。运行设置Cron为: "
					+ trigger3.getCronExpression());
			// Job4
			JobDetail job4 = JobBuilder.newJob(SimpleJob.class)
					.withIdentity("Job4", "Gourp1").build();

			// CronTrigger
			// 立即执行，每个月的8,9,12日的下午14点15分执行。
			CronTrigger trigger4 = TriggerBuilder
					.newTrigger()
					.withIdentity("CronTrigger4", "Group1")
					.withSchedule(
							CronScheduleBuilder
									.cronSchedule("0 17 14am 8,9,12 * ?"))
					.build();
			Date ft4 = scheduler.scheduleJob(job4, trigger4);

			logger.info(job4.getKey() + " 运行时间: " + ft4 + " 。运行设置Cron为: "
					+ trigger4.getCronExpression());

			// Job5
			JobDetail job5 = JobBuilder.newJob(SimpleJob.class)
					.withIdentity("Job5", "Gourp1").build();

			// CronTrigger
			// 立即执行，每个工作日周一到周五的每一分钟的0,14,41秒执行。
			CronTrigger trigger5 = TriggerBuilder
					.newTrigger()
					.withIdentity("CronTrigger5", "Group1")
					.withSchedule(
							CronScheduleBuilder
									.cronSchedule("0,14,41 * * ? * MON-FRI"))
					.build();
			Date ft5 = scheduler.scheduleJob(job5, trigger5);

			logger.info(job5.getKey() + " 运行时间: " + ft5 + " 。运行设置Cron为: "
					+ trigger5.getCronExpression());

			scheduler.start();

			// 5分钟的等待输出
			TimeUnit.MINUTES.sleep(5);

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
		CronApp app = new CronApp();
		app.run();
	}

}
