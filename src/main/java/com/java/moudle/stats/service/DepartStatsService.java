package com.java.moudle.stats.service;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.stats.dto.DepartStatsDto;
import com.java.moudle.webservice.domain.DepartmentInfo;
import com.java.until.dba.PageModel;

public interface DepartStatsService extends BaseService<DepartmentInfo> {

	void getDepartStatsList(DepartStatsDto info, PageModel pag) throws Exception;
}
