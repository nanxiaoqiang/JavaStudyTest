package com.nanxiaoqiang.test.netty.http.sever1;

import static io.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
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
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpServerHandler extends ChannelHandlerAdapter {
	private static Logger logger = LogManager.getLogger(HttpServerHandler.class
			.getName());

	private static final byte[] CONTENT = { 'H', 'e', 'l', 'l', 'o', ',', 'W',
			'o', 'r', 'l', 'd', '!' };

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
			// if(io.netty.handler.codec.http.HttpHeaderUtil)
			if (is100ContinueExpected(req)) {
				ctx.write(new DefaultHttpResponse(HTTP_1_1, CONTINUE));
			}
			boolean isKeepAlive = isKeepAlive(req);

			FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
					OK, Unpooled.wrappedBuffer(CONTENT));

			response.headers().set(CONTENT_TYPE, "text/plain");
			response.headers().set(CONTENT_LENGTH,
					response.content().readableBytes());

			if (isKeepAlive) {
				ctx.write(response).addListener(ChannelFutureListener.CLOSE);
			} else {
				response.headers().set(CONNECTION, Values.KEEP_ALIVE);
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
