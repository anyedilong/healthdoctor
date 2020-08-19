package com.java.moudle.documentGuidelines.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.java.moudle.documentGuidelines.dao.repository.SysLinksRepository;
import com.java.moudle.documentGuidelines.domain.SysLinks;
import com.java.until.StringUtil;
import com.java.until.dba.BaseDao;
import com.java.until.dba.PageModel;

@Named
public class SysLinksDao extends BaseDao<SysLinksRepository, SysLinks> {

	@Autowired
	SysLinksRepository sysLinksRepository;
	
	public void getSysLinksInfoList(PageModel page, String startTime, String endTime) {
		StringBuffer sql = new StringBuffer();
		Map<String, String> param = new HashMap<String, String>();
		sql.append(" select * from SYS_LINKS where status = 1 ");
		if (!StringUtil.isNull(startTime)) {
			sql.append(" and to_date(to_char(update_time, 'YYYY/MM/DD'), 'YYYY/MM/DD') >= to_date(:startTime, 'YYYY/MM/DD')");
			param.put("startTime", startTime);
		}
		
		if (!StringUtil.isNull(endTime)) {
			sql.append(" and to_date(to_char(update_time, 'YYYY/MM/DD'), 'YYYY/MM/DD') <= to_date(:endTime, 'YYYY/MM/DD')");
			param.put("endTime", endTime);
		}
		sql.append(" order by update_time desc");
		queryPageList(sql.toString(), param, page, SysLinks.class);
	}

	public SysLinks getSysLinksInfo(String id) {
		return sysLinksRepository.findById(id).get();
	}

	public void deleteSysLinksInfo(String id) {
		sysLinksRepository.deleteSysLinksInfo(id);
	}

	public List<SysLinks> getSysLinksInfoList(String startTime, String endTime) {
		StringBuffer sql = new StringBuffer();
		Map<String, String> param = new HashMap<String, String>();
		sql.append(" select * from SYS_LINKS where status = 1 ");
		if (!StringUtil.isNull(startTime)) {
			sql.append(" and to_date(to_char(update_time, 'YYYY/MM/DD'), 'YYYY/MM/DD') >= to_date(:startTime, 'YYYY/MM/DD')");
			param.put("startTime", startTime);
		}
		
		if (!StringUtil.isNull(endTime)) {
			sql.append(" and to_date(to_char(update_time, 'YYYY/MM/DD'), 'YYYY/MM/DD') <= to_date(:endTime, 'YYYY/MM/DD')");
			param.put("endTime", endTime);
		}
		sql.append(" order by update_time desc");
		return queryList(sql.toString(), param, SysLinks.class);
	}
	
}
