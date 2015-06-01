package com.nanxiaoqiang.test.netty.protocol.demo2.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 白名单过滤,放在了channelRegistered,因为channelActive是已经连上了
 * 
 * @description:
 * @author: nanxiaoqiang
 * @version: V1.00
 * @create Date: 2015年6月1日下午5:08:45
 */
public class WriteListHandler extends ChannelHandlerAdapter {

	/**
	 * 白名单，可以放到一个单独的类的static属性中，或者到Redis之类的库中查询
	 */
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
		// 或者到Redis之类的库中查询
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
