package com.nanxiaoqiang.test.netty.protocol.demo2.msg;

import java.io.Serializable;

import com.nanxiaoqiang.test.netty.protocol.demo2.Constants;

public class Header implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private byte systemId;// 0xFF 0

	private short length;// 16位Total Length 1~2

	private byte multiFlag;// Multi-flag 3

	private short messageLength; // Message_length 4~5

	private int time;// 32位 6~9

	private short version;// Version 10~11

	private short msgId; // msg_id 12~13

	public Header() {
		super();
	}

	public Header(byte systemId, short length, byte multiFlag,
			short messageLength, int time, short version, short msgId) {
		super();
		this.systemId = systemId;
		this.length = length;
		this.multiFlag = multiFlag;
		this.messageLength = messageLength;
		this.time = time;
		this.version = version;
		this.msgId = msgId;
	}

	public Header(short length, byte multiFlag, short messageLength, int time,
			short version, short msgId) {
		super();
		this.systemId = Constants.SYSTEMID;
		this.length = length;
		this.multiFlag = multiFlag;
		this.messageLength = messageLength;
		this.time = time;
		this.version = version;
		this.msgId = msgId;
	}

	public Header(short length, byte multiFlag, short messageLength,
			short version, short msgId) {
		super();
		this.systemId = Constants.SYSTEMID;
		this.length = length;
		this.multiFlag = multiFlag;
		this.messageLength = messageLength;
		this.time = new Long(System.currentTimeMillis() * 1000).intValue();
		this.version = version;
		this.msgId = msgId;
	}

	public Header(short length, byte multiFlag, short messageLength, short msgId) {
		super();
		this.systemId = Constants.SYSTEMID;
		this.length = length;
		this.multiFlag = multiFlag;
		this.messageLength = messageLength;
		this.time = new Long(System.currentTimeMillis() * 1000).intValue();
		this.version = Constants.CURRENT_VERSION;
		this.msgId = msgId;
	}

	/**
	 * 
	 * @param length
	 * @param messageLength
	 * @param msgId
	 */
	public Header(short length, short messageLength, short msgId) {
		super();
		this.systemId = Constants.SYSTEMID;
		this.length = length;
		this.multiFlag = Constants.IS_NOT_MULTIPLE;
		this.messageLength = messageLength;
		this.time = new Long(System.currentTimeMillis() * 1000).intValue();
		this.version = Constants.CURRENT_VERSION;
		this.msgId = msgId;
	}

	public byte getSystemId() {
		return systemId;
	}

	public void setSystemId(byte systemId) {
		this.systemId = systemId;
	}

	public short getLength() {
		return length;
	}

	public void setLength(short length) {
		this.length = length;
	}

	public byte getMultiFlag() {
		return multiFlag;
	}

	public void setMultiFlag(byte multiFlag) {
		this.multiFlag = multiFlag;
	}

	public short getMessageLength() {
		return messageLength;
	}

	public void setMessageLength(short messageLength) {
		this.messageLength = messageLength;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public short getVersion() {
		return version;
	}

	public void setVersion(short version) {
		this.version = version;
	}

	public short getMsgId() {
		return msgId;
	}

	public void setMsgId(short msgId) {
		this.msgId = msgId;
	}

	public static void main(String[] args) {
		// byte ii = (byte) 0xFF;
		// System.out.println(ii);
		// byte iii = (byte) 0x29;
		// System.out.println(iii);
	}
}
