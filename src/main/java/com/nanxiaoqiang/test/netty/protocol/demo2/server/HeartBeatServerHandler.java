package com.nanxiaoqiang.test.netty.protocol.demo2.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nanxiaoqiang.test.netty.protocol.demo2.msg.BaseMessage;
import com.nanxiaoqiang.test.netty.protocol.demo2.msg.Header;
import com.nanxiaoqiang.test.netty.protocol.demo2.msg.MsgType;

public class HeartBeatServerHandler extends ChannelHandlerAdapter {

	private static Logger LOGGER = LogManager
			.getLogger(HeartBeatServerHandler.class.getName());

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		BaseMessage message = (BaseMessage) msg;
		// LOGGER.debug(message == null);
		// 返回心跳应答消息
		if (message.getHeader() != null
				&& message.getHeader().getMsgId() == MsgType.MSG_BEATHEART
						.getType()) {
			LOGGER.debug("收到心跳:" + message);
			BaseMessage heartBeat = buildHeatBeat();
			LOGGER.debug("发送心跳回执:" + heartBeat);
			ctx.writeAndFlush(heartBeat);
		} else {
			// 不是心跳该干嘛干嘛
			ctx.fireChannelRead(msg);
		}
		// super.channelRead(ctx, msg);
	}

	private BaseMessage buildHeatBeat() {
		BaseMessage msg = new BaseMessage();
		Header h = new Header((short) 0, (short) 0,
				MsgType.MSG_BEATHEART.getType());
		msg.setHeader(h);
		return msg;
	}
}
