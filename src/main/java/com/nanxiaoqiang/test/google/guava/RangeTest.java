package com.nanxiaoqiang.test.google.guava;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import com.google.common.primitives.Ints;

/**
 * <pre>
 * (a..b) 	open(C, C)
 * [a..b] 	closed(C, C)
 * [a..b) 	closedOpen(C, C)
 * (a..b] 	openClosed(C, C)
 * (a..+∞) 	greaterThan(C)
 * [a..+∞) 	atLeast(C)
 * (-∞..b) 	lessThan(C)
 * (-∞..b] 	atMost(C)
 * (-∞..+∞) 	all()
 * </pre>
 * 
 * @ClassName: RangeTest
 * @Description: 边界判定
 * @author nanxiaoqiang nanxiaoqiang_gmail_com
 * @date 2015年7月26日 下午4:18:01
 *
 */
public class RangeTest {

	public RangeTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		System.out.println("Range.closed(1, 3).contains(2):"
				+ Range.closed(1, 3).contains(2));
		System.out.println("Range.closed(1, 3).contains(4):"
				+ Range.closed(1, 3).contains(4));
		System.out.println("Range.lessThan(5).contains(5):"
				+ Range.lessThan(5).contains(5));
		System.out
				.println("Range.closed(1, 4).containsAll(Ints.asList(1, 2, 3)):"
						+ Range.closed(1, 4).containsAll(Ints.asList(1, 2, 3)));
		Range.range(4, BoundType.OPEN, 10, BoundType.CLOSED);
		// for (int i = 0; i < 100; i++) {
		//
		// }
	}

}
