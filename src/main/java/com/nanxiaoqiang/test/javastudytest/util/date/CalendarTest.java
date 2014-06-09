package com.nanxiaoqiang.test.javastudytest.util.date;

import java.util.Calendar;
import java.util.Date;

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
	}
}
