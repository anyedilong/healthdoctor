package com.java.moudle.documentGuidelines.domain;
import java.sql.Clob;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.java.until.dba.BaseDomain;

/**
 * 联系我们(sys_call_us)
 * 
 * @author ljf
 * @version 1.0.0 2019-12-11
 */
@Entity
@Table(name = "sys_call_us")
public class SysCallUs extends BaseDomain implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -8324033209452805554L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private String id;

    /** 售后电话 */
    private String afterSalePhone;
    
    /** 入驻电话 */
    private String settledInPhone; 
    
    /** 联系我们 */
    private String content;

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
     * 获取联系我们
     * 
     * @return 联系我们
     */
    public String getContent() {
        return this.content;
    }

    public String getAfterSalePhone() {
		return afterSalePhone;
	}

	public void setAfterSalePhone(String afterSalePhone) {
		this.afterSalePhone = afterSalePhone;
	}

	public String getSettledInPhone() {
		return settledInPhone;
	}

	public void setSettledInPhone(String settledInPhone) {
		this.settledInPhone = settledInPhone;
	}

	/**
     * 设置联系我们
     * 
     * @param content
     *          联系我们
     */
    public void setContent(String content) {
        this.content = content;
    }

    /* This code was generated by TableGo tools, mark 2 end. */
}