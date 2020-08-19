package com.java.moudle.system.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.java.moudle.hospital.domain.HospitalInfo;
import com.java.until.dba.BaseDomain;


/**
 * 系统用户表(sys_user)
 * 
 * @author ljf
 * @version 1.0.0 2019-11-29
 */
@Entity
@Table(name = "sys_user")
public class SysUser extends BaseDomain {
    /** 版本号 */
    private static final long serialVersionUID = 8499231367204709464L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private String id;
    
    private String username;//用户名
    
    /** 昵称 */
    private String nickname;

    /** 密码 */
    private String password;

    /** 联系方式 */
    private String mobilePhone;
    
    /** 微信openid */
    private String openId;

    /** 状态   0 正常  1 冻结  2 删除 */
    private String status;

    /** 类型 0 客户端用户 1 后台维护用户 */
    private String type;
    
    private String 	hospitalId;//医院的唯一标识

    /** 备注 */
    private String remarks;

    /** 更新时间 */
    @JSONField(format="yyyy-MM-dd")
    private Date updateTime;

    /** 更新人 */
    private String updateUser;

    private String authorities;
    
    private String pwd;
    
    @Transient
    private String identCode;//验证码
    @Transient
    private String optionName;//验证码
    @Transient
    private HospitalInfo hospitalInfo;//医疗机构
    @Transient
    private SysRole role;//角色

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

    public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
     * 获取密码
     * 
     * @return 密码
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置密码
     * 
     * @param password
     *          密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取联系方式
     * 
     * @return 联系方式
     */
    public String getMobilePhone() {
        return this.mobilePhone;
    }

    /**
     * 设置联系方式
     * 
     * @param mobilePhone
     *          联系方式
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * 获取状态   0 正常  1 冻结  2 删除
     * 
     * @return 状态   0 正常  1 冻结  2 删除
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 设置状态   0 正常  1 冻结  2 删除
     * 
     * @param status
     *          状态   0 正常  1 冻结  2 删除
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
     * 获取类型 0 客户端用户 1 后台维护用户
     * 
     * @return 类型 0 客户端用户 1 后台维护用户
     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置类型 0 客户端用户 1 后台维护用户
     * 
     * @param type
     *          类型 0 客户端用户 1 后台维护用户
     */
    public void setType(String type) {
        this.type = type;
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
        return this.updateTime;
    }

    public void setUpdateTime(Date createTime) {
        this.updateTime = createTime;
    }
    
    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String createUser) {
        this.updateUser = createUser;
    }

	public String getIdentCode() {
		return identCode;
	}

	public void setIdentCode(String identCode) {
		this.identCode = identCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public HospitalInfo getHospitalInfo() {
		return hospitalInfo;
	}

	public void setHospitalInfo(HospitalInfo hospitalInfo) {
		this.hospitalInfo = hospitalInfo;
	}

	public SysRole getRole() {
		return role;
	}

	public void setRole(SysRole role) {
		this.role = role;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

    /* This code was generated by TableGo tools, mark 2 end. */
}