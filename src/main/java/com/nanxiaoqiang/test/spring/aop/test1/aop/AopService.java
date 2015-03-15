package com.nanxiaoqiang.test.spring.aop.test1.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年3月15日
 *
 */
public class AopService {

	private static Logger logger = LogManager.getLogger(AopService.class
			.getName());

	public AopService() {
	}

	public void beforeAdviceMethod() {
		logger.info("beforeAdviceMethod");
	}

	public void afterAdviceMethod() {
		logger.info("afterAdviceMethod");
	}

	public void aroundAdviceMethod() {
		logger.info("aroundAdviceMethod");
	}
}
