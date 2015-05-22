/*
 * Copyright 2013-2018 Lilinfeng.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nanxiaoqiang.test.netty.protocol.demo1.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.nanxiaoqiang.test.netty.protocol.demo1.struct.Header;
import com.nanxiaoqiang.test.netty.protocol.demo1.struct.NettyMessage;

/**
 * @author Lilinfeng
 * @date 2014年3月15日
 * @version 1.0
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

	MarshallingDecoder marshallingDecoder;

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
	public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset,
			int lengthFieldLength) throws IOException {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
		marshallingDecoder = new MarshallingDecoder();
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in)
			throws Exception {
		ByteBuf frame = (ByteBuf) super.decode(ctx, in);
		if (frame == null) {
			return null;// 空的就不管了
		}

		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setCrcCode(frame.readInt());
		header.setLength(frame.readInt());
		header.setSessionID(frame.readLong());
		header.setType(frame.readByte());
		header.setPriority(frame.readByte());

		int size = frame.readInt();// 读取Header中的附件的大小
		if (size > 0) {
			Map<String, Object> attch = new HashMap<String, Object>(size);
			int keySize = 0;
			byte[] keyArray = null;
			String key = null;
			for (int i = 0; i < size; i++) {
				keySize = frame.readInt();
				keyArray = new byte[keySize];
				frame.readBytes(keyArray);
				key = new String(keyArray, "UTF-8");// key是UTF-8的String
				attch.put(key, marshallingDecoder.decode(frame));
			}
			keyArray = null;
			key = null;
			header.setAttachment(attch);
		}
		// Header End
		if (frame.readableBytes() > 4) {
			message.setBody(marshallingDecoder.decode(frame));// 如果包的长度大于4，那么解Data
		}
		message.setHeader(header);
		return message;
	}
}
