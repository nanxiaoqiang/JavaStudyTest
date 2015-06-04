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
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;
import java.util.Map;

import com.nanxiaoqiang.test.netty.protocol.demo1.struct.NettyMessage;

/**
 * @author Lilinfeng
 * @date 2014年3月14日
 * @version 1.0
 */
public final class NettyMessageEncoder extends
	MessageToByteEncoder<NettyMessage> {

    MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() throws IOException {
	this.marshallingEncoder = new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg,
	    ByteBuf sendBuf) throws Exception {
	if (msg == null || msg.getHeader() == null)
	    throw new Exception("The encode message is null");
	// Header Start
	sendBuf.writeInt((msg.getHeader().getCrcCode()));// crc
	sendBuf.writeInt((msg.getHeader().getLength()));// 长度?如何得知
	sendBuf.writeLong((msg.getHeader().getSessionID()));
	sendBuf.writeByte((msg.getHeader().getType()));
	sendBuf.writeByte((msg.getHeader().getPriority()));
	sendBuf.writeInt((msg.getHeader().getAttachment().size()));
	String key = null;
	byte[] keyArray = null;
	Object value = null;
	for (Map.Entry<String, Object> param : msg.getHeader().getAttachment()
		.entrySet()) {
	    key = param.getKey();
	    keyArray = key.getBytes("UTF-8");
	    sendBuf.writeInt(keyArray.length);
	    sendBuf.writeBytes(keyArray);
	    value = param.getValue();
	    marshallingEncoder.encode(value, sendBuf);
	}
	key = null;
	keyArray = null;
	value = null;
	// Head End
	
	// Body Start
	if (msg.getBody() != null) {// 如果有消息
	    marshallingEncoder.encode(msg.getBody(), sendBuf);
	} else
		// 如果没消息
	    sendBuf.writeInt(0);// 加一个长度为0的int
		sendBuf.setInt(4, sendBuf.readableBytes() - 8);// 设置长度，0~3为CRC，4~7是长度，总长度减去前8位
    }
    // Body End
}
