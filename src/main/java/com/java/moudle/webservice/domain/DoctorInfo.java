package com.java.moudle.webservice.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.java.until.dba.BaseDomain;

/**
 * doctor_info
 * 
 * @author ljf
 * @version 1.0.0 2019-11-29
 */
@Entity
@Table(name = "doctor_info")
public class DoctorInfo extends BaseDomain {
    /** 版本号 */
    private static final long serialVersionUID = 3687192098064716978L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 主键 */
    @Id
    private String id;

    /** 姓名 */
    private String name;

    private String code;
    
    /** 职称 */
    private String professional;

    /** 门诊类型 */
    private String outpatType;

    /** 所属科室 */
    private String deptCode;

    /** 所属医院 */
    private String hospitId;

    /** 擅长描述 */
    private String depict;

    /** 简介 */
    private String introduce;

    /** 头像 */
    private String imageUrl;

    /** 状态 */
    private String status;

    /** 创建人 */
    private String createUser;

    /** 创建时间 */
    @JSONField(format="yyyy-MM-dd")
    private Date createTime;
    
    private String hisId;//his上医生的唯一标识

    @Transient
    private String hospitalName;
    @Transient
    private String deptName;
    @Transient
    private String userId;
    @Transient
    private String outpTime;
    @Transient
    private String deptId;
    @Transient
    private String isFollow;
    @Transient
    private String num;
    @Transient
    private String areaCode;
    @Transient
    private String deptHisId;
    
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
     * 获取姓名
     * 
     * @return 姓名
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置姓名
     * 
     * @param name
     *          姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
     * 获取职称
     * 
     * @return 职称
     */
    public String getProfessional() {
        return this.professional;
    }

    /**
     * 设置职称
     * 
     * @param professional
     *          职称
     */
    public void setProfessional(String professional) {
        this.professional = professional;
    }

    /**
     * 获取门诊类型
     * 
     * @return 门诊类型
     */
    public String getOutpatType() {
        return this.outpatType;
    }

    /**
     * 设置门诊类型
     * 
     * @param outpatType
     *          门诊类型
     */
    public void setOutpatType(String outpatType) {
        this.outpatType = outpatType;
    }

    /**
     * 获取所属科室
     * 
     * @return 所属科室
     */
    public String getDeptCode() {
        return this.deptCode;
    }

    /**
     * 设置所属科室
     * 
     * @param deptCode
     *          所属科室
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    /**
     * 获取所属医院
     * 
     * @return 所属医院
     */
    public String getHospitId() {
        return this.hospitId;
    }

    /**
     * 设置所属医院
     * 
     * @param hospitId
     *          所属医院
     */
    public void setHospitId(String hospitId) {
        this.hospitId = hospitId;
    }

    public String getDepict() {
		return depict;
	}

	public void setDepict(String depict) {
		this.depict = depict;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	/**
     * 获取头像
     * 
     * @return 头像
     */
    public String getImageUrl() {
        return this.imageUrl;
    }

    /**
     * 设置头像
     * 
     * @param imageUrl
     *          头像
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 获取状态
     * 
     * @return 状态
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 设置状态
     * 
     * @param state
     *          状态
     */
    public void setStatus(String status) {
        this.status = status;
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

	public String getHisId() {
		return hisId;
	}

	public void setHisId(String hisId) {
		this.hisId = hisId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOutpTime() {
		return outpTime;
	}

	public void setOutpTime(String outpTime) {
		this.outpTime = outpTime;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getIsFollow() {
		return isFollow;
	}

	public void setIsFollow(String isFollow) {
		this.isFollow = isFollow;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getDeptHisId() {
		return deptHisId;
	}

	public void setDeptHisId(String deptHisId) {
		this.deptHisId = deptHisId;
	}

    /* This code was generated by TableGo tools, mark 2 end. */
}