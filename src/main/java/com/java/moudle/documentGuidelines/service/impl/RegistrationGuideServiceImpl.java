package com.java.moudle.documentGuidelines.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.domain.InitDictDto;
import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.documentGuidelines.dao.RegistrationGuideDao;
import com.java.moudle.documentGuidelines.domain.RegistrationGuide;
import com.java.moudle.documentGuidelines.service.RegistrationGuideService;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;
import com.java.until.dict.DictUtil;


@Named
@Transactional(readOnly = false)
public class RegistrationGuideServiceImpl extends BaseServiceImpl<RegistrationGuideDao, RegistrationGuide> implements RegistrationGuideService{

	@Autowired
	RegistrationGuideDao registrationGuideDao;
	
	@Override
	public List<RegistrationGuide> getGuideList(String startTime, String endTime, String classificationid) {
		return registrationGuideDao.getGuideList(startTime, endTime, classificationid);
	}

	@Override
	public void getGuideList(PageModel page, String startTime, String endTime, String classificationid) {
		registrationGuideDao.getGuideList(page, startTime, endTime, classificationid);
	}

	@Override
	public RegistrationGuide getGuideInfoById(String id) {
		return registrationGuideDao.getGuideInfoById(id);
	}

	@Override
	public void saveOrUpdateGuideInfo(RegistrationGuide registrationGuide) {
		if (registrationGuide.getId() == null || "0".equals(registrationGuide.getId())) { // 没有id为新增
			registrationGuide.setId(UUIDUtil.getUUID());
			registrationGuide.setStatus("1");
		}
		registrationGuide.setUpdateTime(new Date());
		dao.save(registrationGuide);
		
	}

	@Override
	public void deleteOrFrozenGuideInfo(String id, String status) {
		registrationGuideDao.deleteOrFrozenGuideInfo(id, status);
	}

	@Override
	public List<InitDictDto> getGuideClassify() {
		List<InitDictDto> list = DictUtil.getDict("1003");
		return list;
	}

}
