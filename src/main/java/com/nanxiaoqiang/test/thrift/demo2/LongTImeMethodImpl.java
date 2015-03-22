package com.nanxiaoqiang.test.thrift.demo2;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;

import com.google.common.collect.Lists;

public class LongTImeMethodImpl implements LongTimeMethod.Iface {

	private static Logger logger = LogManager
			.getLogger(LongTImeMethodImpl.class.getName());

	public LongTImeMethodImpl() {
		logger.info("LongTImeMethodImpl init...");
	}

	@Override
	public void noResult() throws TException {
		LocalTime starttime = LocalTime.now();
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		LocalTime endtime = LocalTime.now();
		long between = ChronoUnit.SECONDS.between(starttime, endtime);
		logger.info("noResult:从" + starttime + "到" + endtime + "总共执行了"
				+ between + "秒。");
	}

	@Override
	public void showObj(Tweet tweet) throws TException {
		LocalTime starttime = LocalTime.now();
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		LocalTime endtime = LocalTime.now();
		long between = ChronoUnit.SECONDS.between(starttime, endtime);
		logger.info(ToStringBuilder.reflectionToString(tweet,
				ToStringStyle.MULTI_LINE_STYLE));
		logger.info("showObj:从" + starttime + "到" + endtime + "总共执行了"
				+ between + "秒。");
	}

	@Override
	public List<Tweet> getTimeLine(long time) throws TException {
		LocalTime starttime = LocalTime.now();
		logger.info("getTimeLine:" + time + "|" + new Date(time) + "执行时间是："
				+ starttime + ",");
		List<Tweet> list = Lists.newArrayList();
		Tweet t1 = new Tweet(1, "hello world!", System.currentTimeMillis());
		Tweet t2 = new Tweet(2, "@nanxiaoqiang Hi~", System.currentTimeMillis());
		t2.setTweetType(TweetType.REPLY);
		Location loc = new Location(116.344489, 39.961907);
		t2.setLoc(loc);
		list.add(t1);
		list.add(t2);
		return list;
	}
}
