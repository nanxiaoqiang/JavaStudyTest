package com.nanxiaoqiang.test.netty.protocol.demo2.server;

import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Iterator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AllChannelGroup {
	private static final DefaultChannelGroup channelGroups = new DefaultChannelGroup(
			GlobalEventExecutor.INSTANCE);

	public static DefaultChannelGroup getChannelgroups() {
		return channelGroups;
	}

	public static void clearChannelgroups() {
		channelGroups.clear();
	}

	public static String getAllChannelgroups() {
		Iterator<Channel> it = channelGroups.iterator();
		System.out.println(channelGroups.size());
		while (it.hasNext()) {
			Channel c = it.next();
			System.out.println(c);
		}

		return ToStringBuilder.reflectionToString(channelGroups.toArray(),
				ToStringStyle.JSON_STYLE);
	}

	public static void main(String[] args) {
		System.out.println(AllChannelGroup.getAllChannelgroups());
	}
}
