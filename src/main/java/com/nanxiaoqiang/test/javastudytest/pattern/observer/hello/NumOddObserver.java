package com.nanxiaoqiang.test.javastudytest.pattern.observer.hello;

import java.util.Observable;
import java.util.Observer;

public class NumOddObserver implements Observer {

	public NumOddObserver() {
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg == Boolean.TRUE) {
			NumObservable numObservable = (NumObservable) o;
			System.out.println(getClass().getName() + ":\tnum变基：" + numObservable.getNum() + "|" + arg);
		}
	}

}
