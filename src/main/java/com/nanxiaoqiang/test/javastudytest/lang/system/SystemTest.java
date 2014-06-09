package com.nanxiaoqiang.test.javastudytest.lang.system;

import java.lang.management.ManagementFactory;

/**
 * 得到当前系统的pid
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月9日
 * 
 */
public class SystemTest {

	public SystemTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		String name = ManagementFactory.getRuntimeMXBean().getName();
		String mem = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage()
				.getUsed()
				+ "";
		System.out.println(name);
		// get pid
		String pid = name.split("@")[0];
		System.out.println("Pid is:" + pid + "  |mem is " + mem);

		for (int i = 0; i < 20; i++) {
			Thread.sleep(10000);
		}
		// sleep是为了方便在任务管理器里查看进程的pid
	}

}
