package com.nanxiaoqiang.test.spring.data.redis.test1.entity;

import java.io.Serializable;

public class CatagoryCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3256639770642938423L;

	private Long id;
	
	private Long pid;
	
	private String name;
	
	public CatagoryCache() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
