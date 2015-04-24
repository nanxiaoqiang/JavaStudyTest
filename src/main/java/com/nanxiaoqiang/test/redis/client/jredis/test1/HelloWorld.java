package com.nanxiaoqiang.test.redis.client.jredis.test1;

import java.util.List;

import redis.clients.jedis.Jedis;

public class HelloWorld {

	public HelloWorld() {
	}

	public static void main(String[] args) {
		String key = "key.localhost.test1.helloworld";
		Jedis jedis = new Jedis("localhost");
		jedis.set(key, "Hello World!");
		String value = jedis.get(key);
		System.out.println(value);
		String str = jedis.select(1);// 切换到db1簇(第二个)
		System.out.println(str);// OK表示切换成功
		System.out.println(jedis.get(key));// 此时再输出key就找不到了啊！null
		jedis.set(key, "Hello World! in db1");
		System.out.println(jedis.get(key));// 此时输出的是db1的key
		System.out.println(jedis.del(key));// 删除成功返回1
		System.out.println(jedis.select(0));// 此时再选择db0
		System.out.println(jedis.get(key));// 此时输出的是db0的key的值
		System.out.println("***********");
		List<String> list = jedis.mget("null", key, "no");
		for (String string : list) {
			System.out.println(string);
		}
		jedis.disconnect();
		jedis.close();
	}

}
