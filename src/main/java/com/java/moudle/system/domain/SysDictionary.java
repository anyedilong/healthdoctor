package com.java.moudle.system.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.java.until.dba.BaseDomain;

/**
 * sys_dictionary
 * 
 * @author ljf
 * @version 1.0.0 2019-11-29
 */
@Entity
@Table(name = "sys_dictionary")
public class SysDictionary extends BaseDomain {
    /** 版本号 */
    private static final long serialVersionUID = 794407181568234937L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 主键 */
    @Id
    private String id;

    /** 上级ID */
    private String parentCode;

    /** code */
    private String code;

    /** 名称 */
    private String name;

    /** 更新人 */
    private String updateUser;

    /** 更新时间 */
    @JSONField(format="yyyy-MM-dd")
    private Date updateTime;

    /** 备注 */
    private String remark;

    /** 0是没删除1是已删除 */
    private Integer deleteFlg;

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

    public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	/**
     * 获取code
     * 
     * @return code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置code
     * 
     * @param code
     *          code
     */
    public void setCode(String code) {
        this.code = code;
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

    public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
     * 获取备注
     * 
     * @return 备注
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置备注
     * 
     * @param remark
     *          备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取0是没删除1是已删除
     * 
     * @return 0是没删除1是已删除
     */
    public Integer getDeleteFlg() {
        return this.deleteFlg;
    }

    /**
     * 设置0是没删除1是已删除
     * 
     * @param deleteFlg
     *          0是没删除1是已删除
     */
    public void setDeleteFlg(Integer deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    /* This code was generated by TableGo tools, mark 2 end. */
}