package com.nanxiaoqiang.test.activemq.test1.receive;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.transport.DefaultTransportListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

/**
 * 测试用：ActiveMQ的自动重连。<br/>
 * 连接方式要failover:(tcp://localhost:61616)XXXX的方式
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月18日
 * 
 */
public class App {
	private static String MQ_url = "failover:(tcp://localhost:61616)"; // MQ服务器地址
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
	// 消息接收者
	static MessageConsumer consumer = null;

	private static Logger logger = LogManager.getLogger(App.class.getName());

	// static DateTime expTime;
	//
	// static DateTime interuptedTime;
	//
	// static DateTime normalTime;

	public App() {

	}

	public static void main(String[] args) {
		logger.info("开始启动程序");
		try {
			getMQConnection();
			while (true) {
				Message message = consumer.receive(1000);
				logger.info("Message:" + message == null);
				if (message != null) {
					logger.info(message.getClass().getName());
				}
			}
		} catch (JMSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private static void getMQConnection() throws JMSException {
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, MQ_url);
		connectionFactory.setUseAsyncSend(true);// 同步发送！？不能异步？？？
		connectionFactory.setTransportListener(new DefaultTransportListener() {

			@Override
			public void onCommand(Object command) {
				logger.info("onCommand-->会执行三次。暂时不知道干什么的");
			}

			@Override
			public void onException(IOException error) {
				logger.info("onException!");
				// expTime = DateTime.now();
				// logger.info("onException-->发生异常，无法连接ActiveMQ at "
				// + expTime.toString("yyyy-MM-dd HH:mm:ss"));
			}

			@Override
			public void transportInterupted() {
				logger.error("ActiveMQ 连接失败。发生transportInterupted 在 "
						+ DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
				// synchronized (this) {
				// interuptedTime = DateTime.now();
				// logger.info("===>> 连接断开 at "
				// + expTime.toString("yyyy-MM-dd HH:mm:ss"));
				// }
			}

			@Override
			public void transportResumed() {
				logger.error("ActiveMQ重新连接。transportResumed于"
						+ DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
				// normalTime = DateTime.now();
				// logger.info("===>> 重连 at "
				// + normalTime.toString("yyyy-MM-dd HH:mm:ss")
				// + ". 中断时间为"
				// + Days.daysBetween(normalTime, interuptedTime)
				// .getDays()
				// + "天"
				// + Hours.hoursBetween(normalTime, interuptedTime)
				// .getHours()
				// + "小时"
				// + Minutes.minutesBetween(normalTime, interuptedTime)
				// .getMinutes()
				// + "分钟"
				// + Seconds.secondsBetween(normalTime, interuptedTime)
				// .getSeconds() + "秒");
			}
		});
		MQ_Conn = (Connection) connectionFactory.createConnection();
		MQ_Conn.start();
		logger.info("ActiveMQ 连接成功！");
		session = MQ_Conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
		destination = session.createQueue(QUEUE_NAME);
		// 得到消息生成者【发送者】
		// producer = session.createProducer(destination);

		// 得到一个消息接收者
		consumer = session.createConsumer(destination);
		logger.info("connection success");
	}
}
