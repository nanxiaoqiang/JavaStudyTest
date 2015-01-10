package com.nanxiaoqiang.test.rabbitmq.rpc.test1;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * RabbitMQ的RPC调用，Client发送10个请求，Server得到后做处理然后返回。想法应当是发送X10，然后Server谁处理好谁返回。
 * 但是貌似写的有问题，现在成了线性的了。
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年1月10日
 *
 */
public class RpcServer {

	private static Logger logger = LogManager.getLogger(RpcServer.class
			.getName());

	private static final String RPC_QUEUE_NAME = "rpc_queue";

	Connection connection = null;
	Channel channel = null;
	ConnectionFactory factory = null;

	// 处理相关的
	ExecutorService threadPool = null;

	public RpcServer() throws IOException {
		threadPool = Executors.newFixedThreadPool(20);
		// 连接RabbitMQ服务端
		factory = new ConnectionFactory();
		factory.setHost("localhost");
		connection = factory.newConnection();
		channel = connection.createChannel();
	}

	public void start() throws IOException, ShutdownSignalException,
			ConsumerCancelledException, InterruptedException {
		// 连接队列
		channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
		channel.basicQos(1);
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
		logger.info(" [x] Awaiting RPC requests");
		logger.info("开始等待回调请求");
		while (true) {
			String response = null;
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			BasicProperties props = delivery.getProperties();
			BasicProperties replyProps = new BasicProperties.Builder()
					.correlationId(props.getCorrelationId()).build();
			try {
				// 得到请求的String message
				String message = new String(delivery.getBody(), "UTF-8");
				logger.info("得到请求的message: " + message);
				// 模拟处理事件
				MyCallable a = new MyCallable(message);
				Future<String> future = threadPool.submit(a);
				response = future.get();
				logger.info("得到回调:" + response);
			} catch (Exception e) {
				logger.error("出错啦！回调的response置为空字符串");
				logger.error(e.getMessage());
				response = "";
			} finally {
				channel.basicPublish("", props.getReplyTo(), replyProps,
						response.getBytes("UTF-8"));
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			}
		}
	}

	public void close() throws IOException {
		if (connection != null) {
			connection.close();
		}
	}

	public static void main(String[] argv) {
		RpcServer server = null;
		try {
			server = new RpcServer();
			server.start();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

}

/**
 * Server端的回调
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年1月10日
 *
 */
class MyCallable implements Callable<String> {

	private static Logger logger = LogManager.getLogger(MyCallable.class
			.getName());

	private String str;

	public MyCallable(String str) {
		this.str = str;
	}

	@Override
	public String call() throws Exception {
		int i = RandomUtils.nextInt(2, 20);
		TimeUnit.SECONDS.sleep(i);
		logger.info(new StringBuffer().append("服务端线程:")
				.append(Thread.currentThread().getName()).append("得到消息:")
				.append(str).append(",然后假装在做些事情,并且做了").append(i).append("秒")
				.toString());
		return new StringBuffer().append(Thread.currentThread().getName())
				.append("|").append(str).append("|").append(i).toString();
	}
}
