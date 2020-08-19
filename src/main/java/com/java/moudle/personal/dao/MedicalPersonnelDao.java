package com.java.moudle.personal.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.personal.dao.repository.MedicalPersonnelRepository;
import com.java.moudle.personal.domain.MedicalPersonnel;
import com.java.until.dba.BaseDao;
import org.apache.commons.lang3.StringUtils;

@Named
public class MedicalPersonnelDao extends BaseDao<MedicalPersonnelRepository, MedicalPersonnel> {

	public List<MedicalPersonnel> getPatientList(String userId) {
		String sql = "select id, name, sfzh, user_id, phone, address_province, ADDRESS_CITY, ADDRESS_DETAIL, PATIENT_SIGN, STATUS, UPDATE_USER, UPDATE_TIME, case when XB = '1' then '男' else '女' end as xb, ADDRESS_REGION,ADDRESS_STREET, (select area_name from SYS_AREA where id = m.ADDRESS_PROVINCE) addressProvinceName, (select area_name from SYS_AREA where id = m.ADDRESS_CITY) addressCityName, (select area_name from SYS_AREA where id = m.ADDRESS_REGION) addressRegionName, (select area_name from SYS_AREA where id = m.ADDRESS_STREET) addressStreetName from medical_personnel m where user_id = :userId and status = 1 order by patient_sign desc, update_time desc";
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId);
		return queryList(sql, param, MedicalPersonnel.class);
	}
	
	public List<MedicalPersonnel> getHcPatientList(String userId, String hospitalId) {
		StringBuffer sql = new StringBuffer();
		Map<String, String> param = new HashMap<String, String>();
		sql.append(" select m.id, m.name, m.sfzh, m.user_id, m.phone, m.address_province, m.ADDRESS_CITY, ");
		sql.append(" m.ADDRESS_DETAIL, m.PATIENT_SIGN, m.STATUS, m.UPDATE_USER, m.UPDATE_TIME, case when m.XB = '1' then '男' else '女' end as xb, m.ADDRESS_REGION, ");
		sql.append(" m.ADDRESS_STREET, (select area_name from SYS_AREA where id = m.ADDRESS_PROVINCE) addressProvinceName, ");
		sql.append(" (select area_name from SYS_AREA where id = m.ADDRESS_CITY) addressCityName, ");
		sql.append(" (select area_name from SYS_AREA where id = m.ADDRESS_REGION) addressRegionName, ");
		sql.append(" (select area_name from SYS_AREA where id = m.ADDRESS_STREET) addressStreetName,  ");
		sql.append(" (select a.card_num from patient_card a where m.id = a.mp_id and a.hospital_id = :hospitalId) as cardNum ");
		sql.append(" from medical_personnel m  ");
		sql.append(" where m.user_id = :userId and m.status = 1  ");
		sql.append(" order by m.patient_sign desc, m.update_time desc ");
		if (StringUtils.isNotBlank(hospitalId))
			param.put("hospitalId", hospitalId);
		param.put("userId", userId);
		return queryList(sql.toString(), param, MedicalPersonnel.class);
	}
	
	public MedicalPersonnel getHcPatientDefaultInfo(String userId, String hospitalId) {
		StringBuffer sql = new StringBuffer();
		Map<String, String> param = new HashMap<String, String>();
		sql.append(" select m.id, m.name, m.sfzh, m.user_id, m.phone, m.address_province, m.ADDRESS_CITY, ");
		sql.append(" m.ADDRESS_DETAIL, m.PATIENT_SIGN, m.STATUS, m.UPDATE_USER, m.UPDATE_TIME, case when m.XB = '1' then '男' else '女' end as xb, m.ADDRESS_REGION, ");
		sql.append(" m.ADDRESS_STREET, (select area_name from SYS_AREA where id = m.ADDRESS_PROVINCE) addressProvinceName, ");
		sql.append(" (select area_name from SYS_AREA where id = m.ADDRESS_CITY) addressCityName, ");
		sql.append(" (select area_name from SYS_AREA where id = m.ADDRESS_REGION) addressRegionName, ");
		sql.append(" (select area_name from SYS_AREA where id = m.ADDRESS_STREET) addressStreetName,  ");
		sql.append(" (select a.card_num from patient_card a where m.id = a.mp_id and a.hospital_id = :hospitalId) as cardNum ");
		sql.append(" from medical_personnel m  ");
		sql.append(" where m.user_id = :userId and m.status = 1 and patient_sign = 1 ");
		sql.append(" order by m.patient_sign desc, m.update_time desc ");
		param.put("hospitalId", hospitalId);
		param.put("userId", userId);
		return queryOne(sql.toString(), param, MedicalPersonnel.class);
	}
	
	public MedicalPersonnel getHcPatientBaseInfo(String id, String hospitalId) {
		StringBuffer sql = new StringBuffer();
		Map<String, String> param = new HashMap<String, String>();
		sql.append(" select m.id, m.name, m.sfzh, m.user_id, m.phone, m.address_province, m.ADDRESS_CITY, ");
		sql.append(" m.ADDRESS_DETAIL, m.PATIENT_SIGN, m.STATUS, m.UPDATE_USER, m.UPDATE_TIME, case when m.XB = '1' then '男' else '女' end as xb, m.ADDRESS_REGION, ");
		sql.append(" m.ADDRESS_STREET, (select area_name from SYS_AREA where id = m.ADDRESS_PROVINCE) addressProvinceName, ");
		sql.append(" (select area_name from SYS_AREA where id = m.ADDRESS_CITY) addressCityName, ");
		sql.append(" (select area_name from SYS_AREA where id = m.ADDRESS_REGION) addressRegionName, ");
		sql.append(" (select area_name from SYS_AREA where id = m.ADDRESS_STREET) addressStreetName,  ");
		sql.append(" (select a.card_num from patient_card a where m.id = a.mp_id and a.hospital_id = :hospitalId) as cardNum ");
		sql.append(" from medical_personnel m  ");
		sql.append(" where m.id = :id and m.status = 1  ");
		sql.append(" order by m.patient_sign desc, m.update_time desc ");
		param.put("hospitalId", hospitalId);
		param.put("id", id);
		return queryOne(sql.toString(), param, MedicalPersonnel.class);
	}
	
	public int isExist(String sfzh, String phone, String userId) {
		StringBuffer sql = new StringBuffer();
		Map<String, String> param = new HashMap<String, String>();
		sql.append(" select count(m.id) ");
		sql.append(" from medical_personnel m  ");
		sql.append(" where m.user_id = :userId and m.status = 1  ");
		//sql.append(" and (m.sfzh = :sfzh or m.phone = :phone) ");
		sql.append(" and (m.sfzh = :sfzh) ");
		param.put("sfzh", sfzh);
		//param.put("phone", phone);
		param.put("userId", userId);
		return queryOne(sql.toString(), param, Integer.class);
	}
	
	public MedicalPersonnel getPatientDefaultInfo(String userId) {
		StringBuffer sql = new StringBuffer();
		Map<String, String> param = new HashMap<String, String>();
		sql.append(" select * from medical_personnel where user_id = :userId and status = 1 and patient_sign = 1 ");
		param.put("userId", userId);
		return queryOne(sql.toString(), param, MedicalPersonnel.class);
	}
	
	public MedicalPersonnel getPatientBaseInfo(String id) {
		StringBuffer sql = new StringBuffer();
		Map<String, String> param = new HashMap<String, String>();
		sql.append(" select * from medical_personnel where id = :id and status = 1 ");
		param.put("id", id);
		return queryOne(sql.toString(), param, MedicalPersonnel.class);
	}
	
	public MedicalPersonnel getPatientBaseInfoBySfzh(String sfzh) {
		StringBuffer sql = new StringBuffer();
		Map<String, String> param = new HashMap<String, String>();
		sql.append(" select * from medical_personnel where sfzh = :sfzh and user_id = :userId and status = 1 ");
		param.put("sfzh", sfzh);
		return queryOne(sql.toString(), param, MedicalPersonnel.class);
	}
}
