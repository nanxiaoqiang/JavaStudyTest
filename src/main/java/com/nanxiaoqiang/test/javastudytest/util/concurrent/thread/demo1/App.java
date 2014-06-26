package com.nanxiaoqiang.test.javastudytest.util.concurrent.thread.demo1;

/**
 * 最简单的一个例子，创建一个线程。<br/>
 * 看《Java7 Concurrency Cookbook》敲的代码
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月26日
 */
public class App {

	public App() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			Calculator c = new Calculator(2);
			Thread thread = new Thread(c);
			thread.start();
		}
	}

}
