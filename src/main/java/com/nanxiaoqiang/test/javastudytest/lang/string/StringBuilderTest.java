package com.nanxiaoqiang.test.javastudytest.lang.string;

/**
 * StringBuilder
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月9日
 * 
 */
public class StringBuilderTest {

	public StringBuilderTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("");
		sb.append("123");
		sb.append("456");
		System.out.println(sb);
		// 去掉最后一个字符
		sb.deleteCharAt(sb.length() - 1);
		System.out.println(sb);

		// 在前边插入
		StringBuilder sb2 = new StringBuilder(16);
		sb2.append("xxyy5678");
		sb2.insert(0, 1);
		sb2.insert(0, 2);
		sb2.insert(0, 3);
		sb2.insert(0, 4);
		sb2.insert(0, 5);
		sb2.insert(0, 6);
		sb2.insert(0, 78);
		System.out.println(sb2.toString());

		System.out.println(System.currentTimeMillis());
		
		// 删除不能-1，要用delete(0, sb.length())
		sb2.delete(0, sb2.length()-1);
		System.out.println(sb2.toString());
		
	}

}
