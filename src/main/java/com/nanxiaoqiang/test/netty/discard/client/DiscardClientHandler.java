package com.nanxiaoqiang.test.netty.discard.client;

import io.netty.buffer.ByteBuf;
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
public class DiscardClientHandler extends SimpleChannelInboundHandler<String> {

	private static Logger logger = LogManager
			.getLogger(DiscardClientHandler.class.getName());

	private ByteBuf content;

	private ChannelHandlerContext ctx;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.ctx = ctx;
		// 在客户端和服务端，channel的id是不一样的。
		logger.info(ctx.channel().id().asLongText() + "|"
				+ ctx.channel().id().asShortText());
		// 初始化Message
		// 长度为DiscardClient.SIZE
		// 默认一个是NULL的内容
		// content = ctx.alloc().directBuffer(DiscardClient.SIZE)
		// .writeZero(DiscardClient.SIZE);

		// 发送数据
		// generateTraffic();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		content.release();
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String msg)
			throws Exception {
		// 必须的实现
		// ByteBuf b = (UnpooledUnsafeDirectByteBuf) msg;
		// String str = new String(b.toString());
		logger.info("messageReceived." + msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	long counter;

	private void generateTraffic() throws UnknownHostException {
		// this.ctx.writeAndFlush(content.duplicate().retain()).addListener(
		// trafficGenerator);
		logger.info("generateTraffic");
		ctx.writeAndFlush(
				"我是客户端，我第一次登陆:" + InetAddress.getLocalHost().getHostName()
						+ "|" + InetAddress.getLocalHost().getHostAddress())
				.addListener(trafficGenerator);
	}

	private final ChannelFutureListener trafficGenerator = new ChannelFutureListener() {

		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			// 如果发送成功
			if (future.isSuccess()) {
				// future.channel().close();
			} else {
				future.cause().printStackTrace();
				generateTraffic();
			}
		}
	};
}
