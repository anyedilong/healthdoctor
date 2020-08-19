package com.java.moudle.stats.dto;

import java.io.Serializable;

public class StatsResultDto implements Serializable {

	private static final long serialVersionUID = 163633355522L;

	private String name;
	private String value;
	private String deptCode;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
}
