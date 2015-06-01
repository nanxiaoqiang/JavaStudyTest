package com.nanxiaoqiang.test.javastudytest.util.date;

import java.util.Calendar;
import java.util.Date;

public class DateTest {

	public static void main(String[] args) {
		int timea = DateTest.getCurrentUtcTime();
		long timeb = System.currentTimeMillis();
		int timec = new Long(System.currentTimeMillis()).intValue();
		System.out.println(timea);
		System.out.println((int) (timeb / 1000));
		System.out.println(timec);
	}

	public static int getCurrentUtcTime() {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);

		cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));

		Calendar cc = Calendar.getInstance();
		cc.set(1970, 0, 1, 0, 0, 1);

		int secNum = (int) ((new Date(cal.getTimeInMillis()).getTime() - new Date(
				cc.getTimeInMillis()).getTime()) / 1000);

		return secNum;
	}
}
