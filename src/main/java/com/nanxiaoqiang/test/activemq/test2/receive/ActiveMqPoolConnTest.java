package com.nanxiaoqiang.test.activemq.test2.receive;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActiveMqPoolConnTest {
	private static Logger logger = LogManager
			.getLogger(ActiveMqPoolConnTest.class.getName());
	private static JMSConsumer consumer = new JMSConsumer();

	public static void main(String[] args) throws Exception {
		consumer.setBrokerUrl("tcp://172.20.98.222:61616");
		consumer.setQueue("L7_MCS_polling.queue");
		consumer.setUserName(null);
		consumer.setPassword(null);
		consumer.setQueuePrefetch(500);
		consumer.setMessageListener(new MultiThreadMessageListener(50,
				new MessageHandler() {
					public void handle(Message message) {
						try {
							logger.info(((TextMessage) message).getText());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}));
		consumer.start();
	}
}
