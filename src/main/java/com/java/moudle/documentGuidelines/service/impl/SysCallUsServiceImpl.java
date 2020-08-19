package com.java.moudle.documentGuidelines.service.impl;


import java.util.List;

import javax.inject.Named;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.documentGuidelines.dao.SysCallUsDao;
import com.java.moudle.documentGuidelines.domain.SysCallUs;
import com.java.moudle.documentGuidelines.service.SysCallUsService;

@Named
public class SysCallUsServiceImpl extends BaseServiceImpl<SysCallUsDao, SysCallUs> implements SysCallUsService{

	@Override
	public List<SysCallUs> getSysCallUsInfo() {
		return dao.findList();
	}

	@Override
	public void addOrUpdateCallInfo(SysCallUs sysCallUs) {
		dao.save(sysCallUs);
	}
	
}
