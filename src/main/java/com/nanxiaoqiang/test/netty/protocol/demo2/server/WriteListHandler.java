package com.nanxiaoqiang.test.netty.protocol.demo2.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WriteListHandler extends ChannelHandlerAdapter {

	private String[] whitekList = { "127.0.0.1", "192.168.1.104" };

	private static Logger LOGGER = LogManager.getLogger(WriteListHandler.class
			.getName());

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {// 输出当前连接channel的id
		InetSocketAddress isa = (InetSocketAddress) ctx.channel()
				.remoteAddress();
		LOGGER.debug("新建连接来自于：" + isa.getAddress().getHostAddress() + ":"
				+ isa.getPort());
		LOGGER.debug("新建连接ID:" + ctx.channel().id().asShortText() + "|"
				+ ctx.channel().id().asLongText());
		boolean isOK = false;
		for (String WIP : whitekList) {
			if (WIP.equals(isa.getAddress().getHostAddress())) {
				isOK = true;
				break;
			}
		}
		if (isOK) {
			LOGGER.debug("验证白名单成功！");
			// 加到ChannelGroup中
			AllChannelGroup.getChannelgroups().add(ctx.channel());
			LOGGER.debug("channelGroups:"
					+ AllChannelGroup.getChannelgroups().size());
			// 透传
		} else {
			LOGGER.debug("验证白名单失败！");
			// 退出
			ctx.close();
		}
	}
}
