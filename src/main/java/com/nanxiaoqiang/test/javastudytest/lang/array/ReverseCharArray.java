package com.nanxiaoqiang.test.javastudytest.lang.array;

import org.apache.commons.lang3.RandomUtils;

/**
 * 
 * @ClassName: ReverseCharArray
 * @Description: PPTV的面试题，一个char[]，如何判断在去除空格char之后正反向相同？
 * @author nanxiaoqiang nanxiaoqiang_gmail_com
 * @date 2015年9月1日 下午9:25:58
 *
 */
public class ReverseCharArray {

	/**
	 * 复杂度过高，要3次循环，而且char中有特殊字符转义String会出问题。
	 * 
	 * @param c
	 * @return
	 */
	public static boolean check1(char[] c) {
		StringBuffer sb = new StringBuffer(c.length);
		for (int i = 0; i < c.length; i++) {
			if (c[i] != ' ') {
				sb.append(c[i]);
			}
		}
		return sb.reverse().equals(sb.toString());
	}

	/**
	 * 复杂度0.5
	 * 
	 * @param c
	 * @return
	 */
	public static boolean check2(char[] c) {
		for (int i = 0, j = c.length - 1; i < j;) {
			if (c[i] == ' ') {
				i++;
				continue;
			}
			if (c[j] == ' ') {
				j--;
				continue;
			}
			if (c[i] == c[j]) {
				i++;
				j--;
			} else {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		char[] c = new char[200000000];
		for (int i = 0; i < 200000000; i++) {
			c[i] = ' ';
		}
		for (int j = 0; j < 100; j++) {
			int xx = RandomUtils.nextInt(0 + (j * 1000000),
					1000000 + (j * 1000000));
			c[xx] = (char) (97 + j);
			c[200000000 - xx] = (char) (97 + j);
		}
		long a1 = System.currentTimeMillis();
		boolean ba = check1(c);
		long b1 = System.currentTimeMillis();
		long c1 = System.currentTimeMillis();
		boolean b2 = check2(c);
		long d1 = System.currentTimeMillis();
		System.out.println((b1 - a1) + " | " + (d1 - c1) + " | " + ba + "|"
				+ b2);
	}
}
