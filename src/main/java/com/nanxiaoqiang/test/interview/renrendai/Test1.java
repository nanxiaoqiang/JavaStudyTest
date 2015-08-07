package com.nanxiaoqiang.test.interview.renrendai;

import java.util.List;
import java.util.Map;

import org.apache.storm.guava.collect.Lists;

import com.google.common.collect.Maps;

/**
 * 人人贷的面试，人人贷的新部门，类似于Saas的云呼叫中心。
 * 
 * @author nanxiaoqiang
 * 
 * @version 2015年8月7日
 */
public class Test1 {

	Map<String, Integer> map = Maps.newHashMap();

	public Test1() {
		map.put("A", 3);
		map.put("B", 2);
		map.put("C", 5);
		map.put("D", 1);
	}

	public static void main(String[] args) {
		// 要求：一个方法，类似于俄罗斯轮盘，随机一个结果出来，要求权重是map的key的value

	}

	/**
	 * 根据随机数字按照权重输出结果（缺点，ifelse写的太麻烦）
	 * 
	 * @param value
	 */
	public void test1(int value) {
		if (0 < value && value <= 3) {
			System.out.println("A");
		} else if (3 < value && value <= (3 + 2)) {
			System.out.println("B");
		} else if ((3 + 2) < value && value <= (3 + 2 + 5)) {
			System.out.println("C");
		} else if ((3 + 2 + 5) < value && value <= (3 + 2 + 5 + 1)) {
			System.out.println("D");
		}
	}

	/**
	 * 提前准备好一个数组或者list，长度为所有权重之和，然后随机数是几，直接取顺序位置输出值即可。缺点，需要提前计算，然后占空间和初始化时间。
	 * 
	 * @param value
	 */
	public void test2(int value) {
		// 下边的是要提前生成的
		List<String> list = Lists.newArrayList();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			for (int i = 0; i < entry.getValue(); i++) {
				list.add(entry.getKey());
			}
		}
		// 上边是提前生成的

		// 方法的正式内容
		System.out.println(list.get(value));
	}

	/**
	 * 可以无视Map里到底有多少个，但是如果随机数的相对位置在最后变那么就GameOver等死了
	 * 
	 * @param value
	 */
	public void test3(int value) {
		int b = 0;
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			b += entry.getValue();
			if (value <= b) {
				System.out.println(entry.getKey());
			}
		}
	}

	public void test4(int value) {
		// 需要提前计算
		// 新建一个数组，权重改为之前权重之和，如map的权重数组是[3, 5, 10, 11]
		// 然后随机数做二分查找
		// 找到之后再取值返回。
	}
}
