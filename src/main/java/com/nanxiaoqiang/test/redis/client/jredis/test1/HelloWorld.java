package com.nanxiaoqiang.test.redis.client.jredis.test1;

import redis.clients.jedis.Jedis;

public class HelloWorld {

	public HelloWorld() {
	}

	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost");
		jedis.set("key.localhost.test1.helloworld", "Hello World!");
		String value = jedis.get("key.localhost.test1.helloworld");
		System.out.println(value);
		jedis.disconnect();
		jedis.close();
	}

}
