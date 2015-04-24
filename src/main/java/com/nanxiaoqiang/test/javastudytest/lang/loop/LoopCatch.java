package com.nanxiaoqiang.test.javastudytest.lang.loop;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nanxiaoqiang.test.javastudytest.util.concurrent.lock.demo1.ConditionTest;

public class LoopCatch {

	private static final Logger LOGGER = LogManager
			.getLogger(ConditionTest.class.getName());

	public LoopCatch() {
	}

	public static void main(String[] args) {
		int i = 0;
		while (true) {
			try {
				i++;
				LOGGER.info("i:" + i);
				if (i == 3) {
					break;
				}
				Integer number = Integer.parseInt("wohaha");
				LOGGER.info(number + '|' + i);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				LOGGER.info("故意出错，沉睡1s");
			} finally {
				try {
					LOGGER.info("Finally，沉睡1s，即便是break也会执行哦！");
					TimeUnit.SECONDS.sleep(1L);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
