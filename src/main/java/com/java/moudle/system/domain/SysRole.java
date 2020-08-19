package com.java.moudle.system.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.java.until.dba.BaseDomain;

/**
 * 角色表(sys_role)
 * 
 * @author ljf
 * @version 1.0.0 2019-11-29
 */
@Entity
@Table(name = "sys_role")
public class SysRole extends BaseDomain {
    /** 版本号 */
    private static final long serialVersionUID = 8678596719137552690L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private String id;

    /** 角色名称 */
    private String roleName;

    /** 状态  0 正常  1 冻结  2 删除 */
    private Integer status;

    /** 机构ID */
    private String orgId;

    /** 备注 */
    private String remarks;

    /** 更新时间 */
    @JSONField(format="yyyy-MM-dd")
    private Date updateTime;

    /** 更新人 */
    private String updateUser;

    /* This code was generated by TableGo tools, mark 1 end. */

    /* This code was generated by TableGo tools, mark 2 begin. */

    /**
     * 获取id
     * 
     * @return id
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置id
     * 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取角色名称
     * 
     * @return 角色名称
     */
    public String getRoleName() {
        return this.roleName;
    }

    /**
     * 设置角色名称
     * 
     * @param roleName
     *          角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取状态  0 正常  1 冻结  2 删除
     * 
     * @return 状态  0 正常  1 冻结  2 删除
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置状态  0 正常  1 冻结  2 删除
     * 
     * @param status
     *          状态  0 正常  1 冻结  2 删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取机构ID
     * 
     * @return 机构ID
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 设置机构ID
     * 
     * @param orgId
     *          机构ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取备注
     * 
     * @return 备注
     */
    public String getRemarks() {
        return this.remarks;
    }

    /**
     * 设置备注
     * 
     * @param remarks
     *          备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

    /* This code was generated by TableGo tools, mark 2 end. */
}