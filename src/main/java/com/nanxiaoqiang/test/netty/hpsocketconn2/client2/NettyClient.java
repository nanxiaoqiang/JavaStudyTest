package com.nanxiaoqiang.test.netty.hpsocketconn2.client2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 开始写Netty客户端。加入了心跳测试，client2包和server2包是一对。
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月22日
 * 
 */
public class NettyClient {
	private static Logger logger = LogManager.getLogger(NettyClient.class
			.getName());
	// 处理Callback应答器
	// private final ExecutorService publicExecutor;

	private final Bootstrap bootstrap = new Bootstrap();

	private final EventLoopGroup group;

	// private final NettyClientConfig nettyClientConfig;

	// private DefaultEventExecutorGroup defaultEventExecutorGroup;

	private InetSocketAddress remote_addr;

	private ChannelFuture cf;

	int readerIdleTimeSeconds = 10;
	int writerIdleTimeSeconds = 0;
	int allIdleTimeSeconds = 0;

	public NettyClient(String host, int port) {

		// this.publicExecutor = Executors.newFixedThreadPool(publicThreadNums,
		// new ThreadFactory() {
		// private AtomicInteger threadIndex = new AtomicInteger(0);
		//
		// /*
		// * (non-Javadoc)
		// *
		// * @see
		// * java.util.concurrent.ThreadFactory#newThread(java.lang
		// * .Runnable)
		// */
		// @Override
		// public Thread newThread(Runnable r) {
		// // 给Thread命名
		// return new Thread(r, "NettyClientPublicExecutor_"
		// + this.threadIndex.incrementAndGet());
		// }
		// });

		this.group = new NioEventLoopGroup();
		// nettyClientConfig.getClientSelectorThreads());
		// this.defaultEventExecutorGroup = new DefaultEventExecutorGroup(//
		// nettyClientConfig.getClientWorkerThreads(), //
		// new ThreadFactory() {
		//
		// private AtomicInteger threadIndex = new AtomicInteger(0);
		//
		// /*
		// * (non-Javadoc)
		// *
		// * @see
		// * java.util.concurrent.ThreadFactory#newThread(java.lang
		// * .Runnable)
		// */
		// @Override
		// public Thread newThread(Runnable r) {
		// return new Thread(r, "NettyClientWorkerThread_"
		// + this.threadIndex.incrementAndGet());
		// }
		// });
		this.bootstrap.group(this.group).channel(NioSocketChannel.class)
		//
				.option(ChannelOption.TCP_NODELAY, true)
				// .option(ChannelOption.SO_KEEPALIVE, true)
				// 缓冲大小调节，╮(￣▽￣")╭ 具体参数不知道应该怎么调整。
				// .option(ChannelOption.SO_SNDBUF,
				// NettyConf.SocketSndbufSize)
				// 缓冲大小调节，╮(￣▽￣")╭ 具体参数不知道应该怎么调整。
				// .option(ChannelOption.SO_RCVBUF,
				// NettyConf.SocketRcvbufSize)
				.handler(new ChannelInitializer<SocketChannel>() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * io.netty.channel.ChannelInitializer#initChannel(io.netty
					 * .channel.Channel)
					 */
					@Override
					protected void initChannel(SocketChannel ch)
							throws Exception {
						ChannelPipeline p = ch.pipeline();
						p.addLast(new LineBasedFrameDecoder(1024))
								.addLast(new StringDecoder())
								// ch.pipeline().addLast(new
								// LineBasedFrameDecoder(1024))
								// .addLast(new StringDecoder())
								.addLast(
										new IdleStateHandler(
												readerIdleTimeSeconds,
												writerIdleTimeSeconds,
												allIdleTimeSeconds))
								.addLast(new NettyClientHandler());
						// .addLast(defaultEventExecutorGroup,
						// new LineBasedFrameDecoder(1024),// 字符串消息，回车结束
						// new StringDecoder(),//
						// // new StringEncoder(),// 不要String编码
						// new NettyClientHandler());
					}
				});

		// Start the connection attempt.
		remote_addr = new InetSocketAddress(host, port);
		try {
			cf = bootstrap.connect(remote_addr).sync();
			logger.info("启动NettyClient，连接: {}", remote_addr);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void send(String message) {
		logger.debug(message);
		byte[] b = (message + System.getProperty("line.separator")).getBytes();
		ByteBuf bf = Unpooled.buffer(b.length);
		bf.writeBytes(b);
		this.cf.channel().writeAndFlush(bf);
	}

	public void shutdown() {
		this.group.shutdownGracefully();
	}

	public static void main(String[] args) {

		NettyClient c1 = new NettyClient("localhost", 8080);
		// NettyClient c2 = new NettyClient("localhost", 8080);
		// NettyClient c3 = new NettyClient("localhost", 8080);
		// NettyClient c4 = new NettyClient("localhost", 8080);

		c1.send("wowowo");
	}
}
