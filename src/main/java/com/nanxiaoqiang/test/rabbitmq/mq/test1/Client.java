package com.nanxiaoqiang.test.rabbitmq.mq.test1;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Client {

	private static Logger logger = LogManager.getLogger(Client.class.getName());

	private final static String QUEUE_NAME = "hello";

	public Client() {
	}

	public static void main(String[] args) {
		ConnectionFactory factory = null;
		Connection connection = null;
		Channel channel = null;

		try {
			// 连接Server
			factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection();
			channel = connection.createChannel();

			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			logger.info(" [*] Waiting for messages. To exit press CTRL+C");
			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(QUEUE_NAME, true, consumer);

			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				logger.info(" [x] Received '" + message + "'");
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
		}
	}
}
