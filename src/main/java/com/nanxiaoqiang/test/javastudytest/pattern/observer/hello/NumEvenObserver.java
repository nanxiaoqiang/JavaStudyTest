package com.nanxiaoqiang.test.javastudytest.pattern.observer.hello;

import java.util.Observable;
import java.util.Observer;

public class NumEvenObserver implements Observer {

	public NumEvenObserver() {
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg == Boolean.FALSE) {
			NumObservable numObservable = (NumObservable) o;
			System.out.println(getClass().getName() + ":\tnum变偶：" + numObservable.getNum() + "|" + arg);
		}
	}

}
