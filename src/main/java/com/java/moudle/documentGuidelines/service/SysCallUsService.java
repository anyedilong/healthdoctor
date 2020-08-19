package com.java.moudle.documentGuidelines.service;


import java.util.List;

import javax.inject.Named;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.documentGuidelines.domain.SysCallUs;

@Named
public interface SysCallUsService extends BaseService<SysCallUs>{

	List<SysCallUs> getSysCallUsInfo();

	void addOrUpdateCallInfo(SysCallUs sysCallUs);
	
}
