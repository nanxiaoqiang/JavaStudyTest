package com.nanxiaoqiang.test.netty.protocol.demo2.msg;

import java.io.Serializable;

public class BaseMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Header header;

	private byte[] data;// 临时

	public BaseMessage() {
	}

	public BaseMessage(Header header, byte[] data) {
		super();
		this.header = header;
		this.data = data;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
