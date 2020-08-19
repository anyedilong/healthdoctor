package com.java.moudle.documentGuidelines.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.documentGuidelines.dao.SysLinksDao;
import com.java.moudle.documentGuidelines.domain.SysLinks;
import com.java.moudle.documentGuidelines.service.SysLinksService;
import com.java.until.StringUtil;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;


@Named
@Transactional(readOnly = false)
public class SysLinksServiceImpl extends BaseServiceImpl<SysLinksDao, SysLinks> implements SysLinksService{

	@Autowired
	SysLinksDao sysLinksDao;
	
	@Override
	public void getSysLinksInfoList(PageModel page, String startTime, String endTime) {
		sysLinksDao.getSysLinksInfoList(page, startTime, endTime);
	}

	@Override
	public SysLinks getSysLinksInfo(String id) {
		return sysLinksDao.getSysLinksInfo(id);
	}

	@Override
	public void deleteSysLinksInfo(String id) {
		sysLinksDao.deleteSysLinksInfo(id);
	}

	@Override
	public void addOrUpdateSysLinksInfo(SysLinks sysLinks) {
		if (StringUtil.isNull(sysLinks.getId())) {
			sysLinks.setId(UUIDUtil.getUUID());
			sysLinks.setStatus("1");
		}
		sysLinks.setUpdateTime(new Date());
		sysLinksDao.save(sysLinks);
	}

	@Override
	public List<SysLinks> getSysLinksInfoList(String startTime, String endTime) {
		return sysLinksDao.getSysLinksInfoList(startTime, endTime);
	}

}
