package com.nanxiaoqiang.test.netty.protocol.demo2.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

import com.nanxiaoqiang.test.netty.protocol.demo2.codec.NxqDecoder;
import com.nanxiaoqiang.test.netty.protocol.demo2.codec.NxqEncoder;

public class Client {

	private final EventLoopGroup group = new NioEventLoopGroup();

	private ChannelFuture cf;

	private InetSocketAddress remote_addr;

	static final String HOST = System.getProperty("host", "127.0.0.1");

	static final int PORT = Integer
			.parseInt(System.getProperty("port", "8080"));

	private Client() {
		this(HOST, PORT);
	}

	public Client(String host, int port) {
		remote_addr = new InetSocketAddress(host, port);
	}

	public static Client client = null;// new Client();

	public static void main(String[] args) throws Throwable {
		Client.client = new Client();
		Client.client.connect();
		// BaseMessage msg = new BaseMessage();
		// Header h = new Header((short) 0, (short) 0, (short) 0x03);
		// msg.setHeader(h);
		// Client.client.getCf().channel().writeAndFlush(msg);
	}

	public void connect() throws Throwable {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch)
							throws Exception {
						ch.pipeline().addLast(
								new NxqDecoder(1024 * 1024, 4, 2, 8));
						ch.pipeline().addLast("MessageEncoder",
								new NxqEncoder());
						ch.pipeline().addLast("HeartBeatClientHandler",
								new HeartBeatClientHandler());
					}
				});
		cf = bootstrap.connect(remote_addr).sync();
		cf.channel().closeFuture().sync();
	}

	public ChannelFuture getCf() {
		return cf;
	}

	public void shutdown() {
		this.group.shutdownGracefully();
	}

}
