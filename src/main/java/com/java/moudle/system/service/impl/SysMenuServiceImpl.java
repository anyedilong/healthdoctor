package com.java.moudle.system.service.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.SysMenuDao;
import com.java.moudle.system.domain.SysMenu;
import com.java.moudle.system.service.SysMenuService;


@Named
@Transactional(readOnly = false)
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDao, SysMenu> implements SysMenuService{

	@Override
	public List<SysMenu> getMenuTree(String userId) throws Exception {
		return dao.getMenuTree(userId);
	}
}
