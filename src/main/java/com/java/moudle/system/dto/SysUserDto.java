package com.java.moudle.system.dto;

import java.io.Serializable;


public class SysUserDto implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 8493434709464L;

    /* This code was generated by TableGo tools, mark 1 begin. */
    private String id;
    private String username;//用户名
    private String password;//密码
    private String authorities;
    private String pwd;
    private String type;
    
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAuthorities() {
		return authorities;
	}
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}