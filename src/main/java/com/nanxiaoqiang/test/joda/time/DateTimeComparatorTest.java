package com.nanxiaoqiang.test.joda.time;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Date;

import org.joda.time.DateTimeComparator;

/**
 * 昨天碰到的，之比较年月日的日期怎么办
 * 
 * @author nanxiaoqiang
 * 
 * @version 2016年6月25日
 */
public class DateTimeComparatorTest {

	public DateTimeComparatorTest() {
	}

	public static void main(String[] args) {
		LocalDateTime ldt1 = LocalDateTime.of(2016, Month.JUNE, 25, 15, 34, 43);
		LocalDateTime ldt2 = LocalDateTime.of(2016, Month.JUNE, 25, 7, 21, 55);
		System.out.println(String.format("两个日期:\nd1[%s]\nd2[%s]", ldt1, ldt2));
		System.out.println(String.format("Date.compareTo [%s]", ldt1.compareTo(ldt2)));
		// 转换LocalDateTime >> Instant >> Date
		Date d1 = Date.from(ldt1.toInstant(ZoneOffset.UTC));
		Date d2 = Date.from(ldt2.toInstant(ZoneOffset.UTC));
		System.out.println(String.format("DateTimeComparator只比较年月日: [%s]", DateTimeComparator.getDateOnlyInstance().compare(d1, d2)));
	}

}
