package com.nanxiaoqiang.test.javastudytest.time;

import java.time.LocalDateTime;

/**
 * JDK8的新时间处理java.time包
 * 
 * @author nanxiaoqiang
 * 
 * @version 2016年6月25日
 */
public class DateTimeTest {

	public DateTimeTest() {
	}

	public static void main(String[] args) {
		LocalDateTime d1 = LocalDateTime.now();
		System.out.println(d1);// 2016-06-25T15:07:27.627
		System.out.println(d1.toLocalDate());// yyyy-MM-dd
		System.out.println(d1.toLocalTime());// hh24:mm:ss.mills
		// JUNE   6(1~12)
		System.out.println(String.format("getMonth[%s]getMonthValue[%s]", d1.getMonth(), d1.getMonthValue()));
		
	}

}
