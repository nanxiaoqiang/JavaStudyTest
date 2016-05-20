package com.nanxiaoqiang.test.javastudytest.lang.base;

import java.util.concurrent.TimeUnit;

/**
 * 程序关闭时的附加程序
 * 
 * @author nanxiaoqiang
 *
 */
public class TestShutDwonMethod {

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				// ------- stop method
				System.out.println("this is sleep method, and I 'll stop");
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Sleep 5 sec.");
			}
		});
		for (int i = 0; i < 5; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(String.format("Process:sleep[%s] 1 sec in 5 times.", i));
		}
	}

}
