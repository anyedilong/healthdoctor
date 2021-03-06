package com.java.moudle.webservice.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.java.until.dba.BaseDomain;

/**
 * department_info
 * 
 * @author ljf
 * @version 1.0.0 2019-11-29
 */
@Entity
@Table(name = "department_info")
public class DepartmentInfo extends BaseDomain {
    /** 版本号 */
    private static final long serialVersionUID = -3392294256508224583L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 主键 */
    @Id
    private String id;

    /** 名称 */
    private String name;

    /** 编号 */
    private String code;
    
    private String parentCode;//父级节点
    
    private String hospitId;//所属医院

    /** 状态 */
    private String state;

    /** 简介 */
    private String introduce;
    
    private String hisId;//his上科室的唯一标识

    /** 创建人 */
    private String createUser;

    /** 创建时间 */
    @JSONField(format="yyyy-MM-dd")
    private Date createTime;

    /* This code was generated by TableGo tools, mark 1 end. */

    /* This code was generated by TableGo tools, mark 2 begin. */

    /**
     * 获取主键
     * 
     * @return 主键
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置主键
     * 
     * @param id
     *          主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取名称
     * 
     * @return 名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置名称
     * 
     * @param name
     *          名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取编号
     * 
     * @return 编号
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置编号
     * 
     * @param code
     *          编号
     */
    public void setCode(String code) {
        this.code = code;
    }

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getHospitId() {
		return hospitId;
	}

	public void setHospitId(String hospitId) {
		this.hospitId = hospitId;
	}

	/**
     * 获取状态
     * 
     * @return 状态
     */
    public String getState() {
        return this.state;
    }

    /**
     * 设置状态
     * 
     * @param state
     *          状态
     */
    public void setState(String state) {
        this.state = state;
    }

    public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getHisId() {
		return hisId;
	}

	public void setHisId(String hisId) {
		this.hisId = hisId;
	}

	/**
     * 获取创建人
     * 
     * @return 创建人
     */
    public String getCreateUser() {
        return this.createUser;
    }

    /**
     * 设置创建人
     * 
     * @param createUser
     *          创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置创建时间
     * 
     * @param createTime
     *          创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /* This code was generated by TableGo tools, mark 2 end. */
}