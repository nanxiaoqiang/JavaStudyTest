package com.nanxiaoqiang.test.netty.hpsocketconn2.client;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Sharable
public class NettyClientHandler extends ChannelHandlerAdapter {
	private static Logger logger = LogManager
			.getLogger(NettyClientHandler.class.getName());

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
		InetSocketAddress isa = (InetSocketAddress) ctx.channel()
				.remoteAddress();
		logger.warn(isa.getAddress().getHostAddress() + ":" + isa.getPort()
				+ ": excption!" + cause.getMessage());
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		logger.info(msg.toString());
	}

}
