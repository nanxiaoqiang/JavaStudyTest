package com.nanxiaoqiang.test.joda.time;

import org.joda.time.DateTime;
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
	}

}
