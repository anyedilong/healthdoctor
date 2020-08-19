package com.java.moudle.system.service.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.SysRoleDao;
import com.java.moudle.system.domain.SysRole;
import com.java.moudle.system.service.SysRoleService;


@Named
@Transactional(readOnly = false)
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDao, SysRole> implements SysRoleService{

	@Override
	public SysRole getRoleInfoByUserId(String userId) {
		return dao.getRoleInfoByUserId(userId);
	}

}
