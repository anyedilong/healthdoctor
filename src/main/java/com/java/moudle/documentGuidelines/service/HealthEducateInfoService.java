package com.java.moudle.documentGuidelines.service;


import javax.inject.Named;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.documentGuidelines.domain.HealthEducateInfo;
import com.java.until.dba.PageModel;

@Named
public interface HealthEducateInfoService extends BaseService<HealthEducateInfo>{

	void getHealthEducateInfoList(PageModel page, String startTime, String endTime, String hospitalId, String type);

	HealthEducateInfo getHealthEducateInfo(String id);

	void deleteHealthEducateInfo(String id);

	void addOrUpdateHealthEducateInfo(HealthEducateInfo healthEducateInfo);

	void auditHealthEducateInfo(String id, String status);
	
}
