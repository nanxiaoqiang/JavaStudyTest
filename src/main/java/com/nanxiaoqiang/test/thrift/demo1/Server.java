package com.nanxiaoqiang.test.thrift.demo1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * 一个基本的Thrift的例子，Server 服务模型，线程安全
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年3月21日
 *
 */
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
			// 支持的服务模型
			// TSimpleServer – 简单的单线程服务模型，常用于测试
			// TThreadedServer - 多线程服务模型，使用阻塞式IO，每个请求创建一个线程。
			// TThreadPoolServer – 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
			// TNonblockingServer – 多线程服务模型，使用非阻塞式IO（需使用TFramedTransport数据传输方式）
			// 处理大量更新的话，主要是在TThreadedServer和TNonblockingServer中进行选择。
			// TNonblockingServer能够使用少量线程处理大量并发连接，但是延迟较高；
			// TThreadedServer的延迟较低。
			// 实际中，TThreadedServer的吞吐量可能会比TNonblockingServer高，
			// 但是TThreadedServer的CPU占用要比TNonblockingServer高很多。
			TServerSocket serverTransport = new TServerSocket(SERVER_PORT);

			TServer.Args tArgs = new TServer.Args(serverTransport);

			tArgs.processor(tprocessor);

			// 支持的传输格式
			// TBinaryProtocol – 二进制格式.
			// TCompactProtocol – 压缩格式
			// TJSONProtocol – JSON格式
			// TSimpleJSONProtocol –提供JSON只写协议, 生成的文件很容易通过脚本语言解析。
			// TDebugProtocol – 使用易懂的可读的文本格式，以便于debug
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
