package com.java.moudle.documentGuidelines.service.impl;

import java.util.Date;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.documentGuidelines.dao.HealthEducateInfoDao;
import com.java.moudle.documentGuidelines.domain.HealthEducateInfo;
import com.java.moudle.documentGuidelines.service.HealthEducateInfoService;
import com.java.until.StringUtil;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;


@Named
@Transactional(readOnly = false)
public class HealthEducateInfoServiceImpl extends BaseServiceImpl<HealthEducateInfoDao, HealthEducateInfo> implements HealthEducateInfoService{

	@Autowired
	HealthEducateInfoDao healthEducateInfoDao;
	
	@Override
	public void getHealthEducateInfoList(PageModel page, String startTime, String endTime, String hospitalId, String type) {
		healthEducateInfoDao.getHealthEducateInfoList(page, startTime, endTime, hospitalId, type);
	}

	@Override
	public HealthEducateInfo getHealthEducateInfo(String id) {
		return healthEducateInfoDao.getHealthEducateInfo(id);
	}

	@Override
	public void deleteHealthEducateInfo(String id) {
		healthEducateInfoDao.deleteHealthEducateInfo(id);
	}

	@Override
	public void addOrUpdateHealthEducateInfo(HealthEducateInfo healthEducateInfo) {
		if (StringUtil.isNull(healthEducateInfo.getId())) {
			healthEducateInfo.setId(UUIDUtil.getUUID());
		}
		healthEducateInfo.setUpdateTime(new Date());
		dao.save(healthEducateInfo);
	}

	@Override
	public void auditHealthEducateInfo(String id, String status) {
		healthEducateInfoDao.auditHealthEducateInfo(id, status);
	}
}
