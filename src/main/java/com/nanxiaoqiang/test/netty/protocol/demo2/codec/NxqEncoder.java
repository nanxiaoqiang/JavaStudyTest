package com.nanxiaoqiang.test.netty.protocol.demo2.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nanxiaoqiang.test.apache.avro.java.User;
import com.nanxiaoqiang.test.netty.protocol.demo2.msg.BaseMessage;

public class NxqEncoder extends MessageToByteEncoder<BaseMessage> {

	private static Logger LOGGER = LogManager.getLogger(NxqEncoder.class
			.getName());

	@Override
	protected void encode(ChannelHandlerContext ctx, BaseMessage msg,
			ByteBuf out) throws Exception {
		if (msg == null || msg.getHeader() == null)
			throw new Exception("The encode message is null");
		LOGGER.debug(msg);
		out.writeByte(msg.getHeader().getSystemId());
		out.writeShort(msg.getHeader().getLength());
		out.writeByte(msg.getHeader().getMultiFlag());
		out.writeShort(msg.getHeader().getMessageLength());
		out.writeInt(msg.getHeader().getTime());
		out.writeShort(msg.getHeader().getVersion());
		out.writeShort(msg.getHeader().getMsgId());

		Object obj = msg.getBody();
		if (obj instanceof User) {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(bout,
					null);
			DatumWriter<User> writer = new SpecificDatumWriter<User>(
					User.getClassSchema());

			writer.write((User) obj, encoder);
			encoder.flush();
			bout.close();
			byte[] serializedBytes = bout.toByteArray();
			out.writeBytes(serializedBytes);
			LOGGER.debug("xxx:" + serializedBytes.length);
		}
		LOGGER.debug("消息长度:" + (out.readableBytes() - 14));
		// 写入长度
		out.setShort(4, out.readableBytes() - 14);
	}

}
