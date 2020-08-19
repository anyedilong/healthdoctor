package com.java.moudle.system.service;

import java.util.List;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.system.domain.SysArea;

public interface SysAreaService extends BaseService<SysArea> {


	List<SysArea> getAreaList(SysArea area) throws Exception;

	List<SysArea> getAreaListById(String id);
	
}
