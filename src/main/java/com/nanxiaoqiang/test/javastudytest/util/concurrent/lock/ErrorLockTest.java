package com.nanxiaoqiang.test.javastudytest.util.concurrent.lock;

/**
 * 内置锁不是可重入的，那么代码将死锁。。。不知道咋回事。。。。。这个不懂
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月10日
 * 
 */
public class ErrorLockTest {

	public ErrorLockTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DoOutputThing a = new DoOutputThing();
		a.doSomeThing();
	}

}

class DoSomeThingParent {
	public synchronized void doSomeThing() {
		System.out.println(this.getClass().getName() + "|"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
	}
}

class DoOutputThing extends DoSomeThingParent {

	@Override
	public synchronized void doSomeThing() {
		System.out.println(this.getClass().getName() + "|"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		super.doSomeThing();
	}
}