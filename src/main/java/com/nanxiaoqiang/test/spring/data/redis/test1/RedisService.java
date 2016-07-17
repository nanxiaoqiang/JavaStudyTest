package com.nanxiaoqiang.test.spring.data.redis.test1;

import java.net.URL;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
	private static Logger logger = LogManager.getLogger(RedisService.class
			.getName());

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Resource(name = "redisTemplate")
	private ListOperations<String, String> listOps;

	@Resource(name = "redisTemplate")
	private ValueOperations<String, String> valueOps;
	
	public RedisService() {
	}

	public void addLink(String userId, URL url) {
		logger.info("RedisService addLink {} {}", userId, url);
		listOps.leftPush(userId, url.toExternalForm());
		// or use template directly
		redisTemplate.boundListOps(userId).leftPush(url.toExternalForm());
	}
	
	public void addValue(String id, String value) {	
		redisTemplate.opsForValue().set(id, value);
	}
	
	public String getValue(String id) {
		return redisTemplate.opsForValue().get(id);
	}
}
