package com.nanxiaoqiang.test.netty.protocol.demo2.client;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NettyClientInit implements Runnable {
	private static Logger LOGGER = LogManager.getLogger(NettyClientInit.class
			.getName());
	boolean isRunning = true;
	boolean isStart = false;

	public NettyClientInit() {
		Client.client = new Client("127.0.0.1", 8080);
	}

	public void shutDown() {
		isStart = true;
	}

	@Override
	public void run() {
		while (isRunning) {
			try {
				if (!isStart) {
					Client.client.connect();
				}
			} catch (Throwable e) {
				LOGGER.error("启动NettyServer未成功。" + e.getMessage());
				e.printStackTrace();
			} finally {
				LOGGER.debug("关闭Netty。并准备重新启动。");
				try {
					TimeUnit.SECONDS.sleep(5);
					Client.client.connect();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}

}
