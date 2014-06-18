package com.nanxiaoqiang.test.storm.server.jms.activemq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.transport.DefaultTransportListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

/**
 * 暂时写的通用的ActiveMQ的客户端，暂时只是用于Spout入口的读取用。
 * 
 * @author nanxiaoqiang
 * 
 * @version v0.1.1 2014年6月18日
 * 
 */
public class ActiveMqClient {

	private static Logger logger = LogManager.getLogger(ActiveMqClient.class
			.getName());
	/**
	 * ActiveMQ URL
	 */
	private String url = null;

	/**
	 * 队列名称
	 */
	private String queue = null;

	/**
	 * 当前线程名称
	 */
	private String threadName = null;

	/**
	 * ActiveMQConnectionFactory
	 */
	private ActiveMQConnectionFactory connectionFactory = null;

	/**
	 * Connection
	 */
	private Connection connection = null;

	/**
	 * Session： 一个发送或接收消息的线程
	 */
	private Session session = null;

	/**
	 * Destination ：消息的目的地;消息发送给谁.
	 */
	private Destination destination = null;

	/**
	 * 消息发送者
	 */
	private MessageConsumer consumer = null;

	public ActiveMqClient(final String url, final String queue,
			final String usr, final String pwd, final String threadName)
			throws JMSException {
		this.url = url;
		this.queue = queue;
		this.threadName = threadName;
		Thread.currentThread().setName(threadName);
		reconn(usr, pwd);
	}

	/**
	 * 打开ActiveMQ连接
	 * 
	 * @param usr
	 * @param pwd
	 * @throws JMSException
	 */
	private void reconn(String usr, String pwd) throws JMSException {
		connectionFactory = new ActiveMQConnectionFactory(
				StringUtils.isBlank(usr) ? ActiveMQConnection.DEFAULT_USER
						: usr,
				StringUtils.isBlank(pwd) ? ActiveMQConnection.DEFAULT_PASSWORD
						: pwd, url);
		connectionFactory.setUseAsyncSend(true);
		connectionFactory.setTransportListener(new DefaultTransportListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.apache.activemq.transport.DefaultTransportListener#
			 * transportInterupted()
			 */
			@Override
			public void transportInterupted() {
				// 采用了ActiveMQ的自动连接机制。
				// 在出错无法连接MQ Server的时候不会抛出异常。
				// consumer将持续去取数据，不过都是null
				logger.error("ActiveMQ 连接失败。发生transportInterupted 在 "
						+ DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.apache.activemq.transport.DefaultTransportListener#
			 * transportResumed()
			 */
			@Override
			public void transportResumed() {
				// 采用ActiveMQ内置方式的时候，如果能够自动重新连接MQ的话会自动的记录数据。
				logger.error("ActiveMQ重新连接。transportResumed于"
						+ DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
			}
		});
		connection = (Connection) connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(Boolean.TRUE,
				Session.AUTO_ACKNOWLEDGE);
		// 获取session
		destination = session.createQueue(this.queue);

		// 得到一个消息接收者
		consumer = session.createConsumer(destination);
		logger.info("connection success!ActiveMQ " + this.url + "\tqueue:"
				+ this.queue + "连接成功.");
	}

	/**
	 * 接收数据的方法，暂时的每次只接收一条数据。<br/>
	 * 接收的数据有两种：<br/>
	 * 
	 * <pre>
	 * 一、Text
	 * 命令。命令有以下几种：
	 *     1)spout接收用，用于将spout的已知的有value的内存实时数据向所有下级节点发送。会有大批量的数据更新。
	 *     2)spout接收，但是只向其中的一个节点发送全内存数据(暂时不会).
	 *     3)spout接收，向下方的全部节点发送命令。如：重新读取内存数据并初始化。
	 * 二、Object
	 * 数据。目前是寄存器数组
	 *     1)spout接收，ISCS接口的全部寄存器数据。
	 * </pre>
	 * 
	 * <del>ver 0.1 返回一个Object对象</del><br/>
	 * <del>ver 0.1.1 估计要将内存值传到此方法中。然后返回一个StormMqObject</del> <br/>
	 * 在版本ver0.1.2 改为返回一个Message对象.
	 * 
	 * @return StormMqObject
	 */
	public Message receive() {
		logger.debug("线程" + this.threadName + "调用receive.");
		// Object obj = null;
		Message message = null;
		try {
			message = this.consumer.receive(1000);
			if (message != null) {
				// if (message instanceof TextMessage) {
				// // 命令
				// logger.debug("TextMessage：JMSCorrelationID:"
				// + message.getJMSCorrelationID());
				// obj = ((TextMessage) message).getText();
				// } else if (message instanceof ObjectMessage) {
				// // 寄存器对象
				// logger.debug("ObjectMessage：JMSCorrelationID:"
				// + message.getJMSCorrelationID());
				// obj = ((ObjectMessage) message).getObject();
				// }
				this.session.commit();
				// return obj;
			}
		} catch (JMSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return message;
		}
		// return obj;
		return message;
	}

	public void close() {
		if (consumer != null) {
			try {
				consumer.close();
				consumer = null;
			} catch (JMSException e) {
				logger.warn(e.getMessage());
				e.printStackTrace();
			}
		}
		if (destination != null) {
			destination = null;
		}
		if (session != null) {
			try {
				session.close();
				session = null;
			} catch (JMSException e1) {
				e1.printStackTrace();
				logger.warn(e1.getMessage());
			}
		}
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (JMSException e1) {
				e1.printStackTrace();
				logger.warn(e1.getMessage());
			}
		}
		if (connectionFactory != null) {
			connectionFactory = null;
		}
	}

}
