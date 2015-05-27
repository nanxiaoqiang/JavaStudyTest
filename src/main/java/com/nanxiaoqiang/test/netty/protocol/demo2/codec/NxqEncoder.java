package com.nanxiaoqiang.test.netty.protocol.demo2.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nanxiaoqiang.test.netty.protocol.demo2.msg.BaseMessage;

public class NxqEncoder extends MessageToByteEncoder<BaseMessage> {

	private static Logger LOGGER = LogManager.getLogger(NxqEncoder.class
			.getName());

	@Override
	protected void encode(ChannelHandlerContext ctx, BaseMessage msg,
			ByteBuf out) throws Exception {
		if (msg == null || msg.getHeader() == null)
			throw new Exception("The encode message is null");

		out.writeByte(msg.getHeader().getSystemId());
		out.writeShort(msg.getHeader().getLength());
		out.writeByte(msg.getHeader().getMultiFlag());
		out.writeShort(msg.getHeader().getMessageLength());
		out.writeInt(msg.getHeader().getTime());
		out.writeShort(msg.getHeader().getVersion());
		out.writeShort(msg.getHeader().getMsgId());
	}

}
