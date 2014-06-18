package com.nanxiaoqiang.test.activemq.test1.send;

import java.util.Random;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ActiveMQ发送端Test，自动重连等，调整，只创建一个session，不要每次都创建session。自动发送20个Text
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年5月16日
 * 
 */
public class ActiveMQConnTest {

	private static String MQ_url = "tcp://localhost:61616"; // MQ服务器地址
	private static String QUEUE_NAME = "L7_MCS_polling.queue";
	static ActiveMQConnectionFactory connectionFactory = null;
	static Connection MQ_Conn = null;

	static boolean firstflag = true;

	// Session： 一个发送或接收消息的线程
	static Session session;
	// Destination ：消息的目的地;消息发送给谁.
	static Destination destination;
	// MessageProducer：消息发送者
	static MessageProducer producer;

	private static Logger logger = LogManager.getLogger(ActiveMQConnTest.class
			.getName());

	public static void getMQConnection() throws JMSException {
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, MQ_url);
		logger.info(ActiveMQConnection.DEFAULT_USER + "|"
				+ ActiveMQConnection.DEFAULT_PASSWORD);
		connectionFactory.setUseAsyncSend(true);// 同步发送！？不能异步？？？
		MQ_Conn = (Connection) connectionFactory.createConnection();
		MQ_Conn.setClientID("nanxiaoqiang");
		MQ_Conn.start();
		logger.info("ActiveMQ 连接成功！");
		session = MQ_Conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue(QUEUE_NAME);
		logger.info("连接队列成功:" + QUEUE_NAME);
		// 得到消息生成者【发送者】
		producer = session.createProducer(destination);
		logger.debug("消息生成者创建成功");
		// 设置持久化，此处学习，实际根据项目决定
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		logger.trace("设置发送的消息持久化");

	}

	public ActiveMQConnTest() {
		// TODO Auto-generated constructor stub
	}

	private static void close() {
		if (producer != null) {
			try {
				producer.close();
				producer = null;
			} catch (JMSException e1) {
				e1.printStackTrace();
				logger.info(e1.getMessage());
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
				logger.info(e1.getMessage());
			}
		}
		if (MQ_Conn != null) {
			try {
				MQ_Conn.close();
				MQ_Conn = null;
			} catch (JMSException e1) {
				e1.printStackTrace();
				logger.info(e1.getMessage());
			}
		}
		if (connectionFactory != null) {
			connectionFactory = null;
		}
	}

	public static void main(String[] args) {
		logger.trace("trace初始化");
		logger.debug("debug初始化");
		logger.info("info初始化");
		logger.warn("warn初始化");
		logger.error("error初始化");

		// 程序死循环
		while (true) {
			if (firstflag) {
				// 初始化连接死循环
				while (true) {
					try {
						Thread.sleep(1000);// 休眠1秒
						getMQConnection();

						// 标志位，首次启动读取实时表数据
						firstflag = true;

						break;
					} catch (JMSException e) {
						String errorCode = e.getMessage();
						if (errorCode.endsWith("No route to host: connect")) {
							logger.error("无法连接MQ，请检查网络状态，正在重新建立连接...");
						} else if (errorCode
								.endsWith("Connection refused: connect")) {
							logger.error("连接被拒绝，请检查MQ服务状态，正在重新建立连接...");
						} else {
							logger.error("其他错误，正在重新建立连接..." + errorCode);
							for (int q = 0; q < e.getStackTrace().length; q++) {
								logger.error(e.getStackTrace()[q]);
							}
						}
						close();
					} catch (InterruptedException e) {
						logger.error(e.getMessage());
					} finally {

					}
				}
			}
			// 工程执行死循环
			while (true) {
				firstflag = false;

				// 获取操作连接
				try {
					for (int i = 0; i < 20; i++) {
						// 构造消息
						sendMessage(session, producer);
						session.commit();
						logger.info("session.commit()");
						Thread.sleep(500);
					}
				} catch (JMSException e) {
					close();

					String errorCode = e.getMessage();
					if (errorCode.endsWith("No route to host: connect")) {
						logger.error("无法连接MQ，请检查网络状态，正在重新建立连接...");
					} else if (errorCode
							.endsWith("Connection refused: connect")) {
						logger.error("连接被拒绝，请检查MQ服务状态，正在重新建立连接...");
					} else {
						logger.error("其他错误，正在重新建立连接..." + errorCode);
						for (int q = 0; q < e.getStackTrace().length; q++) {
							logger.error(e.getStackTrace()[q]);
						}
					}
					firstflag = true;
					break;
				} catch (InterruptedException e) {
					logger.error(e.getStackTrace());
				}
			}
		}

	}

	private static void sendMessage(Session session2, MessageProducer producer2)
			throws JMSException {
		// TextMessage msg = session2.createTextMessage("Message:"
		// + System.currentTimeMillis());

		// String[] str = { "1,2", "2,3", "3,4" };
		// ObjectMessage msg = session2.createObjectMessage(str);
		Random r = new Random();
		int rn = r.nextInt(500);
		logger.trace(rn);
		StringBuilder sb = new StringBuilder();
		int[][] a = new int[50000][2];
		for (int i = 0; i < 50000; i++) {
			a[i] = new int[] { i + 701000000, rn + i };
		}
		for (int[] i : a) {
			sb.append(i[0]).append(',').append(i[1]).append(';');
		}
		sb.append(System.currentTimeMillis());
		// System.out.println(ArrayUtils.toString(a));
		TextMessage msg = session2.createTextMessage(sb.toString());
		msg.setJMSCorrelationID("asdasdasdasd");
		// ObjectMessage msg = session2.createObjectMessage(a);
		// MapMessage msg = session2.createMapMessage();
		// msg.
		// msg.setObject("a", a);// ("a", a);
		// try {
		// JMXServiceURL url = new JMXServiceURL(
		// "service:jmx:rmi:///jndi/rmi://localhost:");
		// JMXConnector connector = JMXConnectorFactory.connect(url, null);
		// connector.connect();
		// MBeanServerConnection connection = connector
		// .getMBeanServerConnection();
		// BrokerViewMBean mBean = (BrokerViewMBean)
		// MBeanServerInvocationHandler
		// .newProxyInstance(connection, new ObjectName(""),
		// BrokerViewMBean.class, true);
		// for (ObjectName queueName : mBean.getQueues()) {
		// QueueViewMBean queueMBean = (QueueViewMBean)
		// MBeanServerInvocationHandler
		// .newProxyInstance(connection, queueName,
		// QueueViewMBean.class, true);
		// queueMBean.getQueueSize();
		// queueMBean.removeAllMessageGroups();
		// }
		// } catch (MalformedObjectNameException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		producer2.send(msg);
	}
}
