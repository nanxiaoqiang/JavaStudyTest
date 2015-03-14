package com.nanxiaoqiang.test.netty.http.sever1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NettyHttpServer {

	private static Logger logger = LogManager.getLogger(NettyHttpServer.class
			.getName());

	// private static final String DEFAULT_URL =
	// "/src/com/nanxiaoqiang/test/netty/http/server1/";

	public void run(final int port) throws Exception {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();

		ServerBootstrap b = new ServerBootstrap();
		try {
			b.option(ChannelOption.SO_BACKLOG, 1024).group(boss, worker)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new HttpServerInitializer());
			Channel ch = b.bind(port).sync().channel();
			logger.info("Http服务器启动！！http://localhost:" + port);
			ch.closeFuture().sync();
		} finally {
			worker.shutdownGracefully();
			boss.shutdownGracefully();
		}
	}

	public NettyHttpServer() {
		logger.info("NettyHttpServer");
	}

	public static void main(String[] args) throws Exception {
		// String host = "172.20.98.42";
		int port = 8999;
		// String url = DEFAULT_URL;
		new NettyHttpServer().run(port);
	}

}
