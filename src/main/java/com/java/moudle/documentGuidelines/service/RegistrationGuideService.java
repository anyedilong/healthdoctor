package com.java.moudle.documentGuidelines.service;


import java.util.List;

import com.java.moudle.common.domain.InitDictDto;
import com.java.moudle.common.service.BaseService;
import com.java.moudle.documentGuidelines.domain.RegistrationGuide;
import com.java.until.dba.PageModel;


public interface RegistrationGuideService extends BaseService<RegistrationGuide>{

	// 挂号指南列表，不分页
	List<RegistrationGuide> getGuideList(String startTime, String endTime, String classificationid);

	// 获取详情
	RegistrationGuide getGuideInfoById(String id);

	// 添加或修改挂号指南信息
	void saveOrUpdateGuideInfo(RegistrationGuide registrationGuide);

	// 删除
	void deleteOrFrozenGuideInfo(String id, String status);

	// 挂号指南分页
	void getGuideList(PageModel page, String startTime, String endTime, String classificationid);

	// 获取挂号分类
	List<InitDictDto> getGuideClassify();  
}
