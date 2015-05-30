package com.nanxiaoqiang.test.netty.protocol.demo2.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nanxiaoqiang.test.netty.protocol.demo2.msg.BaseMessage;
import com.nanxiaoqiang.test.netty.protocol.demo2.msg.Header;
import com.nanxiaoqiang.test.netty.protocol.demo2.msg.MsgType;

public class HeartBeatClientHandler extends ChannelHandlerAdapter {

	private static Logger LOGGER = LogManager
			.getLogger(HeartBeatClientHandler.class.getName());

	private volatile ScheduledFuture<?> heartBeat = null;;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		LOGGER.debug("channelActive");
		if (heartBeat == null) {
			LOGGER.debug("初始化heartBeat的Task");
			heartBeat = ctx.executor().scheduleAtFixedRate(
					new HeartBeatClientHandler.HeartBeatTask(ctx), 0, 5000,
					TimeUnit.MILLISECONDS);
		}
		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		BaseMessage message = (BaseMessage) msg;
		// 握手成功，主动发送心跳消息
		if (message.getHeader() != null
				&& message.getHeader().getMsgId() == MsgType.MSG_BEATHEART
						.getType()) {
			LOGGER.debug("收到心跳回执：" + message);
		} else {
			// 该干嘛干嘛
			ctx.fireChannelRead(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		if (heartBeat != null) {
			heartBeat.cancel(true);
			heartBeat = null;
		}
		super.exceptionCaught(ctx, cause);
	}

	public class HeartBeatTask implements Runnable {

		private final ChannelHandlerContext ctx;

		public HeartBeatTask(final ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}

		@Override
		public void run() {
			BaseMessage heatBeat = buildHeatBeat();
			LOGGER.debug("Client发送心跳：" + heatBeat);
			ctx.writeAndFlush(heatBeat);
		}

		private BaseMessage buildHeatBeat() {
			BaseMessage msg = new BaseMessage();
			Header h = new Header((short) 0, (short) 0,
					MsgType.MSG_BEATHEART.getType());
			msg.setHeader(h);
			return msg;
		}

	}
}
