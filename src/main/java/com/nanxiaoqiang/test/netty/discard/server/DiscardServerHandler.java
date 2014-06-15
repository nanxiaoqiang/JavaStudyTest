package com.nanxiaoqiang.test.netty.discard.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Sharable
public class DiscardServerHandler extends SimpleChannelInboundHandler<String> {

	private static Logger logger = LogManager
			.getLogger(DiscardServerHandler.class.getName());

	private ChannelHandlerContext ctx;

	public DiscardServerHandler() {
		logger.info("DiscardServerHandler");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 自己的实现，当有连接连接的时候。
		this.ctx = ctx;
		sendActiveMsg();
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String msg)
			throws Exception {
		// discard
		logger.info("messageReceived." + msg);
		if (msg.isEmpty()) {
			ctx.writeAndFlush("别老是空着，敲点什么。\r\n");
		} else if (msg.equalsIgnoreCase("exit")) {
			ChannelFuture f = ctx.writeAndFlush("exit\r\n");
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		// 关掉出错的ctx
		ctx.close();
	}

	private void sendActiveMsg() throws UnknownHostException {
		logger.info("sendActiveMsg");
		ctx.writeAndFlush("欢迎登录" + InetAddress.getLocalHost().getHostName()
				+ "|" + InetAddress.getLocalHost().getHostAddress() + "\r\n");
		// .addListener(listener);
		// ByteBuf b = ctx.alloc().buffer();// 建立一个不定长的ByteBuf
		// b.writeBytes("欢迎连接到Server".getBytes());
		// ctx.writeAndFlush(b).addListener(listener);
	}

	private final ChannelFutureListener listener = new ChannelFutureListener() {

		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			if (future.isSuccess()) {
				// future.channel().close().sync();
			} else {
				sendActiveMsg();
			}
		}

	};

}
