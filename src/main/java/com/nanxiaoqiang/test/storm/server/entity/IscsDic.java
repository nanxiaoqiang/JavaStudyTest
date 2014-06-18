package com.nanxiaoqiang.test.storm.server.entity;

import java.io.Serializable;

/**
 * 数据库dic表的POJO映射:以bjmetro_iscs_dic为主
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月18日
 * 
 */
public class IscsDic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// SELECT
	// id;
	// line_id;
	// station_id;
	// station_code;
	// system;
	// subsystem;
	// station_type;
	// device_type;
	// device_num;
	// switch_num;
	// point_description;
	// final_point_description;
	// address_1;
	// address_2;
	// point_type;
	// status_00;
	// status_01;
	// status_10;
	// status_11;
	// unit;
	// ratio;
	// offset;
	// is_alarm;
	// alarm_level
	// FROM bjmetro.bjmetro_iscs_dic
	// LIMIT 0; 1000;
	
	/**
	 * id序列号
	 */
	private Long id;

	/**
	 * 线路号
	 */
	private Long line_id;

	/**
	 * 车站号
	 */
	private Long station_id;

	/**
	 * 车站编号
	 */
	private String station_code;

	/**
	 * 系统
	 */
	private String system;

	/**
	 * 子系统
	 */
	private String subsystem;

	/**
	 * 车站类型
	 */
	private String station_type;

	/**
	 * 设备类型
	 */
	private String device_type;

	/**
	 * 采集装置型号
	 */
	private String device_num;

	/**
	 * 开关号
	 */
	private String switch_num;

	/**
	 * 点描述
	 */
	private String point_description;

	/**
	 * 最终点描述
	 */
	private String final_point_description;

	/**
	 * 协议附加地址1（byte）
	 */
	private Integer address_1;

	/**
	 * 协议附加地址2（bit)<br/>
	 * version0.4.1改为Integer
	 */
	private Integer address_2;

	/**
	 * 点类型
	 */
	private String point_type;

	/**
	 * 状态00
	 */
	private String status_00;

	/**
	 * 状态01
	 */
	private String status_01;

	/**
	 * 状态10
	 */
	private String status_10;

	/**
	 * 状态11
	 */
	private String status_11;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 比例系数
	 */
	private Float ratio;

	/**
	 * 偏移系数
	 */
	private Float offset;

	/**
	 * 是否是报警
	 */
	private Integer is_alarm;

	/**
	 * 报警级别
	 */
	private Integer alarm_level;

	/**
	 * 点的实际值<br/>
	 * version0.4.1加入的
	 */
	private String value;
	
	/********************/
	// 后期加入上下限，还有越线报警等级
	// 还要加入小系统（1500V、750V、10KV、400V、UPS、大系统、小系统）

	public IscsDic() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLine_id() {
		return line_id;
	}

	public void setLine_id(Long line_id) {
		this.line_id = line_id;
	}

	public Long getStation_id() {
		return station_id;
	}

	public void setStation_id(Long station_id) {
		this.station_id = station_id;
	}

	public String getStation_code() {
		return station_code;
	}

	public void setStation_code(String station_code) {
		this.station_code = station_code;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}

	public String getStation_type() {
		return station_type;
	}

	public void setStation_type(String station_type) {
		this.station_type = station_type;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public String getDevice_num() {
		return device_num;
	}

	public void setDevice_num(String device_num) {
		this.device_num = device_num;
	}

	public String getSwitch_num() {
		return switch_num;
	}

	public void setSwitch_num(String switch_num) {
		this.switch_num = switch_num;
	}

	public String getPoint_description() {
		return point_description;
	}

	public void setPoint_description(String point_description) {
		this.point_description = point_description;
	}

	public String getFinal_point_description() {
		return final_point_description;
	}

	public void setFinal_point_description(String final_point_description) {
		this.final_point_description = final_point_description;
	}

	public Integer getAddress_1() {
		return address_1;
	}

	public void setAddress_1(Integer address_1) {
		this.address_1 = address_1;
	}

	public Integer getAddress_2() {
		return address_2;
	}

	public void setAddress_2(Integer address_2) {
		this.address_2 = address_2;
	}

	public String getPoint_type() {
		return point_type;
	}

	public void setPoint_type(String point_type) {
		this.point_type = point_type;
	}

	public String getStatus_00() {
		return status_00;
	}

	public void setStatus_00(String status_00) {
		this.status_00 = status_00;
	}

	public String getStatus_01() {
		return status_01;
	}

	public void setStatus_01(String status_01) {
		this.status_01 = status_01;
	}

	public String getStatus_10() {
		return status_10;
	}

	public void setStatus_10(String status_10) {
		this.status_10 = status_10;
	}

	public String getStatus_11() {
		return status_11;
	}

	public void setStatus_11(String status_11) {
		this.status_11 = status_11;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Float getRatio() {
		return ratio;
	}

	public void setRatio(Float ratio) {
		this.ratio = ratio;
	}

	public Float getOffset() {
		return offset;
	}

	public void setOffset(Float offset) {
		this.offset = offset;
	}

	public Integer getIs_alarm() {
		return is_alarm;
	}

	public void setIs_alarm(Integer is_alarm) {
		this.is_alarm = is_alarm;
	}

	public Integer getAlarm_level() {
		return alarm_level;
	}

	public void setAlarm_level(Integer alarm_level) {
		this.alarm_level = alarm_level;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
