package com.nanxiaoqiang.test.netty.discard.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DiscardClientInitializer extends ChannelInitializer<SocketChannel> {
	private static Logger logger = LogManager
			.getLogger(DiscardClientInitializer.class.getName());

	private static final StringDecoder DECODER = new StringDecoder();

	private static final StringEncoder ENCODER = new StringEncoder();

	private static final DiscardClientHandler CLIENT_HANDLER = new DiscardClientHandler();

	public DiscardClientInitializer() {
		logger.info("DiscardClientInitializer");
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();

		p.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters
				.lineDelimiter()));
		p.addLast(DECODER);
		p.addLast(ENCODER);
		p.addLast(CLIENT_HANDLER);
	}

}
