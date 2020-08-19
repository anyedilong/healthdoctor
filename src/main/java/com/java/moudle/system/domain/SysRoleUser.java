package com.java.moudle.system.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.java.until.dba.BaseDomain;

/**
 * 角色用户关系表(sys_role_user)
 * 
 * @author ljf
 * @version 1.0.0 2019-11-29
 */
@Entity
@Table(name = "sys_role_user")
public class SysRoleUser extends BaseDomain {
    /** 版本号 */
    private static final long serialVersionUID = 4333268344461482996L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private String id;

    /** roleId */
    private String roleId;

    /** userId */
    private String userId;

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
     * 获取roleId
     * 
     * @return roleId
     */
    public String getRoleId() {
        return this.roleId;
    }

    /**
     * 设置roleId
     * 
     * @param roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取userId
     * 
     * @return userId
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * 设置userId
     * 
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /* This code was generated by TableGo tools, mark 2 end. */
}