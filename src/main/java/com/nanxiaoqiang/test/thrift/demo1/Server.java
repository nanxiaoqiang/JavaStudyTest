package com.nanxiaoqiang.test.thrift.demo1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class Server {
	private static Logger logger = LogManager.getLogger(Server.class.getName());

	public static final int SERVER_PORT = 9999;

	public Server() {
	}

	public void startServer() {
		logger.info("准备启动TProcessor");
		TProcessor tprocessor = new HelloworldService.Processor<HelloworldService.Iface>(
				new HelloworldServiceImpl());

		// 简单的单线程服务模型，一般用于测试
		try {
			TServerSocket serverTransport = new TServerSocket(SERVER_PORT);

			TServer.Args tArgs = new TServer.Args(serverTransport);

			tArgs.processor(tprocessor);
			tArgs.protocolFactory(new TBinaryProtocol.Factory());// 传输格式，二进制
			// tArgs.protocolFactory(new TCompactProtocol.Factory()); // 传输格式，压缩
			// tArgs.protocolFactory(new TJSONProtocol.Factory());// 传输格式，JSON
			TServer server = new TSimpleServer(tArgs);
			server.serve();
			logger.info("启动完成.");

		} catch (TTransportException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.startServer();
	}

}
