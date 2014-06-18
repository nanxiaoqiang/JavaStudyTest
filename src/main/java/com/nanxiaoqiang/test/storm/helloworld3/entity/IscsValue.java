package com.nanxiaoqiang.test.storm.helloworld3.entity;

import java.io.Serializable;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 更新量用的类，只有id和value，去掉了time
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月18日
 */
public class IscsValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;

	private String value;

	// private long time = 0L;

	public IscsValue() {
		// TODO Auto-generated constructor stub
	}

	public IscsValue(Long id, String value, long time) {
		super();
		this.id = id;
		this.value = value;
		// this.time = time;
	}

	public IscsValue(Long id, String value) {
		super();
		this.id = id;
		this.value = value;
		// this.time = System.currentTimeMillis();
	}

	public IscsValue(IscsValue obj) {
		super();
		this.id = obj.getId();
		this.value = obj.getValue();
		// this.time = obj.getTime();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	// public long getTime() {
	// return time;
	// }
	//
	// public void setTime(long time) {
	// this.time = time;
	// }

	@Override
	public boolean equals(Object other) {
		// TODO Auto-generated method stub
		if (this == other) // 先检查是否其自反性，后比较other是否为空。这样效率高
			return true;
		if (other == null)
			return false;
		if (!(other instanceof IscsValue))
			return false;

		final IscsValue obj = (IscsValue) other;
		return new EqualsBuilder().append(this.id, obj.id)
				.append(this.value, obj.value)
				// .append(this.time, obj.time)
				.isEquals();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return new HashCodeBuilder().append(this.id).append(this.value)
		// .append(this.time)
				.toHashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		// return super.toString();
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
		// return new ToStringBuilder(this).append("id", id)
		// .append("value", value).append("time", time).toString();
		// return new StringBuffer().append(this.id).append('|')
		// .append(this.value).append('|').append(this.time).toString();
	}

	/**
	 * 做测试用
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		IscsValue iv = new IscsValue(1L, "12", 1234567890L);
		System.out.println(iv);
		for (int i = 0; i < 100; i++)
			System.out.println(RandomUtils.nextInt(0, 2));
	}
}
