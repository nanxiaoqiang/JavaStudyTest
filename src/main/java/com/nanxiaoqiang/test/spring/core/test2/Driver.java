package com.nanxiaoqiang.test.spring.core.test2;

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

	private String name;

	public Driver() {
		if (StringUtils.isBlank(name)) {
			this.name = "无证驾驶员";
		}
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
}
