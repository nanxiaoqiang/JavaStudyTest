package com.nanxiaoqiang.test.spring.core.test2;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年3月14日
 *
 */
@Component
public class Driver {

	private static Logger logger = LogManager.getLogger(Driver.class.getName());

	private String name = null;

	/**
	 * PS:PostConstruct在构造方法之后执行
	 */
	@PostConstruct
	private void checkBefore() {
		logger.info("@PostConstruct:checkBefore");
		try {
			logger.info("now sleep 3 sec.");
			TimeUnit.SECONDS.sleep(3);
			logger.info("slleep 3 sec over!");
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/**
	 * 最先执行的是构造方法
	 */
	public Driver() {
		logger.info("constructor:Driverz()");
		if (StringUtils.isBlank(name)) {
			this.name = "无证驾驶员";
		}
	}
	
	public void init() {
		logger.info("I'am Init...不论private还是public没人调用就不会执行");
	}

	public void sayName() {
		logger.info("司机名字叫:" + this.name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
	/**
	 * Spring容器销毁前执行
	 */
	@PreDestroy
	private void beforeDead() {
		logger.info("@PreDestroy:beforeDead");
		logger.info("now I will be back...");
	}
}
