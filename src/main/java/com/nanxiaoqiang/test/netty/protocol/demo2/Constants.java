package com.nanxiaoqiang.test.netty.protocol.demo2;

public class Constants {
	/********* msg ***********/

	public static final byte SYSTEMID = (byte) 0xFF;// 消息头

	public static final short CURRENT_VERSION = (short) 1;// 当前版本号

	public static final byte IS_MULTIPLE = (byte) 0x01;// 多包

	public static final byte IS_NOT_MULTIPLE = (byte) 0x00;// 单包

	private Constants() {

	}
}
