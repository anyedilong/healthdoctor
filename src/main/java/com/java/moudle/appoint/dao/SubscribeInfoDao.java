package com.java.moudle.appoint.dao;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.appoint.dao.repository.SubscribeInfoRepository;
import com.java.moudle.appoint.domain.SubscribeInfo;
import com.java.moudle.appoint.dto.SysSubscribeDto;
import com.java.until.StringUtil;
import com.java.until.dba.BaseDao;
import com.java.until.dba.PageModel;

@Named
public class SubscribeInfoDao extends BaseDao<SubscribeInfoRepository, SubscribeInfo> {
	
	public void getSubscribeInfoList(String userId, String startTime, String endTime, String ifevaluate, String status, PageModel page) {
		StringBuffer sql = new StringBuffer();
		Map<String, String> param = new HashMap<String, String>();
		sql.append(" select s.id yyid, s.DOCTOR_ID ysid, VISIT_TIME, s.MEDICAL_PERSONNEL_ID jzrid, m.phone,");
		sql.append(" s.STATUS status, s.CREATE_TIME, t.id pjid, t.EVALUATION_TYPE, t.EVALUATION_CONTENT,  ");
		sql.append(" t.COMPLAINT_CONTENT, m.NAME jzrName, d.NAME ysName, d.PROFESSIONAL, e.NAME deptName,  ");
		sql.append(" h.name HOSPITNAME, to_char(d.DEPICT) depict, to_char(d.INTRODUCE) introduck, d.IMAGE_URL, ");
		sql.append(" s.card_num as cardNum, ");
		sql.append(" (select w.address from hospital_info w where w.id = d.hospit_id) as address ");
		sql.append(" from subscribe_info s  ");
		sql.append(" left join subscribe_result t on s.id = t.SUBSCRIBE_ID ");
		sql.append(" left join medical_personnel m on m.id = s.MEDICAL_PERSONNEL_ID ");
		sql.append(" left join doctor_info d on d.id = s.DOCTOR_ID ");
		sql.append(" left join department_info e on d.DEPT_CODE = e.code ");
		sql.append(" left join hospital_info h on h.id = e.HOSPIT_ID where s.create_user = :userId ");
		
		if (!StringUtil.isNull(startTime)) {
			sql.append(" and to_date(to_char(s.CREATE_TIME, 'YYYY/MM/DD'), 'YYYY/MM/DD') >= to_date(:startTime, 'YYYY/MM/DD') ");
			param.put("startTime", startTime);
		}
		if (!StringUtil.isNull(endTime)) {
			sql.append(" and to_date(to_char(s.CREATE_TIME, 'YYYY/MM/DD'), 'YYYY/MM/DD') <= to_date(:endTime, 'YYYY/MM/DD') ");
			param.put("endTime", endTime);
		}
		if (!StringUtil.isNull(ifevaluate)) {
			sql.append(" and t.EVALUATION_TYPE is null and s.status = 2");
		}
		if (!StringUtil.isNull(status)) {
			sql.append(" and s.status = :status ");
			param.put("status", status);
		}
		sql.append(" order by s.CREATE_TIME desc");
		param.put("userId", userId);
		queryPageList(sql.toString(), param, page, Map.class);
	}
	
	public String getSubNumByHospitId(String hospitId){
	      StringBuffer sql = new StringBuffer();
	      sql.append(" select count(1) ");
	      sql.append(" from subscribe_info a ");
	      sql.append(" join doctor_info b on a.doctor_id = b.id ");
	      sql.append(" where b.hospit_id = :hospitId ");
	      sql.append(" and a.status != '4' ");
	      Map<String, Object> paramMap = new HashMap<>();
	      paramMap.put("hospitId", hospitId);
	      return queryOne(sql.toString(), paramMap, String.class);
	 }
	
	public String getSubNumByDoctorId(String doctorId){
	      StringBuffer sql = new StringBuffer();
	      sql.append(" select count(1) ");
	      sql.append(" from subscribe_info a ");
	      sql.append(" where a.doctor_id = :doctorId ");
	      sql.append(" and a.status != '4' ");
	      Map<String, Object> paramMap = new HashMap<>();
	      paramMap.put("doctorId", doctorId);
	      return queryOne(sql.toString(), paramMap, String.class);
	 }
	
	public String getSubNumByDeptCode(String hospitalId, String deptCode){
	      StringBuffer sql = new StringBuffer();
	      sql.append(" select count(1) ");
	      sql.append(" from subscribe_info a ");
	      sql.append(" where a.doctor_id in ( select id from doctor_info where hospit_id = :hospitalId and dept_code like concat(:deptCode, '%') ) ");
	      sql.append(" and a.status != '4' ");
	      Map<String, Object> paramMap = new HashMap<>();
	      paramMap.put("hospitalId", hospitalId);
	      paramMap.put("deptCode", deptCode);
	      return queryOne(sql.toString(), paramMap, String.class);
	 }
	
	
	public SubscribeInfo getDoctorInfoById(String subId){
	      StringBuffer sql = new StringBuffer();
	      sql.append(" select a.* ");
	      sql.append(" from subscribe_info a ");
	      sql.append(" where a.id = :subId ");
	      Map<String, Object> paramMap = new HashMap<>();
	      paramMap.put("subId", subId);
	      return queryOne(sql.toString(), paramMap, SubscribeInfo.class);
	 }

	@SuppressWarnings("unchecked")
	public Map<String, String> reservationStatistics(int year, String hospitId) {
		StringBuffer sql = new StringBuffer();
	    Map<String, Object> paramMap = new HashMap<>();
	    sql.append("select count(1) status1, count(case when status = 2 then 2 end) status2, count(case when status = 3 then 3 end) status3, count(case when status = 4 then 4 end) status4 from SUBSCRIBE_INFO where ");
	    sql.append("to_char(CREATE_TIME,'YYYY') = :year");
	    paramMap.put("year", year);

		if (!"admin".equals(hospitId)) {
		    if (!StringUtil.isNull(hospitId)) {
		    	sql.append(" and doctor_id in (select id from doctor_info where hospit_id = :hospitId)");
			    paramMap.put("hospitId", hospitId);
		    }
		}
		return queryOne(sql.toString(), paramMap, Map.class);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> reservationStatisticsEvery(int year, int month, String hospitId, String status) {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> paramMap = new HashMap<>();
		sql.append("select ");
		for (int m = 1; m <= 12; m++) {
			if (m != 1) {
				sql.append(" ,");
			}
			sql.append("count(case when to_char(CREATE_TIME, 'YYYY/MM') = '" + year + "/" + (m < 10 ? ("0" + m) : m) + "' then 1 end) month" + m + " ");
		}
		sql.append(" from subscribe_info where 1 = 1 ");
		if (!StringUtil.isNull(status) && !"1".equals(status)) {
			sql.append(" and status = :status ");
			paramMap.put("status", status);
		}
		if (!"admin".equals(hospitId)) {
			if (!StringUtil.isNull(hospitId)) {
				sql.append(" and doctor_id in (select id from doctor_info where hospit_id = :hospitId)");
				paramMap.put("hospitId", hospitId);
			}
		}
		return queryOne(sql.toString(), paramMap, Map.class);
	}

	// 一段时间内诊率统计
	@SuppressWarnings("unchecked")
	public Map<String, BigDecimal> getRecentVisitRate(String date, String date2, String hospitId) {
		StringBuffer sql = new StringBuffer();
	    Map<String, Object> paramMap = new HashMap<>();
	    sql.append("select round(count(case when to_char(CREATE_TIME, 'YYYY/MM/DD') > :date and to_char(CREATE_TIME, 'YYYY/MM/DD') <= :date2 and status = 2 then 1 end )/(case when count(case when to_char(CREATE_TIME, 'YYYY/MM/DD') > :date3 and to_char(CREATE_TIME, 'YYYY/MM/DD') <= :date4 then 1 end) = 0 then 1 else count(case when to_char(CREATE_TIME, 'YYYY/MM/DD') > :date5 and to_char(CREATE_TIME, 'YYYY/MM/DD') <= :date6 then 1 end) end) * 100, 2) count from SUBSCRIBE_INFO ");
	    paramMap.put("date", date);
	    paramMap.put("date2", date2);
	    paramMap.put("date3", date);
	    paramMap.put("date4", date2);
	    paramMap.put("date5", date);
	    paramMap.put("date6", date2);
		if (!"admin".equals(hospitId)) {
		    if (!StringUtil.isNull(hospitId)) {
		    	sql.append(" where doctor_id in (select id from doctor_info where hospit_id = :hospitId)");
			    paramMap.put("hospitId", hospitId);
		    }
		}
	    return queryOne(sql.toString(), paramMap, Map.class);
	}

	@SuppressWarnings("rawtypes")
	public List<Map> trendStatistics(List<String> monthList, String hospitId) {
		StringBuffer sql = new StringBuffer();
	    Map<String, Object> paramMap = new HashMap<>();
	    for (int i = 1; i <= 4; i++) {
	    	if (i == 1) {
			    sql.append(" select ");
	    	} else {
	    		sql.append(" UNION ALL select ");
	    	}
		    for (int a = 0; a < monthList.size(); a++) {
		    	if (a != 0) {
		    		sql.append(", ");
		    	}
			    sql.append(" count(case when to_date('" + monthList.get(a) + "', 'YYYY/MM/DD') = to_date(to_char(create_time, 'YYYY/MM/DD'), 'YYYY/MM/DD') then 1 end) DAY" + monthList.get(a).replace("/", ""));
		    }
		    if (i == 1) {
			    sql.append(" from SUBSCRIBE_INFO where 1 = 1 ");
		    } else {
			    sql.append(" from SUBSCRIBE_INFO where 1 = 1 and status = " + i);
		    }

			if (!"admin".equals(hospitId)) {
			    if (!StringUtil.isNull(hospitId)) {
			    	sql.append(" and doctor_id in (select id from doctor_info where hospit_id = :hospitId)");
				    paramMap.put("hospitId", hospitId);
			    }
			}
	    }
		return queryList(sql.toString(), paramMap, Map.class);
	}

	@SuppressWarnings("unchecked")
	public Map<String, BigDecimal> comparativeAnalysis(String hospitId, List<String> timeList, String status) {
		StringBuffer sql = new StringBuffer();
	    Map<String, Object> paramMap = new HashMap<>();
	    sql.append(" select ");
	    for (int a = 0; a < timeList.size(); a++) {
	    	if (a != 0) {
	    		sql.append(", ");
	    	}
    		sql.append("count(case when to_char(CREATE_TIME, 'YYYY/MM') = '" + timeList.get(a) +  "' then 1 end) date" + timeList.get(a).replace("/", ""));
	    }
	    sql.append(" from SUBSCRIBE_INFO where 1 = 1");
	    if (!StringUtil.isNull(status) && !"1".equals(status)) {
	    	sql.append(" and status = :status ");
		    paramMap.put("status", status);
	    }

		if (!"admin".equals(hospitId)) {
		    if (!StringUtil.isNull(hospitId)) {
		    	sql.append(" and doctor_id in (select id from doctor_info where hospit_id = :hospitId)");
			    paramMap.put("hospitId", hospitId);
		    }
	    }
		return queryOne(sql.toString(), paramMap, Map.class);
	}

	public void getEvaluateList(PageModel page, String time, String deptCode, String hospitalId) {
		StringBuffer sql = new StringBuffer(); 
		sql.append("select e.name dept_name, m.name persion_name, m.phone persion_phone, d.name doctor_name, t.complaint_content from subscribe_result t left join subscribe_info s on s.id = t.SUBSCRIBE_ID left join medical_personnel m on m.id = s.MEDICAL_PERSONNEL_ID left join doctor_info d on d.id = s.DOCTOR_ID left join department_info e on d.DEPT_CODE = e.code left join hospital_info h on h.id = e.HOSPIT_ID where t.complaint_content is not null and h.id = :hospitalId ");
		Map<String, Object> param = new HashMap<>();
		param.put("hospitalId", hospitalId);
		if (!StringUtil.isNull(time)) {
			sql.append(" and to_char(t.CREATE_TIME, 'YYYY/MM') = :time ");
			param.put("time", time);
		}
		if (!StringUtil.isNull(deptCode)) {
			sql.append(" and e.code = :deptCode ");
			param.put("deptCode", deptCode);
		}
		queryPageList(sql.toString(), param, page, Map.class);
	}

	public Map<String, BigDecimal> getEvaluateCount(String userId) {
		String sql = "select count(1) count from subscribe_info s left join subscribe_result t on s.id = t.SUBSCRIBE_ID left join medical_personnel m on m.id = s.MEDICAL_PERSONNEL_ID left join doctor_info d on d.id = s.DOCTOR_ID left join department_info e on d.DEPT_CODE = e.code left join hospital_info h on h.id = e.HOSPIT_ID where m.USER_ID = :userId and t.EVALUATION_TYPE is null and s.status = 2";
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		return queryOne(sql, param, Map.class);
	}
	
	public List<SubscribeInfo> getOverTimeSubList() {
		String sql = " select r.* from subscribe_info r where r.visit_time < sysdate and r.status = '1' ";
		return queryList(sql, null, SubscribeInfo.class);
	}
	
	public List<String> getOverTimeStatusList() {
		String sql = " select r.create_user from subscribe_info r where r.status = '3' and r.valid_flg = '1' group by r.create_user having count(id) > 2  ";
		return queryList(sql, null, String.class);
	}
	
	public void updateSubValidFlg(String userId, String flg) {
		repository.updateSubValidFlg(userId, flg);
	}
	
	public int getOverTimeStatusByUserId(String userId) {
		String sql = " select count(1) from subscribe_info r where r.status = '3' and r.valid_flg = '1' and r.create_user = :userId  ";
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		return queryOne(sql, param, Integer.class);
	}
	
	public SysSubscribeDto querySubscribeDetail(String subId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select i.name as doctorName, i.professional, h.name as hospitalName, d.name as deptName, ");
		sql.append(" r.visit_time, p.name, r.card_num, h.address, i.image_url, p.phone ");
		sql.append(" from subscribe_info r ");
		sql.append(" join doctor_info i on r.doctor_id = i.id ");
		sql.append(" join hospital_info h on i.hospit_id = h.id ");
		sql.append(" join department_info d on i.dept_code = d.code ");
		sql.append(" join medical_personnel p on r.medical_personnel_id = p.id ");
		sql.append(" where r.id = :subId ");
		
		Map<String, Object> param = new HashMap<>();
		param.put("subId", subId);
		return queryOne(sql.toString(), param, SysSubscribeDto.class);
	}
}
