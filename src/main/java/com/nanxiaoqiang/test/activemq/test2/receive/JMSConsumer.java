package com.nanxiaoqiang.test.activemq.test2.receive;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 读取的实现
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年7月1日
 */
public class JMSConsumer implements ExceptionListener {
	private static Logger logger = LogManager.getLogger(JMSConsumer.class
			.getName());

	public final static int DEFAULT_QUEUE_PREFETCH = 10;
	// 队列预取策略
	private int queuePrefetch = DEFAULT_QUEUE_PREFETCH;

	private String brokerUrl;

	private String userName;

	private String password;

	private MessageListener messageListener;

	private Connection connection;

	private Session session;
	// 队列名
	private String queue;

	/**
	 * 执行消息获取的操作
	 * 
	 * @throws Exception
	 */
	public void start() throws Exception {
		logger.info("start");
		// ActiveMQ的连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				StringUtils.isBlank(this.userName) ? ActiveMQConnection.DEFAULT_USER
						: this.userName,
				StringUtils.isBlank(this.password) ? ActiveMQConnection.DEFAULT_PASSWORD
						: this.password, this.brokerUrl);
		connection = connectionFactory.createConnection();
		// activeMQ预取策略
		ActiveMQPrefetchPolicy prefetchPolicy = new ActiveMQPrefetchPolicy();
		prefetchPolicy.setQueuePrefetch(queuePrefetch);
		((ActiveMQConnection) connection).setPrefetchPolicy(prefetchPolicy);
		connection.setExceptionListener(this);
		connection.start();
		// 会话采用事务级别，消息到达机制使用自动通知机制
		session = connection.createSession(Boolean.TRUE,
				Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(this.queue);
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener(this.messageListener);
	}

	/**
	 * 关闭连接
	 */
	public void shutdown() {
		logger.info("shutdown");
		try {
			if (session != null) {
				session.close();
				session = null;
			}
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onException(JMSException e) {
		logger.error("onException" + e.getMessage());
		e.printStackTrace();
	}

	public String getBrokerUrl() {
		return brokerUrl;
	}

	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public MessageListener getMessageListener() {
		return messageListener;
	}

	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	public int getQueuePrefetch() {
		return queuePrefetch;
	}

	public void setQueuePrefetch(int queuePrefetch) {
		this.queuePrefetch = queuePrefetch;
	}

}
