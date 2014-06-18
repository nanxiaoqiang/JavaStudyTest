package com.nanxiaoqiang.test.netty.hpsocketconn.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 世界工程的测试，接收byte[]，发送的是String
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月16日
 * 
 */
public class HpSocketConnServer {

	private static Logger logger = LogManager
			.getLogger(HpSocketConnServer.class.getName());

	static final int PORT = Integer
			.parseInt(System.getProperty("port", "8080"));

	public HpSocketConnServer() {
		logger.info("HpSocketConnServer");
	}

	public static void main(String[] args) {
		logger.info("初始化");

		EventLoopGroup bossGroup = new NioEventLoopGroup(1);// 处理accpet事件？
		EventLoopGroup workerGroup = new NioEventLoopGroup();// 处理每一个连接的IO
		try {
			ServerBootstrap b = new ServerBootstrap();

			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new DiscardServerObjectInitializer());

			ChannelFuture cf = b.bind(PORT);
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
