package com.java.moudle.documentGuidelines.service;


import java.util.List;

import javax.inject.Named;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.documentGuidelines.domain.SysAboutUs;

@Named
public interface SysAboutUsService extends BaseService<SysAboutUs>{

	List<SysAboutUs> getSysAboutUsInfo();

	void addOrUpdateAboutInfo(SysAboutUs sysAboutUs);
	
}
