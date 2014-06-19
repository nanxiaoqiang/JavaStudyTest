package com.nanxiaoqiang.test.netty.hpsocketconn2.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 报警总览实体
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月19日
 * 
 */
public class IscsAlarmHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(IscsAlarmHistory.class
			.getName());

	private Long id; // ID
	private Long lineid; // 线路ID
	private String linename; // 线路名称
	private Long stationid; // 车站id
	private String stationname; // 车站名称
	private String system;// 系统名称
	private String alarmlevel; // 报警等级
	private Long infoid; // 报警信息
	private Long eqpid; // 设备ID
	private String eqpname;// 设备名称
	private Date alarmtime;// 报警时间
	private Date applytime;// 确认时间
	private Date normaltime;// 恢复正常时间
	private Integer isapply;// 是否确认 Integer 1|YES
	private Integer isnormal;// 是否正常 Integer1|YES
	private String remark; // 属性描述

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public Long getLineid() {
		return lineid;
	}

	public void setLineid(Long lineid) {
		this.lineid = lineid;
	}

	public String getLinename() {
		return linename;
	}

	public void setLinename(String linename) {
		this.linename = linename;
	}

	public String getStationname() {
		return stationname;
	}

	public void setStationname(String stationname) {
		this.stationname = stationname;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	@JsonIgnore
	public Long getInfoid() {
		return infoid;
	}

	public void setInfoid(Long infoid) {
		this.infoid = infoid;
	}

	@JsonIgnore
	public Long getEqpid() {
		return eqpid;
	}

	public void setEqpid(Long eqpid) {
		this.eqpid = eqpid;
	}

	public String getEqpname() {
		return eqpname;
	}

	public void setEqpname(String eqpname) {
		this.eqpname = eqpname;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAlarmtime() {
		return alarmtime;
	}

	public void setAlarmtime(Date alarmtime) {
		this.alarmtime = alarmtime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplytime() {
		return applytime;
	}

	public void setApplytime(Date applytime) {
		this.applytime = applytime;
	}

	@JsonIgnore
	public Long getStationid() {
		return stationid;
	}

	public void setStationid(Long stationid) {
		this.stationid = stationid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getNormaltime() {
		return normaltime;
	}

	public void setNormaltime(Date normaltime) {
		this.normaltime = normaltime;
	}

	@JsonIgnore
	public Integer getIsapply() {
		return isapply;
	}

	public void setIsapply(Integer isapply) {
		this.isapply = isapply;
	}

	@JsonIgnore
	public Integer getIsnormal() {
		return isnormal;
	}

	public void setIsnormal(Integer isnormal) {
		this.isnormal = isnormal;
	}

	public IscsAlarmHistory() {
		this.isapply = 0;// 未确认
		this.isnormal = 0;// 未正常
	}

	public String getAlarmlevel() {
		return alarmlevel;
	}

	public void setAlarmlevel(String alarmlevel) {
		this.alarmlevel = alarmlevel;
	}

	public static void main(String[] args) {
		IscsAlarmHistory iah = new IscsAlarmHistory();
		iah.setId(1L);
		iah.setAlarmtime(DateTime.now().minusMinutes(2).toDate());
		iah.setApplytime(DateTime.now().toDate());
		iah.setEqpid(11111111L);
		iah.setEqpname("风机");
		iah.setInfoid(7010101L);
		iah.setIsapply(1);
		iah.setIsnormal(1);
		iah.setLineid(7L);
		iah.setLinename("七号线");
		iah.setNormaltime(DateTime.now().minusMillis(1).toDate());
		iah.setRemark("风机故障");
		iah.setStationid(721L);
		iah.setStationname("焦化厂站");
		iah.setSystem("BAS");
		iah.setAlarmlevel("3");
		ObjectMapper mapper = new ObjectMapper();
		try {
			logger.info(mapper.writeValueAsString(iah));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
