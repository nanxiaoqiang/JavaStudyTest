package com.nanxiaoqiang.test.netty.hpsocketconn2.client.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Netty客户端配置类<br/>
 * 根据Alibaba的RocketMQ源代码的改的。
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * @since 2014年6月21日
 */
public class NettyClientConfig {
	private static Logger logger = LogManager.getLogger(NettyClientConfig.class
			.getName());

	// 处理Server Response/Request
	private int clientWorkerThreads = 4;
	private int clientCallbackExecutorThreads = Runtime.getRuntime()
			.availableProcessors();
	private int clientSelectorThreads = 1;
	private int clientOnewaySemaphoreValue = 256;
	private int clientAsyncSemaphoreValue = 128;
	private long connectTimeoutMillis = 3000;
	// channel超过1分钟不被访问 就关闭
	private long channelNotActiveInterval = 1000 * 60;

	private int clientChannelMaxIdleTimeSeconds = 120;

	public NettyClientConfig() {
		logger.debug("NettyClientConfig");
	}

	public int getClientWorkerThreads() {
		return clientWorkerThreads;
	}

	public void setClientWorkerThreads(int clientWorkerThreads) {
		this.clientWorkerThreads = clientWorkerThreads;
	}

	public int getClientCallbackExecutorThreads() {
		return clientCallbackExecutorThreads;
	}

	public void setClientCallbackExecutorThreads(
			int clientCallbackExecutorThreads) {
		this.clientCallbackExecutorThreads = clientCallbackExecutorThreads;
	}

	public int getClientSelectorThreads() {
		return clientSelectorThreads;
	}

	public void setClientSelectorThreads(int clientSelectorThreads) {
		this.clientSelectorThreads = clientSelectorThreads;
	}

	public int getClientOnewaySemaphoreValue() {
		return clientOnewaySemaphoreValue;
	}

	public void setClientOnewaySemaphoreValue(int clientOnewaySemaphoreValue) {
		this.clientOnewaySemaphoreValue = clientOnewaySemaphoreValue;
	}

	public int getClientAsyncSemaphoreValue() {
		return clientAsyncSemaphoreValue;
	}

	public void setClientAsyncSemaphoreValue(int clientAsyncSemaphoreValue) {
		this.clientAsyncSemaphoreValue = clientAsyncSemaphoreValue;
	}

	public long getConnectTimeoutMillis() {
		return connectTimeoutMillis;
	}

	public void setConnectTimeoutMillis(long connectTimeoutMillis) {
		this.connectTimeoutMillis = connectTimeoutMillis;
	}

	public long getChannelNotActiveInterval() {
		return channelNotActiveInterval;
	}

	public void setChannelNotActiveInterval(long channelNotActiveInterval) {
		this.channelNotActiveInterval = channelNotActiveInterval;
	}

	public int getClientChannelMaxIdleTimeSeconds() {
		return clientChannelMaxIdleTimeSeconds;
	}

	public void setClientChannelMaxIdleTimeSeconds(
			int clientChannelMaxIdleTimeSeconds) {
		this.clientChannelMaxIdleTimeSeconds = clientChannelMaxIdleTimeSeconds;
	}

}
