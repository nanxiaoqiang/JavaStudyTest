package com.nanxiaoqiang.test.netty.hpsocketconn2.server3;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DiscardServerObjectInitializer extends
		ChannelInitializer<SocketChannel> {

	private static Logger logger = LogManager
			.getLogger(DiscardServerObjectInitializer.class.getName());

	private static final DiscardServerObjectHandler SERVER_HANDLER = new DiscardServerObjectHandler();

	public DiscardServerObjectInitializer() {
		logger.info("DiscardServerObjectInitializer");
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		// p.addLast(ENCODER);
		p.addLast(new LineBasedFrameDecoder(1024)).addLast(new StringDecoder())
				.addLast(SERVER_HANDLER);
	}

}
