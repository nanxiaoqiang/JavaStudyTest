package com.nanxiaoqiang.test.memcached.client.xmemcached;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * memcached的客户端XMemCached例子：
 * 官方wiki有中文：https://code.google.com/p/xmemcached/wiki/ User_Guide_zh
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年1月22日
 *
 */
public class Test1 {

	private static Logger logger = LogManager.getLogger(Test1.class.getName());

	public Test1() {
	}

	/**
	 * 官方的例子
	 */
	public void siteExample() {
		// // New a XMemcachedClient instance
		// XMemcachedClientBuilder builder = new XMemcachedClientBuilder(
		// AddrUtil.getAddresses("localhost:11211"));
		// XMemcachedClient client = builder.build();
		//
		// // If you want to use binary protocol
		// XMemcachedClientBuilder builder = new XMemcachedClientBuilder(
		// AddrUtil.getAddresses("localhost:11211"));
		// builder.setCommandFactory(new BinaryCommandFactory());
		// XMemcachedClient client = builder.build();
		//
		// // If you want to use xmemcached talking with kestrel
		// XMemcachedClientBuilder builder = new XMemcachedClientBuilder(
		// AddrUtil.getAddresses("localhost:11211"));
		// builder.setCommandFactory(new KestrelCommandFactory());
		// XMemcachedClient client = builder.build();
		//
		// // If you want to store primitive type as String
		// client.setPrimitiveAsString(true);
		//
		// // Add or remove memcached server dynamically
		// client.addServer("localhost:12001 localhost:12002");
		// client.removeServer("localhost:12001 localhost:12002");
		//
		// // get operation
		// String name = client.get("test");
		//
		// // set add replace append prepend gets
		// client.add("hello", 0, "dennis");
		// client.replace("hello", 0, "dennis");
		// client.append("hello", 0, " good");
		// client.prepend("hello", 0, "hello ");
		// GetsResponse response = client.gets("hello");
		// long cas = response.getCas();
		// Object value = response.getValue();
		//
		// // incr decr
		// client.set("a", 0, "1");
		// client.incr("a", 4);
		// client.decr("a", 4);
		//
		// // cas
		// client.cas("a", 0, new CASOperation() {
		// @Override
		// public int getMaxTries() {
		// return 1; // max try times
		// }
		//
		// @Override
		// public Object getNewValue(long currentCAS, Object currentValue) {
		// System.out.println("current value " + currentValue);
		// return 3; // return new value to update
		// }
		// });
		//
		// // flush_all
		// client.flushAll();
		//
		// // stats
		// Map<InetSocketAddress, Map<String, String>> result =
		// client.getStats();
		//
		// // get server versions
		// Map<InetSocketAddress, String> version = memcached.getVersions();
		//
		// // bulk get
		// List<String> keys = new ArrayList<String>();
		// keys.add("hello");
		// keys.add("test");
		// Map<String, Object> map = client.get(keys);
	}

	public static void main(String[] args) throws IOException {
		// XMemcachedClientBuilder builder = new XMemcachedClientBuilder(
		// AddrUtil.getAddresses("localhost:11211"));
		// XMemcachedClient client = builder.build();
		XMemcachedClientBuilder builder = new XMemcachedClientBuilder(
				AddrUtil.getAddresses("localhost:11211"));
		MemcachedClient client = null;
		try {
			client = builder.build();
			boolean b = client.set("hello", 0, "Hello,World!");
			logger.info("添加设置hello返回结果：" + b);
			if (b) {
				Object value = client.get("hello");
				logger.info("value is " + value.getClass().getName()
						+ ", value:" + value.toString());
				if (!client.add("hello", 0, "hehe...")) {
					logger.warn("add 'hello,hehe..'error.");
				}
				if (!client.prepend("hello", "hehe...", 1000)) {
					logger.warn("prepend 'hello,hehe..'error.");
				}
				value = client.get("hello");
				logger.info("value is " + value.getClass().getName()
						+ ", value:" + value.toString());
				client.deleteWithNoReply("hello");
				logger.info("删除key：hello，因为使用了NoReply，所以没有返回值");
				if (!client.append("hello", "hehe...", 1000)) {
					logger.warn("按道理此处已经没有hello了，所以无法在最后append添加，会报错！");
				}
			}
		} catch (TimeoutException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (MemcachedException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}

	}
}
