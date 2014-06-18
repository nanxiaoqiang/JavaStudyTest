package com.nanxiaoqiang.test.activemq.test1.receive;

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
 * 简单些的ActiveMQ的Client
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月18日
 * 
 */
public class App2 {
	private static Logger logger = LogManager.getLogger(App2.class.getName());
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

	/**
	 * 
	 * @param url
	 * @param queue
	 * @param usr
	 * @param pwd
	 * @param threadName
	 * @throws JMSException
	 */
	public App2(String url, String queue, String usr, String pwd,
			String threadName) throws JMSException {
		this.url = url;
		this.queue = queue;
		this.threadName = threadName;
		Thread.currentThread().setName(threadName);
		reconn(usr, pwd);
	}

	private void reconn(String usr, String pwd) throws JMSException {
		logger.debug("reconn");
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
		// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
		destination = session.createQueue(this.queue);
		// 得到消息生成者【发送者】
		// producer = session.createProducer(destination);

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
	 * @throws JMSException
	 */
	public Object receive() throws JMSException {
		logger.debug("线程" + this.threadName + "调用receive");
		Message message = this.consumer.receive(1000);
		if (message != null) {
			if (message instanceof TextMessage) {
				// 文字JMS
				logger.debug("JMSCorrelationID:"
						+ message.getJMSCorrelationID());
				return ((TextMessage) message).getText();
			} else if (message instanceof ObjectMessage) {
				// 对象JMS
				logger.debug("JMSCorrelationID:"
						+ message.getJMSCorrelationID());
				return ((ObjectMessage) message).getObject();
			}
		}
		return null;
	}

	public static void main(String[] args) throws JMSException {
		App2 app = new App2("failover:(tcp://localhost:61616)",
				"L7_MCS_polling.queue", null, null,
				"TestNamedThreadActiveMQClient");
		Object obj = app.receive();
		logger.info(obj.getClass().getName() + " | " + obj.toString());
		app.close();
	}

	private void close() {
		logger.debug("close");
		if (consumer != null) {
			try {
				consumer.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				consumer = null;
			}
		}
		if (session != null) {
			try {
				session.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				session = null;
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				connection = null;
			}
		}
	}
}
