package com.nanxiaoqiang.test.joda.time;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

/**
 * Joda-Time的测试
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月9日
 * 
 */
public class JodaTimeTest {

	public JodaTimeTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		DateTime now = DateTime.now();
		System.out.println(now.toString("yyyy-MM-dd HH:mm:ss"));
		Thread.sleep(1898);
		DateTime now2 = DateTime.now();
		// 得到两个时间的差值，得出结论时间差值只取整，不四舍五入。
		System.out.println(Seconds.secondsBetween(now2, now).getSeconds());
		DateTime zero = new DateTime(now.getYear(), now.getMonthOfYear(),
				now.getDayOfMonth(), 0, 0, 0, 0);
		System.out.println(zero.toString("yyyy-MM-dd HH:mm:ss"));

		DateTime startTime = new DateTime(2014, 1, 2, 13, 22, 41, 0);
		DateTime finishTime = new DateTime(2014, 1, 2, 15, 21, 53, 0);
		System.out.println(startTime.toString("yyyy-MM-dd HH:mm:ss"));
		System.out.println(finishTime.toString("yyyy-MM-dd HH:mm:ss"));
		// 输出：1小时119分钟7152秒
		// 输出的时间是去掉小数点后位数的！
		System.out.println(Hours.hoursBetween(startTime, finishTime).getHours()
				+ "小时\t"
				+ Minutes.minutesBetween(startTime, finishTime).getMinutes()
				+ "分钟\t"
				+ Seconds.secondsBetween(startTime, finishTime).getSeconds()
				+ "秒");
	}

}
