package com.nanxiaoqiang.test.netty.protocol.demo2.server;

import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class AllChannelGroup {
	private static final DefaultChannelGroup channelGroups = new DefaultChannelGroup(
			GlobalEventExecutor.INSTANCE);

	public static DefaultChannelGroup getChannelgroups() {
		return channelGroups;
	}

	public static void clearChannelgroups() {
		channelGroups.clear();
	}

	public static void main(String[] args) {

	}
}
