package com.nanxiaoqiang.test.quartz.test4;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;

/**
 * 新的添加的注释需要注意！
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2014年7月8日
 * 
 */
// 异常之后持久化
@PersistJobDataAfterExecution
// 不允许并发执行
@DisallowConcurrentExecution
public class ParamJob implements Job {
	private static Logger logger = LogManager.getLogger(ParamJob.class
			.getName());
	public static final String COLOR = "color";
	public static final String NUM = "NUM";

	private int num = 0;

	public ParamJob() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		JobDataMap data = context.getJobDetail().getJobDataMap();
		// 如果类型不匹配，如不是String会抛出ClassCastException
		String color = data.getString(COLOR);
		num = data.getInt(NUM);// .getIntFromString(NUM);
		num++;
		DateTime start = DateTime.now();
		JobKey jobKey = context.getJobDetail().getKey();
		int sleep = RandomUtils.nextInt(1, 5);
		try {

			// 耗时?秒
			TimeUnit.SECONDS.sleep(sleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		data.put(NUM, num);
		logger.info("执行任务:" + jobKey + " | 开始执行时间:"
				+ start.toString("yyyy-MM-dd HH:mm:ss") + " |输出时间"
				+ DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + " |color:"
				+ color + " |num:" + num + " |sleep:" + sleep);

	}

}
