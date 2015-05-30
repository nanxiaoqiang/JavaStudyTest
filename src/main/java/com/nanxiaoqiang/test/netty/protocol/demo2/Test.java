package com.nanxiaoqiang.test.netty.protocol.demo2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nanxiaoqiang.test.netty.protocol.demo2.client.Client;
import com.nanxiaoqiang.test.netty.protocol.demo2.client.NettyClientInit;
import com.nanxiaoqiang.test.netty.protocol.demo2.msg.BaseMessage;
import com.nanxiaoqiang.test.netty.protocol.demo2.msg.Header;
import com.nanxiaoqiang.test.netty.protocol.demo2.msg.MsgType;

public class Test {
	public static Client server;

	private static Logger LOGGER = LogManager.getLogger(Test.class.getName());

	public static void main(String[] args) {
		ExecutorService executorService;
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors() * 10, new ThreadFactory() {
			private AtomicInteger i = new AtomicInteger(0);

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.concurrent.ThreadFactory#newThread(java.lang
			 * .Runnable)
			 */
			@Override
			public Thread newThread(Runnable r) {
				// 线程命名
				return new Thread(r, "Thread_AlarmServer_Executor_"
						+ this.i.incrementAndGet());
			}
		});
		NettyClientInit nettyThread = new NettyClientInit();
		executorService.execute(nettyThread);
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("lalala");
		// BaseMessage msg = new BaseMessage();
		// Header h = new Header((short) 0, (short) 0,
		// MsgType.MSG_BEATHEART.getType());
		// msg.setHeader(h);
		// LOGGER.debug(msg);
		// Client.client.getCf().channel().writeAndFlush(msg);
	}
}
