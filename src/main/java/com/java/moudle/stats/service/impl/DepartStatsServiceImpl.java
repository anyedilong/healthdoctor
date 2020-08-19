package com.java.moudle.stats.service.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.stats.dto.DepartStatsDto;
import com.java.moudle.stats.service.DepartStatsService;
import com.java.moudle.webservice.dao.DepartmentInfoDao;
import com.java.moudle.webservice.domain.DepartmentInfo;
import com.java.until.dba.PageModel;

@Named
@Transactional(readOnly = false)
public class DepartStatsServiceImpl extends BaseServiceImpl<DepartmentInfoDao, DepartmentInfo> implements DepartStatsService{

	@Override
	public void getDepartStatsList(DepartStatsDto info, PageModel page) throws Exception {
		dao.getDepartStatsList(info, page);
	}

	
}
