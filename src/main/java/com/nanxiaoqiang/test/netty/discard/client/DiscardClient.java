package com.nanxiaoqiang.test.netty.discard.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DiscardClient {

	private static Logger logger = LogManager.getLogger(DiscardClient.class
			.getName());

	static final String HOST = System.getProperty("host", "127.0.0.1");

	static final int PORT = Integer
			.parseInt(System.getProperty("port", "8080"));

	static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

	public DiscardClient() {
		logger.info("DiscardClient");
	}

	public static void main(String[] args) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();

			b.group(group).channel(NioSocketChannel.class)
					.handler(new DiscardClientInitializer());
			// .handler(new ChannelInitializer<SocketChannel>() {
			//
			// @Override
			// protected void initChannel(SocketChannel ch)
			// throws Exception {
			// ChannelPipeline p = ch.pipeline();
			// p.addLast(new DiscardClientHandler());
			// }
			//
			// });
			ChannelFuture f = b.connect(HOST, PORT).sync();

			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}

}
