package com.java.moudle.appoint.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.appoint.dao.repository.DoctorConcernRepository;
import com.java.moudle.appoint.domain.DoctorConcern;
import com.java.until.dba.BaseDao;

@Named
public class DoctorConcernDao extends BaseDao<DoctorConcernRepository, DoctorConcern> {

	public String queryConcernInfo(String doctorId, String userId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) ");
		sql.append(" from doctor_concern a ");
		sql.append(" where a.doctor_id = :doctorId ");
		sql.append(" and a.user_id = :userId ");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("doctorId", doctorId);
		paramMap.put("userId", userId);
		return queryOne(sql.toString(), paramMap, String.class);
	}

	public void quitDoctorConcern(String doctorId, String userId) {
		repository.quitDoctorConcern(doctorId, userId);
	}

	public String getConcernNumByDoctorId(String doctorId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) ");
		sql.append(" from doctor_concern a ");
		sql.append(" where a.doctor_id = :doctorId ");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("doctorId", doctorId);
		return queryOne(sql.toString(), paramMap, String.class);
	}

}
