package com.nanxiaoqiang.test.spring.core.test2;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {

	private String brand;

	private String color;

	private int maxSpeed;

	@Autowired
	private Driver driver;

	public Car() {
	}

	public Car(String brand, String color, int maxSpeed) {
		super();
		this.brand = brand;
		this.color = color;
		this.maxSpeed = maxSpeed;
	}

	public Car(String brand, String color, int maxSpeed, Driver driver) {
		super();
		this.brand = brand;
		this.color = color;
		this.maxSpeed = maxSpeed;
		this.driver = driver;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
