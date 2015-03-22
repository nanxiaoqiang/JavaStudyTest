package com.nanxiaoqiang.test.thrift.demo2;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.nanxiaoqiang.test.thrift.demo1.Helloworld;
import com.nanxiaoqiang.test.thrift.demo1.HelloworldService;

public class Client implements Runnable {

	private static Logger logger = LogManager.getLogger(Client.class.getName());

	public static final String SERVER_IP = "localhost";
	public static final int SERVER_PORT = 9999;
	public static final int TIMEOUT = 30000;// 超时

	public Client() {
	}

	public void startclient() {
		TTransport transport = new TFramedTransport(new TSocket(SERVER_IP,
				SERVER_PORT, TIMEOUT));
		TProtocol protocol = new TCompactProtocol(transport);
		// 两个client的调用
		TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,
				"HelloworldService");
		HelloworldService.Client service1 = new HelloworldService.Client(mp1);
		TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol,
				"LongTimeMethod");
		LongTimeMethod.Client service2 = new LongTimeMethod.Client(mp2);
		try {
			transport.open();

			// 测试1，第一个client的测试
			Helloworld h1 = service1.getHelloworld(0);
			logger.info("通过方法getHelloworld得到id为0的Helloworld对象，返回结果:"
					+ ToStringBuilder.reflectionToString(h1,
							ToStringStyle.MULTI_LINE_STYLE));
			TimeUnit.SECONDS.sleep(1);

			// 测试2，第二个client的测试
			LocalTime starttime = LocalTime.now();
			service2.noResult();
			LocalTime endtime = LocalTime.now();
			long between = ChronoUnit.SECONDS.between(starttime, endtime);
			logger.info("noResult:从" + starttime + "到" + endtime + "总共执行了"
					+ between + "秒。");

			// 测试3，第二个client的测试
			Tweet t1 = new Tweet(3, "hehe Meow~ ＞▽＜",
					System.currentTimeMillis());
			t1.setLauague("english");
			LocalTime starttime2 = LocalTime.now();
			service2.showObj(t1);
			LocalTime endtime2 = LocalTime.now();
			long between2 = ChronoUnit.SECONDS.between(starttime2, endtime2);
			logger.info("noResult:从" + starttime2 + "到" + endtime2 + "总共执行了"
					+ between2 + "秒。");

			// 测试4
			List<Tweet> list = service2.getTimeLine(System.currentTimeMillis());
			logger.info("list sizze is " + list.size());
			for (Tweet t : list)
				logger.info(ToStringBuilder.reflectionToString(t,
						ToStringStyle.MULTI_LINE_STYLE));

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
			transport.close();
		}
	}

	public static void main(String[] args) {
		Client c1 = new Client();
		new Thread(c1, "测试A").start();
		new Thread(c1, "测试B").start();
		new Thread(c1, "测试C").start();
		// 上边的代码为一个后果是报错报错报错啊啊啊啊啊！
		// TNonblockingServer采用NIO的模式, 借助Channel/Selector机制, 采用IO事件模型来处理.
		// 源代码中select代码里对accept/read/write等IO事件进行监控和处理,
		// 唯一可惜的这个单线程处理. 当遇到handler里有阻塞的操作时, 会导致整个服务被阻塞住.
	}

	@Override
	public void run() {
		startclient();
	}

}
