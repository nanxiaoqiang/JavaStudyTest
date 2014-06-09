package com.nanxiaoqiang.test.joda.time;

import org.joda.time.DateTime;

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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DateTime now = new DateTime();
		System.out.println(now.toString("yyyyMM"));
	}

}
