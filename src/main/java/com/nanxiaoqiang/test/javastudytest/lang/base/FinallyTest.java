package com.nanxiaoqiang.test.javastudytest.lang.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * finally中的return测试：不论Catch块中写了多少返回，但是实际上程序都会执行finally中的内容，catch中的return无用，JDK8
 * 
 * @author nanxiaoqiang
 * 
 * @version 2015年7月27日
 */
public class FinallyTest {

	private static Logger logger = LogManager.getLogger(FinallyTest.class
			.getName());

	public static int getNumByStrStatic(String str) {
		try {
			int num = Integer.parseInt(str);
			// int num not use
			return num;
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			logger.info("NumberFormatException");
			return 0;
		} finally {
			logger.info("getNumByStrStatic finally");
			return -1;
		}
	}

	@SuppressWarnings("finally")
	public int getNumByStr(String str) {
		try {
			int num = Integer.parseInt(str);
			// int num not use
			return num;
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			logger.info("NumberFormatException");
			return 0;
		} finally {
			logger.info("getNumByStr finally");
			return -1;
		}
	}

	public FinallyTest() {
	}

	public static void main(String[] args) {
		FinallyTest t = new FinallyTest();
		logger.info("getNumByStr:" + t.getNumByStr("123a"));
		logger.info("getNumByStrStatic:" + getNumByStrStatic("123a"));
		// 输出：
		// 两种都是一样的
		// NumberFormatException
		// finally
		// －1

	}

}
