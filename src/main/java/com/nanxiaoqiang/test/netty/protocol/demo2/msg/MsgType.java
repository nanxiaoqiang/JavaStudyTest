package com.nanxiaoqiang.test.netty.protocol.demo2.msg;

public enum MsgType {
	MSG_BEATHEART((short) 0x01), // 0x01 心跳
	MSG_NET_STATUS((short) 0x02), // 0x02信息源网络状态
	MSG_REQ_STATUS((short) 0x03), // 0x03ATS请求信息
	MSG_ALL_EQP_STATUS((short) 0x04), // 0x04设备状态全体信息
	MSG_EQP_STATUS_UPDATE((short) 0x05); // 0x05设备状态变化信息

	private short type;
	
	private MsgType(short type) {
		this.type = type;
	}

	public short getType() {
		return type;
	}

	@Override
	public String toString() {
		return String.valueOf(this.type);
	}

	
}
