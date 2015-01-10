package com.nanxiaoqiang.test.rabbitmq.rpc.test1;

import java.io.IOException;
import java.util.UUID;

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
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年1月10日
 *
 */
public class RpcClient {

	private static Logger logger = LogManager.getLogger(RpcClient.class
			.getName());

	// private static final String RPC_QUEUE_NAME = "rpc_queue";

	private Connection connection;
	private Channel channel;
	private String requestQueueName = "rpc_queue";
	private String replyQueueName;
	private QueueingConsumer consumer;
	private ConnectionFactory factory;

	public RpcClient() throws IOException {
		// 连接RabbitMQ服务器
		factory = new ConnectionFactory();
		factory.setHost("localhost");
		connection = factory.newConnection();
		channel = connection.createChannel();

		consumer = new QueueingConsumer(channel);
		replyQueueName = channel.queueDeclare().getQueue();
		channel.basicConsume(replyQueueName, true, consumer);
	}

	/**
	 * 发起回调
	 * 
	 * @param message
	 * @return
	 * @throws ShutdownSignalException
	 * @throws ConsumerCancelledException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public String call(String message) throws ShutdownSignalException,
			ConsumerCancelledException, InterruptedException, IOException {
		String response = null;
		String corrId = UUID.randomUUID().toString();

		BasicProperties props = new BasicProperties.Builder()
				.correlationId(corrId).replyTo(replyQueueName).build();
		channel.basicPublish("", requestQueueName, props, message.getBytes());
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			if (delivery.getProperties().getCorrelationId().equals(corrId)) {
				response = new String(delivery.getBody(), "UTF-8");
				break;
			}
		}
		return response;
	}

	/**
	 * 关闭方法
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		connection.close();
	}

	public static void main(String[] args) {
		RpcClient client = null;
		String response = null;
		try {
			client = new RpcClient();
			String send = "test rpc";
			for (int i = 0; i < 10; i++) {
				logger.info("发送：" + send + i);
				response = client.call(send + i);
				logger.info("call is : " + send + i + ",response is: "
						+ response);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (ShutdownSignalException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (ConsumerCancelledException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

}
