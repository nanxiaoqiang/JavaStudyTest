package com.nanxiaoqiang.test.netty.protocol.demo2.msg;

public enum MsgType {
	MSG_BEATHEART((byte) 0x01), // 0x01 心跳
	MSG_NET_STATUS((byte) 0x02), // 0x02信息源网络状态
	MSG_REQ_STATUS((byte) 0x03), // 0x03ATS请求信息
	MSG_ALL_EQP_STATUS((byte) 0x04), // 0x04设备状态全体信息
	MSG_EQP_STATUS_UPDATE((byte) 0x05); // 0x05设备状态变化信息

	private byte type;
	
	private MsgType(byte type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return String.valueOf(this.type);
	}

	
}
