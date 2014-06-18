package com.nanxiaoqiang.test.storm.server.entity;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;

/**
 * 从MQ得到的寄存器数据中得到的update数据。
 * 
 * @author nanxiaoqiang
 * 
 * @version v0.1.1 2014年6月18日
 * 
 */
public class StormMqObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(StormMqObject.class
			.getName());

	private String correlationId;

	private Map<Long, IscsValue> values = Maps.newConcurrentMap();

	private Long time;

	public StormMqObject() {
		logger.debug("StormMqObject");
		this.time = System.currentTimeMillis();
	}

	public StormMqObject(String correlationId, Map<Long, IscsValue> values,
			Long time) {
		super();
		logger.debug("StormMqObject");
		this.correlationId = correlationId;
		this.values = values;
		this.time = time;
	}

	public StormMqObject(String correlationId, Map<Long, IscsValue> values) {
		super();
		logger.debug("StormMqObject");
		this.correlationId = correlationId;
		this.values = values;
		this.time = System.currentTimeMillis();
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public Map<Long, IscsValue> getValues() {
		return values;
	}

	public void setValues(Map<Long, IscsValue> values) {
		this.values = values;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.correlationId)
				.append(this.values).append(this.time).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) // 先检查是否其自反性，后比较other是否为空。这样效率高
			return true;
		if (other == null)
			return false;
		if (!(other instanceof StormMqObject))
			return false;

		final StormMqObject obj = (StormMqObject) other;
		return new EqualsBuilder()
				.append(this.correlationId, obj.correlationId)
				.append(this.time, obj.time)
				// 暂时只判断了两个。。。
				// .append(this.time, obj.time)
				.isEquals();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

}
