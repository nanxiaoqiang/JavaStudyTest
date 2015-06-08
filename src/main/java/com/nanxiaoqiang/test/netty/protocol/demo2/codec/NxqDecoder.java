package com.nanxiaoqiang.test.netty.protocol.demo2.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.IOException;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nanxiaoqiang.test.apache.avro.java.User;
import com.nanxiaoqiang.test.netty.protocol.demo2.msg.BaseMessage;
import com.nanxiaoqiang.test.netty.protocol.demo2.msg.Header;

public class NxqDecoder extends LengthFieldBasedFrameDecoder {

	private static Logger LOGGER = LogManager.getLogger(NxqDecoder.class
			.getName());

	/**
	 * 
	 * @param maxFrameLength
	 *            1024 * 1024 这个定义最大帧的长度
	 * @param lengthFieldOffset
	 *            4 长度属性的起始指针(偏移量)
	 * @param lengthFieldLength
	 *            4 长度属性的长度，即存放数据包长度的变量的的字节所占的长度
	 * @throws IOException
	 */
	public NxqDecoder(int maxFrameLength, int lengthFieldOffset,
			int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip)
			throws IOException {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength,
				lengthAdjustment, initialBytesToStrip);
		LOGGER.debug(new StringBuffer("ServerHandler Constructor|:")
				.append(maxFrameLength).append("|lengthFieldOffset:")
				.append(lengthFieldOffset).append("|lengthFieldLength:")
				.append(lengthFieldLength).append("|lengthAdjustment:")
				.append(lengthAdjustment).append("|initialBytesToStrip:")
				.append(initialBytesToStrip));
	}

	public NxqDecoder(int maxFrameLength, int lengthFieldOffset,
			int lengthFieldLength, int lengthAdjustment) throws IOException {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength,
				lengthAdjustment, 0);
		LOGGER.debug(new StringBuffer("ServerHandler Constructor|:")
				.append(maxFrameLength).append("|lengthFieldOffset:")
				.append(lengthFieldOffset).append("|lengthFieldLength:")
				.append(lengthFieldLength).append("|lengthAdjustment:")
				.append(lengthAdjustment).append("|initialBytesToStrip:0"));
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in)
			throws Exception {
		ByteBuf frame = (ByteBuf) super.decode(ctx, in);
		LOGGER.debug("frame:" + frame);
		if (frame == null) {
			return null;// 空的就不管了
		}

		BaseMessage bmsg = new BaseMessage();
		// Head组装
		Header header = new Header();
		header.setSystemId(frame.readByte());
		header.setLength(frame.readShort());
		header.setMultiFlag(frame.readByte());
		header.setMessageLength(frame.readShort());
		header.setTime(frame.readInt());
		header.setVersion(frame.readShort());
		header.setMsgId(frame.readShort());

		// bmsg.setData(frame);
		bmsg.setHeader(header);

		LOGGER.debug(header);
		if (header.getMessageLength() > 0) {
			byte[] b = new byte[header.getMessageLength()];
			frame.readBytes(b);
			// ByteBuf buf = in.slice(frame.readerIndex(),
			// header.getLength());//
			// 读取Data size大小的数据
			DatumReader<User> reader = new SpecificDatumReader<User>(User.class);
			Decoder decoder = DecoderFactory.get().binaryDecoder(b, null);
			User result = reader.read(null, decoder);
			// in.readerIndex(in.readerIndex() + header.getLength());// 移动指针到结尾
			bmsg.setBody(result);
		}
		LOGGER.debug(bmsg);
		return bmsg;// super.decode(ctx, in);
	}
}
