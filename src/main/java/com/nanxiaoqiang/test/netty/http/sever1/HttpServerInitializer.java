package com.nanxiaoqiang.test.netty.http.sever1;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
	// private static Logger logger = LogManager
	// .getLogger(HttpServerInitializer.class.getName());

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		// p.addLast(new HttpRequestDecoder());
		// // cp.addLast(new HttpObjectAggregator(65536));
		// p.addLast(new HttpResponseEncoder());

		p.addLast(new LoggingHandler(LogLevel.DEBUG));
		p.addLast(new HttpServerCodec());
		p.addLast(new HttpServerHandler());
	}
}
