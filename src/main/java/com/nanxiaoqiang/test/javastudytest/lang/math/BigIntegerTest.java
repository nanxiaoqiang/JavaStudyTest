package com.nanxiaoqiang.test.javastudytest.lang.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigIntegerTest {

	public static void main(String[] args) {
		double d1 = 9.6;
		double d2 = 3.0;
		double d3 = 3.2;
		System.out.println(d1 / d2);
		System.out.println(d1 / d3);
		BigDecimal b1 = new BigDecimal(9.6);
		// java double 本身就不是一个准确值，输出BigDecimal double 9.6就可以看出来（感谢豆瓣 @头头带你飞）
		System.out.println("BigDecimal double 9.6:" + b1.toString());
		BigDecimal b2 = new BigDecimal(3.0);
		BigDecimal b3 = b1.divide(b2, RoundingMode.HALF_UP);
		BigDecimal b4 = b1.divide(b2, 8, RoundingMode.HALF_UP);
		BigDecimal b5 = new BigDecimal("9.6");
		System.out.println("BigDecimal String 9.6:" + b5.toString());
		System.out.println(b3 + "|" + b4);// 直接除还是不对
		System.out.println(new BigDecimal(9.6).divide(new BigDecimal(3.0),
				RoundingMode.HALF_UP));
		System.out.println(b5.divide(b2));// String 转换后的除法正常
	}

}
