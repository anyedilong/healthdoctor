package com.java.until.dba;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import java.io.Serializable;

@Inheritance(strategy = InheritanceType.JOINED) // 选择继承策略
public class BaseDomain implements Serializable {

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	@JSONField(serialize = false)
	@Transient // 不映射进数据库
	private boolean isNewRecord;// 添加修改标志

	public boolean isNewRecord() {
		return isNewRecord;
	}

	public void setNewRecord(boolean isNewRecord) {
		this.isNewRecord = isNewRecord;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
