package com.nanxiaoqiang.test.thrift.demo1;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class Client {

	private static Logger logger = LogManager.getLogger(Client.class.getName());

	public static final String SERVER_IP = "localhost";
	public static final int SERVER_PORT = 9999;
	public static final int TIMEOUT = 30000;// 超时

	public Client() {
	}

	public void startclient() {
		// 支持的通信方式(数据传输方式)（Transport）
		// TFileTransport：文件（日志）传输类，允许client将文件传给server，允许server将收到的数据写到文件中。
		// THttpTransport：采用Http传输协议进行数据传输
		// TSocket：采用TCP Socket进行数据传输
		// TZlibTransport：压缩后对数据进行传输，或者将收到的数据解压
		// 下面几个类主要是对上面几个类地装饰（采用了装饰模式），以提高传输效率。
		// TBufferedTransport：对某个Transport对象操作的数据进行buffer，即从buffer中读取数据进行传输，或者将数据直接写入buffer
		// TFramedTransport：以frame为单位进行传输，非阻塞式服务中使用。同TBufferedTransport类似，也会对相关数据进行buffer，同时，它支持定长数据发送和接收。
		// TMemoryBuffer：从一个缓冲区中读写数据
		TTransport transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
		// 协议要和服务端一致
		TProtocol protocol = new TBinaryProtocol(transport);
		// TProtocol protocol = new TCompactProtocol(transport);
		// TProtocol protocol = new TJSONProtocol(transport);
		HelloworldService.Client client = new HelloworldService.Client(protocol);

		try {
			transport.open();

			// 测试1
			Helloworld h1 = null;
			boolean b1 = client.insertHelloworld(h1);
			logger.info("传输一个null值进行insertHelloworld方法，返回结果:" + b1);
			TimeUnit.SECONDS.sleep(1);

			// 测试2
			Helloworld h2 = new Helloworld();
			h2.setId(1);
			h2.setName("Hello World!Thrift!");
			boolean b2 = client.insertHelloworld(h2);
			logger.info("传输一个Helloworld对象进行insertHelloworld方法，返回结果:" + b2);
			TimeUnit.SECONDS.sleep(1);

			// 测试3
			Helloworld h3 = client.getHelloworld(0);
			logger.info("通过方法getHelloworld得到id为0的Helloworld对象，返回结果:"
					+ ToStringBuilder.reflectionToString(h3,
							ToStringStyle.MULTI_LINE_STYLE));
			TimeUnit.SECONDS.sleep(1);

			// 测试4
			Helloworld h4 = client.getHelloworld(1);
			logger.info("通过方法getHelloworld得到id为1的Helloworld对象，返回结果:"
					+ ToStringBuilder.reflectionToString(h4,
							ToStringStyle.MULTI_LINE_STYLE));
			TimeUnit.SECONDS.sleep(1);

			// 测试5
			// 此段会报错！不能返回null对象
			// 报错内容：org.apache.thrift.TApplicationException: getHelloworld failed: unknown result
			// Helloworld h5 = client.getHelloworld(2);
			// logger.info("通过方法getHelloworld得到id为2的Helloworld对象，返回结果:"
			// + ToStringBuilder.reflectionToString(h5,
			// ToStringStyle.MULTI_LINE_STYLE));
			// TimeUnit.SECONDS.sleep(1);

			// 测试6
			boolean b6 = client.removeHelloworld(0);
			logger.info("通过方法removeHelloworld删除id为0的Helloworld对象，返回结果:" + b6);
			TimeUnit.SECONDS.sleep(1);

			// 测试7
			boolean b7 = client.removeHelloworld(1);
			logger.info("通过方法removeHelloworld删除id为1的Helloworld对象，返回结果:" + b7);
			TimeUnit.SECONDS.sleep(1);

			// 测试8
			boolean b8 = client.removeHelloworld(2);
			logger.info("通过方法removeHelloworld删除id为2的Helloworld对象，返回结果:" + b8);
			TimeUnit.SECONDS.sleep(1);
			
		} catch (TTransportException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (TException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (transport != null)
				transport.close();
			logger.info("transport关闭");
		}

	}

	public static void main(String[] args) {
		Client c = new Client();
		c.startclient();
	}

}
