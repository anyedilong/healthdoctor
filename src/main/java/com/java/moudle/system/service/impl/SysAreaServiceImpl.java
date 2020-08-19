package com.java.moudle.system.service.impl;


import java.util.List;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.SysAreaDao;
import com.java.moudle.system.domain.SysArea;
import com.java.moudle.system.service.SysAreaService;

@Named
@Transactional(readOnly = false)
public class SysAreaServiceImpl extends BaseServiceImpl<SysAreaDao, SysArea> implements SysAreaService {

	@Override
	public List<SysArea> getAreaList(SysArea area) throws Exception {
		return dao.getAreaList(area);
	}

	@Override
	public List<SysArea> getAreaListById(String id) {
		return dao.getAreaListById(id);
	}
    
}
