package com.java.moudle.system.service.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.SysRoleUserDao;
import com.java.moudle.system.domain.SysRoleUser;
import com.java.moudle.system.service.SysRoleUserService;


@Named
@Transactional(readOnly = false)
public class SysRoleUserServiceImpl extends BaseServiceImpl<SysRoleUserDao, SysRoleUser> implements SysRoleUserService{

	
	
}
