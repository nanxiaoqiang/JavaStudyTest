package com.nanxiaoqiang.test.javastudytest.lang.number;

/**
 * 哭了！为啥是输出是2212，真得看javap后的JVM编译代码！？？？
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年1月22日
 *
 */
public class IntPlusPlusTest {

	public IntPlusPlusTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		int a = 1, b = 1, c = 1, d = 1;
		a++;
		++b;
		c = c++;
		d = ++d;// Eclipse提示这句话没有意义.
		System.out.println("a:" + a + "\tb:" + b + "\tc:" + c + "\td:" + d);// 输出2212
		System.out.println(new StringBuffer("a:").append(a).append("\tb:")
				.append(b).append("\tc:").append(c).append("\td:").append(d));// 输出2212
	}

}
