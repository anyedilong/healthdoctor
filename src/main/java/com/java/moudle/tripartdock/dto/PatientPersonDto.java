package com.java.moudle.tripartdock.dto;

import java.io.Serializable;
import java.util.List;

public class PatientPersonDto implements Serializable {

	private static final long serialVersionUID = 9564225322L;
	
	private String id;
    private String name; //就诊人
    private String num; //卡的数量
    private List<PatientCardDto> pcards;
    
    
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
	public List<PatientCardDto> getPcards() {
		return pcards;
	}
	public void setPcards(List<PatientCardDto> pcards) {
		this.pcards = pcards;
	}
    
}
