package com.nanxiaoqiang.test.netty.hpsocketconn.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.CharBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

@Sharable
public class DiscardServerObjectHandler extends
		SimpleChannelInboundHandler<Object> {

	private static Logger logger = LogManager
			.getLogger(DiscardServerObjectHandler.class.getName());

	@SuppressWarnings("unused")
	private ChannelHandlerContext ctx;

	public DiscardServerObjectHandler() {
		logger.info("DiscardServerObjectHandler");
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf b = (ByteBuf) msg;
		logger.info("messageReceived: " + b.toString(CharsetUtil.UTF_8));
		// ctx.writeAndFlush(msg);
		String str = "{'LineName':'6号线二期','SystemName':'BAS','StationName':'会展中心','AlarmTime':'"
				+ DateTime.now().toString("yyyy-MM-dd HH:mm:ss")
				+ "','AlarmLevel':'4','EqpName':'区间射流风机-2212','EqpContext':'不转啦不转啦不转不转啦','AlarmApply':true,'AlarmApplyTime':'"
				+ DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + "'}";
		logger.info("发送数据：(" + str.length() + ") " + str);
		// ByteBuf bf = ctx.channel().alloc().buffer(str.length())
		// .writeBytes(str.getBytes(CharsetUtil.UTF_8));
		ByteBuf bf = ByteBufUtil.encodeString(ctx.alloc(),
				CharBuffer.wrap(str), CharsetUtil.UTF_8);
		ctx.writeAndFlush(bf);

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 自己的实现，当有连接连接的时候。
		this.ctx = ctx;
		// 输出当前连接channel的id
		logger.info(ctx.channel().id().asLongText() + "|"
				+ ctx.channel().id().asShortText());
		// sendActiveMsg();
	}
}
