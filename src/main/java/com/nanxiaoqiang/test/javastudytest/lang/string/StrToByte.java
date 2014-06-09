package com.nanxiaoqiang.test.javastudytest.lang.string;

import javax.xml.bind.DatatypeConverter;

/**
 * String到Byte的转换
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月9日
 * 
 */
public class StrToByte {

	public StrToByte() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "0x4F,0x4E,0x03";
		System.out.println(s.length() + "|" + s);
		System.out.println(0x4F);
		Byte.valueOf(s.split(",")[0]);
		System.out.println(DatatypeConverter.parseHexBinary(s.split(",")[0]));
		System.out.println(Byte.valueOf("0x4F"));
	}

}
