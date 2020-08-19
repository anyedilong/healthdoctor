package com.java.moudle.documentGuidelines.service;


import java.util.List;

import javax.inject.Named;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.documentGuidelines.domain.SysLinks;
import com.java.until.dba.PageModel;

@Named
public interface SysLinksService extends BaseService<SysLinks>{

	public void getSysLinksInfoList(PageModel page, String startTime, String endTime);

	public SysLinks getSysLinksInfo(String id);

	public void deleteSysLinksInfo(String id);

	public void addOrUpdateSysLinksInfo(SysLinks sysLinks);

	public List<SysLinks> getSysLinksInfoList(String startTime, String endTime);
	
}
