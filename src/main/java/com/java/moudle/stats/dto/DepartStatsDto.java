package com.java.moudle.stats.dto;

import java.io.Serializable;

public class DepartStatsDto implements Serializable {

	private static final long serialVersionUID = 1636334455522L;

	private String departName;
	private String outName;
	private String subNum;
	private String medNum;
	private String canalNum;
	private String overNum;
	
	private String startDate;
	private String endDate;
	private String departCode;
	private String outCode;
	private String hospitalId;
	
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getOutName() {
		return outName;
	}
	public void setOutName(String outName) {
		this.outName = outName;
	}
	public String getSubNum() {
		return subNum;
	}
	public void setSubNum(String subNum) {
		this.subNum = subNum;
	}
	public String getMedNum() {
		return medNum;
	}
	public void setMedNum(String medNum) {
		this.medNum = medNum;
	}
	public String getCanalNum() {
		return canalNum;
	}
	public void setCanalNum(String canalNum) {
		this.canalNum = canalNum;
	}
	public String getOverNum() {
		return overNum;
	}
	public void setOverNum(String overNum) {
		this.overNum = overNum;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDepartCode() {
		return departCode;
	}
	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}
	public String getOutCode() {
		return outCode;
	}
	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}
	public String getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	
}
