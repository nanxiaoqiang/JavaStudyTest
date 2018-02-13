package com.nanxiaoqiang.test.reactivex.demo;

import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.Flux;

public class HelloWord {
	
	private static List<String> words = Arrays.asList(
			"hello",
			"world",
			"nanxiaoqiang",
			"nxq",
			"reactor",
			"core"
			);

	public static void main(String[] args) {
		Flux<String> fewWords = Flux.just("Eclipse", "IDEA");
		Flux<String> manyWords = Flux.fromIterable(words);// 逐条触发
		
		fewWords.subscribe(System.out::println);
		System.out.println("*****************");
		manyWords.subscribe(System.out::println);
		
		printLetter();
	}

	/**
	 * 输出List中的每一个字母，并排序。输出序号
	 */
	private static void printLetter() {
		Flux<String> manyLetter = Flux.fromIterable(words)
				.flatMap(word -> Flux.fromArray(word.split("")))// 按照每个字母分隔
				.distinct()// 去重
				.sort()// 排序
				.zipWith(Flux.range(1, Integer.MAX_VALUE), 
						(string, count) -> String.format("%2d. %s", count, string));
		manyLetter.subscribe(System.out::println);
	}

}
