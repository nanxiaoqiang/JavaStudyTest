package com.nanxiaoqiang.test.netty.hpsocketconn2.server2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 接受到消息之后给所有的Channel广播！<br/>
 * channelRegistered-->channelActive-->channelReadComplete-->channelInactive
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月21日
 * 
 */
@Sharable
public class DiscardServerObjectHandler extends ChannelHandlerAdapter {

	private static Logger logger = LogManager
			.getLogger(DiscardServerObjectHandler.class.getName());

	@SuppressWarnings("unused")
	private ChannelHandlerContext ctx;

	private static final ChannelGroup channelGroups = new DefaultChannelGroup(
			GlobalEventExecutor.INSTANCE);

	public DiscardServerObjectHandler() {
		logger.debug("DiscardServerObjectHandler");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.debug("channelActive");
		// 自己的实现，当有连接连接的时候。
		this.ctx = ctx;

		channelGroups.add(ctx.channel());
		logger.debug("channelGroups:" + channelGroups.size());

		// 输出当前连接channel的id
		InetSocketAddress isa = (InetSocketAddress) ctx.channel()
				.remoteAddress();
		logger.info(isa.getAddress().getHostAddress() + ":" + isa.getPort());
		logger.info(ctx.channel().id().asLongText() + "|"
				+ ctx.channel().id().asShortText());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.debug("exceptionCaught");
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.debug("channelInactive");
		channelGroups.remove(ctx.channel());
		logger.debug("channelGroups:" + channelGroups.size());
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {// ByteBuf b = (ByteBuf) msg;
		logger.debug("messageReceived: " + msg.toString());// .toString(CharsetUtil.UTF_8));
		// AlarmEntity ae = new AlarmEntity();
		// ae.setCmd("insert");
		// for (int i = 0; i < 10; i++) {
		// IscsAlarmHistory iah = new IscsAlarmHistory();
		// iah.setId(1L);
		// iah.setAlarmtime(DateTime.now().minusMinutes(2).toDate());
		// iah.setApplytime(DateTime.now().toDate());
		// iah.setEqpid(11111111L);
		// iah.setEqpname("风机");
		// iah.setInfoid(7010101L);
		// iah.setIsapply(1);
		// iah.setIsnormal(1);
		// iah.setLineid(7L);
		// iah.setAlarmlevel("3");
		// iah.setLinename("七号线");
		// iah.setNormaltime(DateTime.now().minusMillis(1).toDate());
		// iah.setRemark("风机故障");
		// iah.setStationid(721L);
		// iah.setStationname("焦化厂站");
		// iah.setSystem("BAS");
		// ae.getAlarms().add(iah);
		// }
		// ObjectMapper mapper = new ObjectMapper();
		// String str = "";
		// str = mapper.writeValueAsString(ae);
		//
		// if (StringUtils.isNotBlank(str)) {
		// ByteBuf bf = ByteBufUtil.encodeString(ctx.alloc(), CharBuffer
		// .wrap(String.format("%08d", str.getBytes().length) + str),
		// CharsetUtil.UTF_8);
		// logger.debug(String.format("%08d", str.getBytes().length) + str);
		// ctx.writeAndFlush(bf);
		// }
		// logger.info("line.separator:" +
		// System.getProperty("line.separator"));
		if (msg.toString().startsWith("心跳")) {
			logger.debug("收到心跳包：" + msg.toString());
			String r = "收到" + msg.toString();
			byte[] b = (r + System.getProperty("line.separator")).getBytes();
			ByteBuf bf = Unpooled.buffer(b.length);
			bf.writeBytes(b);
			logger.debug("channelGroups:" + channelGroups.size());
			ctx.writeAndFlush(bf);
			logger.debug("给channel" + ctx.channel().id().asShortText() + "发送了:"
					+ r);
		} else {
			String r = "byte发送" + msg.toString();
			byte[] b = (r + System.getProperty("line.separator")).getBytes();
			ByteBuf bf = Unpooled.buffer(b.length);
			bf.writeBytes(b);
			logger.debug("channelGroups:" + channelGroups.size());
			channelGroups.writeAndFlush(bf);
			logger.debug("发送了:" + r);
		}
		// for (Channel c : channelGroups) {
		// logger.info(c.isActive() + "|" + c.isOpen() + "|"
		// + c.isRegistered() + "|" + c.isWritable());
		// c.writeAndFlush(bf);
		// }
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		logger.debug("userEventTriggered");
		if (!(evt instanceof IdleStateEvent)) {
			return;
		}
		logger.info(evt.toString());
		IdleStateEvent e = (IdleStateEvent) evt;
		if (e.state() == IdleState.ALL_IDLE) {
			logger.debug(ctx.channel().id().asShortText()
					+ "|userEventTriggered:ALL_IDLE");
		} else if (e.state() == IdleState.READER_IDLE) {
			logger.debug(ctx.channel().id().asShortText()
					+ "|userEventTriggered:READER_IDLE");
		} else if (e.state() == IdleState.WRITER_IDLE) {
			logger.debug(ctx.channel().id().asShortText()
					+ "|userEventTriggered:WRITER_IDLE");
		}
	}

}
