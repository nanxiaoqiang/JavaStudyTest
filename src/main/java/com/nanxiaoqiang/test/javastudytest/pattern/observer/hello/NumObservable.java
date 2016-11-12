package com.nanxiaoqiang.test.javastudytest.pattern.observer.hello;

import java.util.Observable;

/**
 * 观察者模式<br/>
 * 非线程安全，可以考虑num为AtomicInteger，注册和去除Observer时用synchronized或者ReentrantLock ?
 * 
 * @author nanxiaoqiang
 * 
 * @version 2016年11月12日
 */
public class NumObservable extends Observable{
	
	private int num = 0;
	
	public static final Integer ODD = 1;// 奇数
	public static final Integer EVEN = 2;// 偶数
	
	private Boolean isODD = true;

	public void setNum(int num) {
		this.num = num;
		if ((num & 0x1) == 1) {
			isODD = true;
		} else {
			isODD = false;
		}
		setChanged();
		notifyObservers(isODD);
	}

	public int getNum() {
		return num;
	}

	public NumObservable() {
	}
	
	public static void main(String[] args) {
		NumObservable numObservable = new NumObservable();
		numObservable.addObserver(new NumOddObserver());
		numObservable.addObserver(new NumEvenObserver());
		numObservable.setNum(1);
		numObservable.setNum(2);
		numObservable.setNum(3);
		numObservable.setNum(3);
	}

}
