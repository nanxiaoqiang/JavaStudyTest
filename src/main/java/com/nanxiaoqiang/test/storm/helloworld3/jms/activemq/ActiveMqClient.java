package com.nanxiaoqiang.test.storm.helloworld3.jms.activemq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.transport.DefaultTransportListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

/**
 * 暂时写的通用的ActiveMQ的客户端
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月18日
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

	public ActiveMqClient(String url, String queue, String usr, String pwd,
			String threadName) throws JMSException {
		this.url = url;
		this.queue = queue;
		this.threadName = threadName;
		Thread.currentThread().setName(threadName);
		reconn(usr, pwd);
	}

	private void reconn(String usr, String pwd) throws JMSException {
		connectionFactory = new ActiveMQConnectionFactory(
				StringUtils.isBlank(usr) ? ActiveMQConnection.DEFAULT_USER
						: usr,
				StringUtils.isBlank(pwd) ? ActiveMQConnection.DEFAULT_PASSWORD
						: pwd, url);
		connectionFactory.setUseAsyncSend(true);// 同步发送！？不能异步？？？
		connectionFactory.setTransportListener(new DefaultTransportListener() {
			@Override
			public void transportInterupted() {
				logger.error("ActiveMQ 连接失败。发生transportInterupted 在 "
						+ DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
			}

			@Override
			public void transportResumed() {
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
	 * 接收
	 * 
	 * @param type
	 * @param msg
	 * @return
	 */
	public Object receive() {
		logger.debug("线程" + this.threadName + "调用receive.");
		Object obj = null;
		try {
			Message message = this.consumer.receive(1000);
			if (message != null) {
				if (message instanceof TextMessage) {
					// 文字JMS
					logger.debug("TextMessage：JMSCorrelationID:"
							+ message.getJMSCorrelationID());
					obj = ((TextMessage) message).getText();
				} else if (message instanceof ObjectMessage) {
					// 对象JMS
					logger.debug("ObjectMessage：JMSCorrelationID:"
							+ message.getJMSCorrelationID());
					obj = ((ObjectMessage) message).getObject();
				}
				this.session.commit();
				return obj;
			}
		} catch (JMSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
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
