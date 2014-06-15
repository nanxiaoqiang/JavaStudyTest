package com.nanxiaoqiang.test.netty.discard.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DiscardServerInitializer extends ChannelInitializer<SocketChannel> {

	private static Logger logger = LogManager
			.getLogger(DiscardServerInitializer.class.getName());

	private static final StringDecoder DECODER = new StringDecoder();

	private static final StringEncoder ENCODER = new StringEncoder();

	private static final DiscardServerHandler SERVER_HANDLER = new DiscardServerHandler();

	public DiscardServerInitializer() {
		logger.info("DiscardServerInitializer");
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();

		p.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters
				.lineDelimiter()));
		p.addLast(DECODER);
		p.addLast(ENCODER);
		p.addLast(SERVER_HANDLER);
	}

}
