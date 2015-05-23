package com.nanxiaoqiang.test.netty.protocol.demo2.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerHandler extends LengthFieldBasedFrameDecoder {

	private static Logger LOGGER = LogManager.getLogger(ServerHandler.class
			.getName());

	/**
	 * 
	 * @param maxFrameLength
	 *            1024 * 1024 这个定义最大帧的长度
	 * @param lengthFieldOffset
	 *            4 长度属性的起始指针(偏移量)
	 * @param lengthFieldLength
	 *            4 长度属性的长度，即存放数据包长度的变量的的字节所占的长度
	 * @throws IOException
	 */
	public ServerHandler(int maxFrameLength, int lengthFieldOffset,
			int lengthFieldLength) throws IOException {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
		LOGGER.debug(new StringBuffer("ServerHandler Constructor|:")
				.append(maxFrameLength).append("|lengthFieldOffset:")
				.append(lengthFieldOffset).append("|lengthFieldLength:")
				.append(lengthFieldLength));
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in)
			throws Exception {
		ByteBuf frame = (ByteBuf) super.decode(ctx, in);
		if (frame == null) {
			return null;// 空的就不管了
		}

		return null;//super.decode(ctx, in);
	}

}
