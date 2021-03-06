package com.java.moudle.system.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.java.until.dba.BaseDomain;

/**
 * 菜单表(sys_menu)
 * 
 * @author ljf
 * @version 1.0.0 2019-11-29
 */
@Entity
@Table(name = "sys_menu")
public class SysMenu extends BaseDomain {
    /** 版本号 */
    private static final long serialVersionUID = 3679995627780429001L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private String id;

    /** 名称 */
    private String name;

    /** 路径 */
    private String url;

    /** 上级ID */
    private String parentId;

    /** 类型(1菜单，2功能) */
    private Integer type;

    /** 等级 */
    private BigDecimal funLevel;

    /** 状态 */
    private Integer status;

    /** 创建时间 */
    @JSONField(format="yyyy-MM-dd")
    private Date updateTime;

    /** 创建人 */
    private String updateUser;
    
    private String orderNum;//排序号

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
     * 获取路径
     * 
     * @return 路径
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * 设置路径
     * 
     * @param url
     *          路径
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取上级ID
     * 
     * @return 上级ID
     */
    public String getParentId() {
        return this.parentId;
    }

    /**
     * 设置上级ID
     * 
     * @param parentId
     *          上级ID
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取类型(1菜单，2功能)
     * 
     * @return 类型(1菜单
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置类型(1菜单，2功能)
     * 
     * @param type
     *          类型(1菜单
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取等级
     * 
     * @return 等级
     */
    public BigDecimal getFunLevel() {
        return this.funLevel;
    }

    /**
     * 设置等级
     * 
     * @param funLevel
     *          等级
     */
    public void setFunLevel(BigDecimal funLevel) {
        this.funLevel = funLevel;
    }

    /**
     * 获取状态
     * 
     * @return 状态
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置状态
     * 
     * @param status
     *          状态
     */
    public void setStatus(Integer status) {
        this.status = status;
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

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

    /* This code was generated by TableGo tools, mark 2 end. */
}