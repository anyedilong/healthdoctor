package com.java.moudle.system.service.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.SysOrgDao;
import com.java.moudle.system.domain.SysOrg;
import com.java.moudle.system.service.SysOrgService;


@Named
@Transactional(readOnly = false)
public class SysOrgServiceImpl extends BaseServiceImpl<SysOrgDao, SysOrg> implements SysOrgService{

	
	

}
