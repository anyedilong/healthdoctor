package com.java.moudle.webservice.dto;

import java.io.Serializable;

public class AppointDto implements Serializable {

	private static final long serialVersionUID = 16486466L;

	private String id;
	private String name;
	
	private String num;//剩余票数
	private String outTime;//出诊时间
	private String price; //挂号费
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
}
