package com.java.moudle.documentGuidelines.service.impl;


import java.util.List;

import javax.inject.Named;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.documentGuidelines.dao.SysAboutUsDao;
import com.java.moudle.documentGuidelines.domain.SysAboutUs;
import com.java.moudle.documentGuidelines.service.SysAboutUsService;
import com.java.until.StringUtil;
import com.java.until.UUIDUtil;

@Named
public class SysAboutUsServiceImpl extends BaseServiceImpl<SysAboutUsDao, SysAboutUs> implements SysAboutUsService{

	@Override
	public List<SysAboutUs> getSysAboutUsInfo() {
		return dao.findList();
	}

	@Override
	public void addOrUpdateAboutInfo(SysAboutUs sysAboutUs) {
		if (StringUtil.isNull(sysAboutUs.getId())) {
			sysAboutUs.setId(UUIDUtil.getUUID());
		}
		dao.save(sysAboutUs);
	}
}
