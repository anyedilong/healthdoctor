package com.java.moudle.documentGuidelines.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.java.moudle.documentGuidelines.dao.repository.RegistrationGuideRepository;
import com.java.moudle.documentGuidelines.domain.RegistrationGuide;
import com.java.until.StringUtil;
import com.java.until.dba.BaseDao;
import com.java.until.dba.PageModel;

@Named
public class RegistrationGuideDao extends BaseDao<RegistrationGuideRepository, RegistrationGuide> {

	@Autowired
	RegistrationGuideRepository registrationGuideRepository;
	
	public List<RegistrationGuide> getGuideList(String startTime, String endTime, String classificationid) {
		StringBuffer sql = new StringBuffer();
		Map<String, String> param = new HashMap<String, String>();
		sql.append("select t.id, t.title, t.subtitle, t.content content, t.status, t.update_user, t.update_time, classificationid from REGISTRATION_GUIDE t where status = 1");
		if (!StringUtil.isNull(startTime)) {
			sql.append(" and to_date(to_char(update_time, 'YYYY/MM/DD'), 'YYYY/MM/DD') >= to_date(:startTime, 'YYYY/MM/DD') ");
			param.put("startTime", startTime);
		}
		if (!StringUtil.isNull(endTime)) {
			sql.append(" and to_date(to_char(update_time, 'YYYY/MM/DD'), 'YYYY/MM/DD') <= to_date(:endTime, 'YYYY/MM/DD') ");
			param.put("endTime", endTime);
		}
		if (!StringUtil.isNull(classificationid)) {
			sql.append(" and CLASSIFICATIONID = :classificationid ");
			param.put("classificationid", classificationid);
		}
		sql.append(" order by update_time desc");
		return queryList(sql.toString(), param, RegistrationGuide.class);
	}

	public void getGuideList(PageModel page, String startTime, String endTime, String classificationid) {
		StringBuffer sql = new StringBuffer();
		Map<String, String> param = new HashMap<String, String>();
		sql.append("select t.id, t.title, t.subtitle, t.content content, t.status, t.update_user, t.update_time, classificationid from REGISTRATION_GUIDE t where status = 1");
		if (!StringUtil.isNull(startTime)) {
			sql.append(" and to_date(to_char(update_time, 'YYYY/MM/DD'), 'YYYY/MM/DD') >= to_date(:startTime, 'YYYY/MM/DD') ");
			param.put("startTime", startTime);
		}
		if (!StringUtil.isNull(endTime)) {
			sql.append(" and to_date(to_char(update_time, 'YYYY/MM/DD'), 'YYYY/MM/DD') <= to_date(:endTime, 'YYYY/MM/DD') ");
			param.put("endTime", endTime);
		}
		if (!StringUtil.isNull(classificationid)) {
			sql.append(" and CLASSIFICATIONID = :classificationid ");
			param.put("classificationid", classificationid);
		}
		sql.append(" order by update_time desc");
		queryPageList(sql.toString(), param, page, RegistrationGuide.class);
	}

	public RegistrationGuide getGuideInfoById(String id) {
		StringBuffer sql = new StringBuffer();
		Map<String, String> param = new HashMap<String, String>();
		sql.append("select t.id, t.title, t.subtitle, t.content content, t.status, t.update_user, t.update_time, t.classificationid from REGISTRATION_GUIDE t where status = 1 and id = :id");
		param.put("id", id);
		return queryOne(sql.toString(), param, RegistrationGuide.class);
	}
	
	public void deleteOrFrozenGuideInfo(String id, String status) {
		registrationGuideRepository.deleteOrFrozenGuideInfo(id, status);
	}
	 
}
