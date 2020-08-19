package com.java.moudle.webservice.dto;

import java.io.Serializable;

public class AppointTimeDto implements Serializable {

	private static final long serialVersionUID = 16486466L;

	private String subTimeText;
	private String subTime;
	private AppointDto morning;
	private AppointDto afternoon;
	private AppointDto nightDiag;
	
	
	public String getSubTimeText() {
		return subTimeText;
	}
	public void setSubTimeText(String subTimeText) {
		this.subTimeText = subTimeText;
	}
	public String getSubTime() {
		return subTime;
	}
	public void setSubTime(String subTime) {
		this.subTime = subTime;
	}
	public AppointDto getMorning() {
		return morning;
	}
	public void setMorning(AppointDto morning) {
		this.morning = morning;
	}
	public AppointDto getAfternoon() {
		return afternoon;
	}
	public void setAfternoon(AppointDto afternoon) {
		this.afternoon = afternoon;
	}
	public AppointDto getNightDiag() {
		return nightDiag;
	}
	public void setNightDiag(AppointDto nightDiag) {
		this.nightDiag = nightDiag;
	}
	
}
