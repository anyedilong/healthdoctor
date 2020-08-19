package com.java.moudle.hospital.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.java.until.dba.BaseDomain;
import com.java.until.dict.DictUtil;

/**
 * hospital_info
 * 
 * @author ljf
 * @version 1.0.0 2019-11-29
 */
@Entity
@Table(name = "hospital_info")
public class HospitalInfo extends BaseDomain {
    /** 版本号 */
    private static final long serialVersionUID = -4948112114528589055L;

    /** 主键 */
    @Id
    private String id;
    private String name;
    private String areaCode;//区域code
    private String type; //医院类型
    private String levelType;//医院等级
    private String introduce;//简介
    private String telephone;//联系电话
    private String address;//地址
    private String imageUrl;//图片
    private String recommend;//是否推荐 1.推荐 2.不推荐
    private String legalParson;//法人
    private String legalIdcardFront;//法人身份证正面
    private String legalIdcardReverse;//法人身份证反面
    private String bussinessLicense;//营业执照
    private String hisInterfaceUrl;//his服务地址
    private String status; //状态 0.暂存 1.提交后未审核 2.审核通过后启用 3.禁用
    private String createUser;
    @JSONField(format="yyyy-MM-dd HH:mm")
    private Date createTime;
    private String remark;//备注

    @Transient
    private String deptCodes;//多个科室字符串
    @Transient
    private String startDate;//开始日期
    @Transient
    private String endDate;//截止日期
    @Transient
    private String levelTypeText;//等级说明
    @Transient
    private String typeText;//类型说明

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.typeText = DictUtil.getDictValue("1002", type);
		this.type = type;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getLevelType() {
		return levelType;
	}

	public void setLevelType(String levelType) {
		this.levelTypeText = DictUtil.getDictValue("1001", levelType);
		this.levelType = levelType;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getLegalParson() {
		return legalParson;
	}

	public void setLegalParson(String legalParson) {
		this.legalParson = legalParson;
	}

	public String getLegalIdcardFront() {
		return legalIdcardFront;
	}

	public void setLegalIdcardFront(String legalIdcardFront) {
		this.legalIdcardFront = legalIdcardFront;
	}

	public String getLegalIdcardReverse() {
		return legalIdcardReverse;
	}

	public void setLegalIdcardReverse(String legalIdcardReverse) {
		this.legalIdcardReverse = legalIdcardReverse;
	}

	public String getBussinessLicense() {
		return bussinessLicense;
	}

	public void setBussinessLicense(String bussinessLicense) {
		this.bussinessLicense = bussinessLicense;
	}

	public String getHisInterfaceUrl() {
		return hisInterfaceUrl;
	}

	public void setHisInterfaceUrl(String hisInterfaceUrl) {
		this.hisInterfaceUrl = hisInterfaceUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDeptCodes() {
		return deptCodes;
	}

	public void setDeptCodes(String deptCodes) {
		this.deptCodes = deptCodes;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLevelTypeText() {
		return levelTypeText;
	}

	public void setLevelTypeText(String levelTypeText) {
		this.levelTypeText = levelTypeText;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTypeText() {
		return typeText;
	}

	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}
    
}