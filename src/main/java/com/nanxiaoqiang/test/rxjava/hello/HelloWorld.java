package com.nanxiaoqiang.test.rxjava.hello;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

public class HelloWorld {

	public HelloWorld() {
	}

	public static void main(String[] args) {
		Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> sub) {
				sub.onNext("Hello, world!");
				sub.onNext("Hello, world!_A");
				sub.onNext("Hello, world!_B");
				sub.onNext("Hello, world!_C");
				sub.onCompleted();
			}
		});

		Subscriber<String> mySubscriber = new Subscriber<String>() {
			@Override
			public void onNext(String s) {
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("onNext:" + s);
			}

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("onError:" + e.getMessage());
			}
		};

		myObservable.subscribe(mySubscriber);
		System.out.println("WAKAWAKA");
	}
}
