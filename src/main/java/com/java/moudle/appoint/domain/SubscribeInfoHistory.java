package com.java.moudle.appoint.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.java.until.dba.BaseDomain;

/**
 * subscribe_info_history
 * 
 * @author ljf
 * @version 1.0.0 2019-11-29
 */
@Entity
@Table(name = "subscribe_info_history")
public class SubscribeInfoHistory extends BaseDomain {
    /** 版本号 */
    private static final long serialVersionUID = 7076176169014564277L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 主键 */
    @Id
    private String id;

    /** 预约信息id */
    private String subscribeId;

    /** 医生id */
    private String doctorId;

    /** 预约时间 */
    @JSONField(format="yyyy-MM-dd HH:mm")
    private Date visitTime;

    /** 就诊人员id */
    private String medicalPersonnelId;

    /** 疾病信息 */
    private String diseaseInfo;

    /** 状态(1.未就诊 2.已就诊 3.已过期 4.已取消) */
    private String status;

    /** 创建人 */
    private String createUser;

    /** 创建时间 */
    @JSONField(format="yyyy-MM-dd")
    private Date createTime;

    private String cardNum;//就诊卡号
    private String quitReason;//取消原因
    
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
     * 获取预约信息id
     * 
     * @return 预约信息id
     */
    public String getSubscribeId() {
        return this.subscribeId;
    }

    /**
     * 设置预约信息id
     * 
     * @param subscribeId
     *          预约信息id
     */
    public void setSubscribeId(String subscribeId) {
        this.subscribeId = subscribeId;
    }

    /**
     * 获取医生id
     * 
     * @return 医生id
     */
    public String getDoctorId() {
        return this.doctorId;
    }

    /**
     * 设置医生id
     * 
     * @param doctorId
     *          医生id
     */
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * 获取预约时间
     * 
     * @return 预约时间
     */
    public Date getVisitTime() {
        return this.visitTime;
    }

    /**
     * 设置预约时间
     * 
     * @param visitTime
     *          预约时间
     */
    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    /**
     * 获取就诊人员id
     * 
     * @return 就诊人员id
     */
    public String getMedicalPersonnelId() {
        return this.medicalPersonnelId;
    }

    /**
     * 设置就诊人员id
     * 
     * @param medicalPersonnelId
     *          就诊人员id
     */
    public void setMedicalPersonnelId(String medicalPersonnelId) {
        this.medicalPersonnelId = medicalPersonnelId;
    }

    /**
     * 获取疾病信息
     * 
     * @return 疾病信息
     */
    public String getDiseaseInfo() {
        return this.diseaseInfo;
    }

    /**
     * 设置疾病信息
     * 
     * @param diseaseInfo
     *          疾病信息
     */
    public void setDiseaseInfo(String diseaseInfo) {
        this.diseaseInfo = diseaseInfo;
    }

    /**
     * 获取状态(1.未就诊 2.已就诊 3.已过期 4.已取消)
     * 
     * @return 状态(1
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 设置状态(1.未就诊 2.已就诊 3.已过期 4.已取消)
     * 
     * @param status
     *          状态(1
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

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getQuitReason() {
		return quitReason;
	}

	public void setQuitReason(String quitReason) {
		this.quitReason = quitReason;
	}

    /* This code was generated by TableGo tools, mark 2 end. */
}