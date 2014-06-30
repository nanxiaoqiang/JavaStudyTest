package com.nanxiaoqiang.test.activemq.test2.send;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ActiveMQ Pool 测试，加报错重连等等。
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * @since 2014年6月30日
 */
public class ActiveMqPoolConnTest2 {// implements MessageListener,
	// ExceptionListener {
	// 设置连接的最大连接数
	public final static int DEFAULT_MAX_CONNECTIONS = 5;
	private int maxConnections = DEFAULT_MAX_CONNECTIONS;
	// 设置每个连接中使用的最大活动会话数
	private int maximumActiveSessionPerConnection = DEFAULT_MAXIMUM_ACTIVE_SESSION_PER_CONNECTION;
	public final static int DEFAULT_MAXIMUM_ACTIVE_SESSION_PER_CONNECTION = 300;
	// 线程池数量
	private int threadPoolSize = DEFAULT_THREAD_POOL_SIZE;
	public final static int DEFAULT_THREAD_POOL_SIZE = 50;
	// 强制使用同步返回数据的格式
	private boolean useAsyncSendForJMS = DEFAULT_USE_ASYNC_SEND_FOR_JMS;
	public final static boolean DEFAULT_USE_ASYNC_SEND_FOR_JMS = true;
	// 是否持久化消息
	private boolean isPersistent = DEFAULT_IS_PERSISTENT;
	public final static boolean DEFAULT_IS_PERSISTENT = true;

	// 连接地址
	private String brokerUrl;

	private String userName;

	private String password;

	private ExecutorService threadPool;

	private PooledConnectionFactory connectionFactory;

	private static Logger logger = LogManager
			.getLogger(ActiveMqPoolConnTest2.class.getName());

	public ActiveMqPoolConnTest2(String brokerUrl, String userName,
			String password) {
		this(brokerUrl, userName, password, DEFAULT_MAX_CONNECTIONS,
				DEFAULT_MAXIMUM_ACTIVE_SESSION_PER_CONNECTION,
				DEFAULT_THREAD_POOL_SIZE, DEFAULT_USE_ASYNC_SEND_FOR_JMS,
				DEFAULT_IS_PERSISTENT);
	}

	public ActiveMqPoolConnTest2(String brokerUrl, String userName,
			String password, int maxConnections,
			int maximumActiveSessionPerConnection, int threadPoolSize,
			boolean useAsyncSendForJMS, boolean isPersistent) {
		this.useAsyncSendForJMS = useAsyncSendForJMS;
		this.isPersistent = isPersistent;
		this.brokerUrl = brokerUrl;
		this.userName = StringUtils.isBlank(userName) ? ActiveMQConnection.DEFAULT_USER
				: userName;
		this.password = StringUtils.isBlank(password) ? ActiveMQConnection.DEFAULT_USER
				: password;
		this.maxConnections = maxConnections;
		this.maximumActiveSessionPerConnection = maximumActiveSessionPerConnection;
		this.threadPoolSize = threadPoolSize;
		// init();
	}

	public void run() {
		logger.info("init");
		// 设置JAVA线程池
		this.threadPool = Executors.newFixedThreadPool(this.threadPoolSize,
				new ThreadFactory() {
					private AtomicInteger threadIndex = new AtomicInteger(0);

					@Override
					public Thread newThread(Runnable r) {
						// 给每个Thread加上了名称Thread_Executor_X,X为数字，采用AtomicInteger位了保证i++的运算线程安全
						// getAndIncrement == i++
						// incrementAndGet == ++i
						return new Thread(r, "ActiveMQ_Thread_Pool_Executor_"
								+ this.threadIndex.getAndIncrement());
					}

				});
		// ActiveMQ的连接工厂
		ActiveMQConnectionFactory actualConnectionFactory = new ActiveMQConnectionFactory(
				this.userName, this.password, this.brokerUrl);
		actualConnectionFactory.setUseAsyncSend(this.useAsyncSendForJMS);
		// Active中的连接池工厂
		this.connectionFactory = new PooledConnectionFactory(
				actualConnectionFactory);
		this.connectionFactory.setCreateConnectionOnStartup(true);
		this.connectionFactory.setMaxConnections(this.maxConnections);
		this.connectionFactory
				.setMaximumActiveSessionPerConnection(this.maximumActiveSessionPerConnection);
	}

	public void shutdown() {
		logger.info("shutdown");
		if (threadPool != null) {
			threadPool.shutdown();
		}
	}

	/**
	 * 执行发送消息的具体方法
	 * 
	 * @param queue
	 * @param map
	 */
	public void send(final String queue, final Object obj) {
		logger.info("send:" + queue + "|" + obj == null ? "null" : obj
				.getClass().getName());
		// 直接使用线程池来执行具体的调用
		this.threadPool.execute(new Runnable() {
			@Override
			public void run() {
				// try {
				while (true) {
					if (sendMsg(queue, obj)) {
						break;
					}
				}

				// } catch (Exception e) {
				// logger.error("send:run:" + e.getMessage());
				// e.printStackTrace();
				// }
			}
		});
	}

	/**
	 * 真正的执行消息发送
	 * 
	 * @param queue
	 * @param map
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	private boolean sendMsg(String queue, Object obj) {// throws Exception {
		boolean rt = false;
		logger.info("sendMsg:" + queue + "|" + obj == null ? "null" : obj
				.getClass().getName());
		Connection connection = null;
		Session session = null;
		try {
			// 从连接池工厂中获取一个连接
			connection = this.connectionFactory.createConnection();
			/*
			 * createSession(boolean transacted,int acknowledgeMode) transacted
			 * - indicates whether the session is transacted acknowledgeMode -
			 * indicates whether the consumer or the client will acknowledge any
			 * messages it receives; ignored if the session is transacted. Legal
			 * values are Session.AUTO_ACKNOWLEDGE, Session.CLIENT_ACKNOWLEDGE,
			 * and Session.DUPS_OK_ACKNOWLEDGE.
			 */
			// false 参数表示 为非事务型消息，后面的参数表示消息的确认类型
			session = connection.createSession(Boolean.FALSE,
					Session.AUTO_ACKNOWLEDGE);
			// Destination is superinterface of Queue
			// PTP消息方式
			Destination destination = session.createQueue(queue);
			// Creates a MessageProducer to send messages to the specified
			// destination
			MessageProducer producer = session.createProducer(destination);
			// set delevery mode
			producer.setDeliveryMode(this.isPersistent ? DeliveryMode.PERSISTENT
					: DeliveryMode.NON_PERSISTENT);
			// map convert to javax message or text message
			Message message = getMessage(session, obj);
			if (message != null) {
				producer.send(message);
				logger.info("send success!!");
			}
			rt = true;
		} catch (Throwable t) {
			logger.error("send error and will sleep 1 sec:" + t.getMessage());
			TimeUnit.SECONDS.sleep(1);
		} finally {
			closeSession(session);
			closeConnection(connection);
			return rt;
		}
	}

	private Message getMessage(Session session, Object obj) throws JMSException {
		if (obj instanceof Map<?, ?>) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) obj;
			MapMessage message = session.createMapMessage();
			if (map != null && !map.isEmpty()) {
				Set<String> keys = map.keySet();
				for (String key : keys) {
					message.setObject(key, map.get(key));
				}
			}
			return message;
		} else if (obj instanceof String) {
			TextMessage message = session.createTextMessage((String) obj);
			return message;
		}
		return null;
	}

	private void closeSession(Session session) {
		try {
			if (session != null) {
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Override
	// public void onException(JMSException e) {
	// e.printStackTrace();
	// logger.error("出错啦！：可以在这里加入重连:" + e.getMessage());
	// try {
	// TimeUnit.SECONDS.sleep(1);
	// } catch (InterruptedException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// this.shutdown();
	// this.init();
	// }

	public static void main(String[] args) throws InterruptedException {
		ActiveMqPoolConnTest2 t = new ActiveMqPoolConnTest2(
				"tcp://172.20.98.222:61616", null, null);
		t.run();
		t.send("L7_MCS_polling.queue", "曹大大今天没吃药感觉自己萌萌哒");
		// TimeUnit.SECONDS.sleep(5);
		t.send("L7_MCS_polling.queue", "caohongfeicaohongfeicaodada");
		// t.shutdown();
	}

	// @Override
	// public void onMessage(Message message) {
	// logger.info("onMessage:" + message == null ? "null" : message
	// .getClass().getName());
	// }
}
