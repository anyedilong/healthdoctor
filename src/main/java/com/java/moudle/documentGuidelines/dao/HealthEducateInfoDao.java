package com.java.moudle.documentGuidelines.dao;


import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.java.moudle.documentGuidelines.dao.repository.HealthEducateInfoRepository;
import com.java.moudle.documentGuidelines.domain.HealthEducateInfo;
import com.java.moudle.system.domain.SysUser;
import com.java.until.StringUtil;
import com.java.until.SysUtil;
import com.java.until.dba.BaseDao;
import com.java.until.dba.PageModel;

@Named
public class HealthEducateInfoDao extends BaseDao<HealthEducateInfoRepository, HealthEducateInfo> {

	@Autowired
	HealthEducateInfoRepository healthEducateInfoRepository;
	
	public void getHealthEducateInfoList(PageModel page, String startTime, String endTime, String hospitalId, String type) {
		StringBuffer sql = new StringBuffer();
		Map<String, String> param = new HashMap<String, String>();
		sql.append(" select ID, TITLE, HOSPITAL_ID, SUBTITLE, IMAGE_URL, content, STATUS, UPDATE_USER, UPDATE_TIME from health_educate_info  where 1 = 1 ");
		if (!StringUtil.isNull(startTime)) {
			sql.append(" and to_date(to_char(update_time, 'YYYY/MM/DD'), 'YYYY/MM/DD') >= to_date(:startTime, 'YYYY/MM/DD')");
			param.put("startTime", startTime);
		}
		if (!StringUtil.isNull(endTime)) {
			sql.append(" and to_date(to_char(update_time, 'YYYY/MM/DD'), 'YYYY/MM/DD') <= to_date(:endTime, 'YYYY/MM/DD')");
			param.put("endTime", endTime);
		}
		if ("admin".equals(hospitalId)) {
			sql.append(" and (status in (1, 4, 6) or (UPDATE_USER = (select id from SYS_USER t where username = 'admin')) and status in 0)");
		} else if ("0".equals(hospitalId)) {
			sql.append(" and status = 1 and UPDATE_USER = (select id from SYS_USER t where username = 'admin')");
		} else  if (!StringUtil.isNull(hospitalId)) {
			// 网页登录
			if ("0".equals(type)) {
				sql.append(" and hospital_id = :hospitalId and status = 1");
				param.put("hospitalId", hospitalId);
			// 后台登录
			} else {
				sql.append(" and hospital_id = :hospitalId and status != 3");
				param.put("hospitalId", hospitalId);
			}
		} else {
			sql.append(" and status = 1");
		}
		sql.append(" order by update_time desc");
		queryPageList(sql.toString(), param, page, HealthEducateInfo.class);
	}

	public HealthEducateInfo getHealthEducateInfo(String id) {
		return healthEducateInfoRepository.findById(id).get();
	}

	public int deleteHealthEducateInfo(String id) {
		return healthEducateInfoRepository.deleteHealthEducateInfo(id);
	}

	public void auditHealthEducateInfo(String id, String status) {
		healthEducateInfoRepository.auditHealthEducateInfo(id, status);
	}
	
}
