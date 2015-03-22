package com.nanxiaoqiang.test.thrift.demo2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.nanxiaoqiang.test.thrift.demo1.HelloworldService;
import com.nanxiaoqiang.test.thrift.demo1.HelloworldServiceImpl;

/**
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年3月22日
 *
 */
public class Server {

	private static Logger logger = LogManager.getLogger(Server.class.getName());

	public static final int SERVER_PORT = 9999;

	public Server() {
	}

	public void startServer() {
		logger.info("准备启动TProcessor");
		// 一个Server注册多个服务
		TMultiplexedProcessor processor = new TMultiplexedProcessor();
		processor.registerProcessor("HelloworldService",
				new HelloworldService.Processor<HelloworldService.Iface>(
						new HelloworldServiceImpl()));
		processor.registerProcessor("LongTimeMethod",
				new LongTimeMethod.Processor<LongTimeMethod.Iface>(
						new LongTImeMethodImpl()));
		try {
			TNonblockingServerTransport nioTransport = new TNonblockingServerSocket(
					SERVER_PORT);
			TNonblockingServer.Args tArgs = new TNonblockingServer.Args(
					nioTransport);
			tArgs.processor(processor);
			// Factory方法可以再放两个参数，1格式消息最大长度，一个是container的最大值
			tArgs.protocolFactory(new TCompactProtocol.Factory());
			// NIO的哦！！！
			TNonblockingServer server = new TNonblockingServer(tArgs);
			server.serve();
			logger.info("启动完成.");
		} catch (TTransportException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server s = new Server();
		s.startServer();
	}

}
