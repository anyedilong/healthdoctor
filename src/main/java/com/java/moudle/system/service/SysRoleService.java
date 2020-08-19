package com.java.moudle.system.service;


import com.java.moudle.common.service.BaseService;
import com.java.moudle.system.domain.SysRole;


public interface SysRoleService extends BaseService<SysRole>{

	SysRole getRoleInfoByUserId(String userId);
  
}
