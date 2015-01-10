package com.nanxiaoqiang.test.rabbitmq.mq.test1;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Server {
	private static Logger logger = LogManager.getLogger(Server.class.getName());

	private final static String QUEUE_NAME = "hello";

	public Server() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		ConnectionFactory factory = null;
		Connection connection = null;
		Channel channel = null;

		try {
			// 连接一个RabbitServer
			factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection();
			channel = connection.createChannel();

			// 参数：
			// QUEUE_NAME 队列名称
			// durable 持久
			// exclusive 排外
			// autoDelete
			// Map<String, Object> arguments ...
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = "Hello World!";
			// 参数：
			// exchange
			// routingKey 队列名称
			// BasicProperties props
			// byte[] body
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			logger.info(" [x] Sent '" + message + "'");
			channel.close();
			connection.close();
		} catch (IOException e) {
			logger.error("IO错误");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

}
