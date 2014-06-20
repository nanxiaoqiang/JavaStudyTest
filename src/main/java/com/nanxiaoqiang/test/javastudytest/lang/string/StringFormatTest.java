package com.nanxiaoqiang.test.javastudytest.lang.string;


/**
 * StringFormat
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月9日
 * 
 */
public class StringFormatTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start1 = System.currentTimeMillis();
		String str = String.format("测试插入的数据%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,",
				"1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		long end1 = System.currentTimeMillis();
		System.out.println(end1 - start1);
		System.out.println(str);
		long start2 = System.currentTimeMillis();
		String str2 = "测试插入的数据";
		for (int i = 0; i < 10; i++) {
			str2 += (i + 1) + ",";
		}
		long end2 = System.currentTimeMillis();
		System.out.println(end2 - start2);
		System.out.println(str2);
		System.out.println(String.format("%03d", 11));
		System.out.println(str.length() + "|"
				+ Integer.toHexString(str.length()));
		byte[] bs = "21900000".getBytes();
		for (byte b : bs) {
			System.out.print(b + ",");
		}
		System.out.print("\r\n");
	}

}
