package com.java.moudle.tripartdock.dto;

import java.io.Serializable;

public class PatientCardDto implements Serializable {

	private static final long serialVersionUID = 9564675322L;
    
	private String id;
    private String hospitalName; //医院名称
    private String cardNum; //就诊卡号
    private String imageUrl;//医院图片
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
