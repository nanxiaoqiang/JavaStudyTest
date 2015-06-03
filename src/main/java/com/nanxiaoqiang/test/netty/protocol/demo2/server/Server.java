package com.nanxiaoqiang.test.netty.protocol.demo2.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nanxiaoqiang.test.netty.protocol.demo2.codec.NxqDecoder;
import com.nanxiaoqiang.test.netty.protocol.demo2.codec.NxqEncoder;

public class Server {

	private static Logger LOGGER = LogManager.getLogger(Server.class.getName());

	private static int PORT = Integer.parseInt(System.getProperty("port",
			"8080"));

	private ChannelFuture cf;

	private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
	private final EventLoopGroup workerGroup = new NioEventLoopGroup();

	private Server() {
		this(PORT);
	}

	public Server(int port) {
		if (port <= 1000)
			PORT = 8080;
		else
			PORT = port;
	}

	public static Server server = null;// new Server();

	public static void main(String[] args) throws Exception {
		Server.server = new Server();
		Server.server.startup();
	}

	protected void startup() throws Exception {
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast("WhiteListHandler",
									new WriteListHandler());
							ch.pipeline().addLast(
									new NxqDecoder(1024 * 1024, 4, 2, 8));
							ch.pipeline().addLast("MessageEncoder",
									new NxqEncoder());
							ch.pipeline().addLast("HeartBeatHandler",
									new HeartBeatServerHandler());
						}
					});

			// Bind and start to accept incoming connections.
			cf = b.bind(PORT);
			LOGGER.info("NettyServer启动成功:" + PORT);
			cf.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public ChannelFuture getCf() {
		return cf;
	}

	public void setCf(ChannelFuture cf) {
		this.cf = cf;
	}

}
