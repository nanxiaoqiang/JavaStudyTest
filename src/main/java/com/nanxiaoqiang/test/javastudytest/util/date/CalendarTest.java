package com.nanxiaoqiang.test.javastudytest.util.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

/**
 * Calendar
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月9日
 * 
 */
public class CalendarTest {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance();
		System.out.println(c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH));// 不能输出
		Date d = new Date();
		d.getTime();
		DateTime dt = new DateTime();
		System.out.println(dt);
		System.out.println(System.currentTimeMillis());
		// Long timea = System.currentTimeMillis();
		System.out.println("***************");
		long time = System.currentTimeMillis();
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long time2 = System.currentTimeMillis();
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(time);
		Date d2 = new Date(time2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		System.out.println(sdf.format(c1.getTime()));
		System.out.println(sdf.format(d2));
		System.out.println(c1.get(Calendar.YEAR) + "-" + c1.get(Calendar.MONTH)// 只有月份是从0开始的
				+ "-" + c1.get(Calendar.DATE));
	}
}
