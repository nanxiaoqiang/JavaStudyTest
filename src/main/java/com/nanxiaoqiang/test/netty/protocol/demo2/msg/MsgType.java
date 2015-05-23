package com.nanxiaoqiang.test.netty.protocol.demo2.msg;

public enum MsgType {
	MSG_BEATHEART((byte) 0x01), // 0x01 心跳
	MSG_LINE_STATUS((byte) 0x02);

	private byte type;
	
	private MsgType(byte type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return String.valueOf(this.type);
	}

	
}
