package com.nanxiaoqiang.test.thrift.demo4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.nanxiaoqiang.test.thrift.demo1.HelloworldService;
import com.nanxiaoqiang.test.thrift.demo1.HelloworldServiceImpl;
import com.nanxiaoqiang.test.thrift.demo2.LongTImeMethodImpl;
import com.nanxiaoqiang.test.thrift.demo2.LongTimeMethod;

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
		logger.info("准备初始化Server，端口号:" + SERVER_PORT);
		// 一个Server注册多个服务
		TMultiplexedProcessor processor = new TMultiplexedProcessor();
		processor.registerProcessor("HelloworldService",
				new HelloworldService.Processor<HelloworldService.Iface>(
						new HelloworldServiceImpl()));
		processor.registerProcessor("LongTimeMethod",
				new LongTimeMethod.Processor<LongTimeMethod.Iface>(
						new LongTImeMethodImpl()));
		try {
			TServerTransport  transport  = new TServerSocket (
					SERVER_PORT);
			TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(
					transport);
			tArgs.processor(processor);
			// Factory方法可以再放两个参数，1格式消息最大长度，一个是container的最大值
			tArgs.protocolFactory(new TCompactProtocol.Factory());
			tArgs.transportFactory(new TFramedTransport.Factory());
			tArgs.minWorkerThreads(10);
			tArgs.maxWorkerThreads(1000);
			TThreadPoolServer server = new TThreadPoolServer(tArgs);

			logger.info("初始化完成.端口号:" + SERVER_PORT);
			server.serve();
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
