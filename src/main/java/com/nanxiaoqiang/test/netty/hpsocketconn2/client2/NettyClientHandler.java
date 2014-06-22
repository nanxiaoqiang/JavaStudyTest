package com.nanxiaoqiang.test.netty.hpsocketconn2.client2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Sharable
public class NettyClientHandler extends ChannelHandlerAdapter {
	private static Logger logger = LogManager
			.getLogger(NettyClientHandler.class.getName());

	private AtomicInteger i = new AtomicInteger(0);

	private ChannelHandlerContext ctx;

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
		// if (i.intValue() < 10) {
		// String r = "byte发送" + msg.toString();
		// byte[] b = (r + System.getProperty("line.separator")).getBytes();
		// ByteBuf bf = Unpooled.buffer(b.length);
		// bf.writeBytes(b);
		// ctx.writeAndFlush(bf);
		// i.getAndIncrement();
		// } else {
		// i = new AtomicInteger(0);
		// }
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.debug("channelActive");
		this.ctx = ctx;
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		logger.debug("userEventTriggered");
		if (!(evt instanceof IdleStateEvent)) {
			return;
		}
		String r = "心跳" + System.currentTimeMillis();
		byte[] b = (r + System.getProperty("line.separator")).getBytes();
		ByteBuf bf = Unpooled.buffer(b.length);
		bf.writeBytes(b);
		ctx.writeAndFlush(bf);
		logger.debug("send:" + r);
		// IdleStateEvent e = (IdleStateEvent) evt;
		// long time = System.currentTimeMillis();
		// ByteBuf bf = null;
		// String idleMsg = null;
		// // byte[] b;
		// if (e.state() == IdleState.ALL_IDLE) {
		// logger.debug("userEventTriggered:ALL_IDLE");
		// idleMsg = "ALL_IDLE" + time + System.getProperty("line.separator");
		// byte[] b = idleMsg.getBytes();
		// bf = Unpooled.buffer(b.length);
		// bf.writeBytes(b);
		// ctx.writeAndFlush(bf);
		// logger.debug("send:" +idleMsg);
		// } else if (e.state() == IdleState.READER_IDLE) {
		// logger.debug("userEventTriggered:READER_IDLE");
		// idleMsg = "READER_IDLE" + time
		// + System.getProperty("line.separator");
		// byte[] b = idleMsg.getBytes();
		// bf = Unpooled.buffer(b.length);
		// ctx.writeAndFlush(bf);
		// logger.debug("send:" +idleMsg);
		// } else if (e.state() == IdleState.WRITER_IDLE) {
		// logger.debug("userEventTriggered:WRITER_IDLE");
		// idleMsg = "WRITER_IDLE" + time
		// + System.getProperty("line.separator");
		// byte[] b = idleMsg.getBytes();
		// bf = Unpooled.buffer(b.length);
		// ctx.writeAndFlush(bf);
		// logger.debug("send:" +idleMsg);
		// }
	}

}
