package com.nanxiaoqiang.test.mybatis.demo3.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MyBatisStudyPerson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private Integer sex;
	private String tel;
	private String phone;
	private String address;
	private String info;

	public MyBatisStudyPerson() {
	}

	public MyBatisStudyPerson(Long id) {
		super();
		this.id = id;
	}

	public MyBatisStudyPerson(String name, Integer sex, String tel,
			String phone, String address, String info) {
		super();
		this.name = name;
		this.sex = sex;
		this.tel = tel;
		this.phone = phone;
		this.address = address;
		this.info = info;
	}

	public MyBatisStudyPerson(Long id, String name, Integer sex, String tel,
			String phone, String address, String info) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.tel = tel;
		this.phone = phone;
		this.address = address;
		this.info = info;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// 只判断id
		return HashCodeBuilder.reflectionHashCode(this, "name", "sex", "tel",
				"phone", "address", "info");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// 只判断id
		return EqualsBuilder.reflectionEquals(this, obj, "name", "sex", "tel",
				"phone", "address", "info");
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
