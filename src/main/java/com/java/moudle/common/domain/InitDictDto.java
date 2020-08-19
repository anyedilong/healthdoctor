package com.java.moudle.common.domain;

import java.io.Serializable;

public class InitDictDto implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String id;//主键
	private String code;
	private String name;
	private String parentCode;

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		InitDictDto dict = (InitDictDto) obj;
		if (dict.getCode().equals(getCode()) && (null == dict.getParentCode()
				|| (null != dict.getParentCode() && dict.getParentCode().equals(getParentCode())))) {
			return true;
		}

		return false;
	}

}
