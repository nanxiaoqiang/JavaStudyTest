package com.nanxiaoqiang.test.javastudytest.lang.system;

import java.lang.management.ManagementFactory;

/**
 * Properties其实这个类主要在读取配置文件中应用，也可以读取系统变量<br/>
 * 得到当前系统的pid<br/>
 * 查看系统环境变量
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

		System.out.println(System.getProperties());

		System.out.println("PATH=" + System.getProperty("$PATH"));

		for (int i = 0; i < 20; i++) {
			Thread.sleep(10000);
		}
		// sleep是为了方便在任务管理器里查看进程的pid
	}

}
