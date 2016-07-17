package com.nanxiaoqiang.test.spring.data.redis.test1.entity;

import java.io.Serializable;

public class UserCache implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9195576945204727609L;

	private Long id;
	
	private String pin;
	
	private String userName;
	
	public UserCache() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
