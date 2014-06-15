package com.nanxiaoqiang.test.netty.discard.server;

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
 * 第一个Netty的例子<br/>
 * 根据默认的官方例子DiscardServer改的。<br/>
 * 接收和发送的均为String
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月15日
 */
public class DiscardServer {

	private static Logger logger = LogManager.getLogger(DiscardServer.class
			.getName());

	static final boolean SSL = System.getProperty("ssl") != null;

	static final int PORT = Integer
			.parseInt(System.getProperty("port", "8080"));

	public DiscardServer() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// SslContext sslCtx;
		// 最新的Netty才有，这个没有。

		logger.info("初始化");

		EventLoopGroup bossGroup = new NioEventLoopGroup(1);// 处理accpet事件？
		EventLoopGroup workerGroup = new NioEventLoopGroup();// 处理每一个连接的IO
		try {
			ServerBootstrap b = new ServerBootstrap();

			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new DiscardServerInitializer());
			// .childHandler(new ChannelInitializer<SocketChannel>() {
			//
			// @Override
			// protected void initChannel(SocketChannel ch)
			// throws Exception {
			// // TODO Auto-generated method stub
			// ChannelPipeline p = ch.pipeline();
			//
			// p.addLast(new DiscardServerHandler());
			// }
			// });

			// 在所有的网卡上开启监听端口？
			// 多网卡情况需要测试
			// 可以绑定ip和host，这样是不是可以对多网卡进行区分？
			// 但是还是估计永远是绑定全网卡的？不过估计分IP。
			// 需要多IP测试。
			ChannelFuture cf = b.bind(PORT).sync();
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
