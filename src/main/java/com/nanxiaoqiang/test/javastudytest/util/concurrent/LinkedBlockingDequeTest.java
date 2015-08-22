package com.nanxiaoqiang.test.javastudytest.util.concurrent;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 
 * @ClassName: LinkedBlockingDequeTest
 * @Description: 双向线程安全的堆栈测试
 * @author nanxiaoqiang nanxiaoqiang_gmail_com
 * @date 2015年8月22日 下午4:56:20
 *
 */
public class LinkedBlockingDequeTest {

	public static void main(String[] args) throws InterruptedException {
		LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(20);
		for (int i = 0; i < 5; i++) {
			deque.push(i);
			System.out.println("向deque中放入了:" + i);
		}
		for (int i = 0; i < 5; i++) {
			int value = deque.poll();
			System.out.println("从deque中取出了：" + value);
		}
		for (int i = 0; i < 30; i++) {
			// deque.push(i);
			// 放满之后会报异常
			// java.lang.IllegalStateException: Deque full
			deque.putFirst(i);// 放满之后会暂停，直到队列再度有空间为止
			System.out.println("向deque中放入了:" + i);
		}
		System.out.println("全部放入");
	}
}
