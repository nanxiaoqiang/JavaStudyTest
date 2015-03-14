package com.nanxiaoqiang.test.netty.http.sever1;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpServerHandler extends ChannelHandlerAdapter {
	private static Logger logger = LogManager.getLogger(HttpServerHandler.class
			.getName());
	// private static final byte[] CONTENT = { 'H', 'e', 'l', 'l', 'o', ',',
	// 'W',
	// 'o', 'r', 'l', 'd', '!' };

	private static final String webhtml = "<html><head><meta http-equiv=Content-Type content=\"text/html;charset=utf-8\"><title>NettyHelloWorld主页</title></head><body><h1>Hello&nbsp;Netty&nbsp;HTTP&nbsp;Server!</h1><a href=\"http://www.baidu.com/\" target=\"_blank\">中文测试，点我跳到百度</a></body></html>";

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.netty.channel.ChannelHandlerAdapter#channelRead(io.netty.channel.
	 * ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (msg instanceof HttpRequest) {
			HttpRequest req = (HttpRequest) msg;

			if (HttpHeaderUtil.is100ContinueExpected(req)) {
				// 在最新的snapshot的Netty例子中有了个HttpHeaderUtil类，
				// 这样只要是协议中有header的都可以用这个提前做处理。
				// 不过5.0.0Alpha1没有这个类。

				// 更改：因为更新了5.0.0.Alpha2，所以下边的方法要注销
				// if (is100ContinueExpected(req)) {
				ctx.write(new DefaultHttpResponse(HTTP_1_1, CONTINUE));
			}
			boolean isKeepAlive = HttpHeaderUtil.isKeepAlive(req);
			// isKeepAlive(req);旧的

			FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
					OK, Unpooled.wrappedBuffer(webhtml.getBytes()));

			response.headers().set(CONTENT_TYPE, "text/html");
			// 注意，5.0.0.Alpha1是set，新的Alpha2改为了setInt
			response.headers().setInt(CONTENT_LENGTH,
					response.content().readableBytes());

			if (isKeepAlive) {
				ctx.write(response).addListener(ChannelFutureListener.CLOSE);
			} else {
				response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);// Values.KEEP_ALIVE);
				ctx.write(response);
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		logger.error(cause.getMessage());
		ctx.close();
	}

}
