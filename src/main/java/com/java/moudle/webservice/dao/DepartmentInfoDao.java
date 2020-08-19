package com.java.moudle.webservice.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.stats.dto.DepartStatsDto;
import com.java.moudle.webservice.dao.repository.DepartmentInfoRepository;
import com.java.moudle.webservice.domain.DepartmentInfo;
import com.java.until.StringUtil;
import com.java.until.dba.BaseDao;
import com.java.until.dba.PageModel;

@Named
public class DepartmentInfoDao extends BaseDao<DepartmentInfoRepository, DepartmentInfo> {

	public void getDepartmentList(DepartmentInfo departmentInfo, PageModel page) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT name, LISTAGG( to_char(code), ',') WITHIN GROUP(ORDER BY code) AS code ");
		sql.append(" from department_info a ");
		sql.append(" where 1 = 1 ");
		if (!StringUtil.isNull(departmentInfo.getName())) {
			sql.append(" and a.name like concat(concat('%', :name), '%') ");
		}
		if (!StringUtil.isNull(departmentInfo.getHospitId())) {
			sql.append(" and a.hospit_id = :hospitId ");
		}
		if (!StringUtil.isNull(departmentInfo.getParentCode())) {
			sql.append(" and a.parent_code like concat(:parentCode, '%') ");
		}
		sql.append(" group by name ");
		queryPageList(sql.toString(), departmentInfo, page, DepartmentInfo.class);
	}
	
	public void getDepartmentInfoList(DepartmentInfo departmentInfo, PageModel page) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.id, a.name, a.code, ");
		sql.append(" (select count(1) from subscribe_info b  where b.doctor_id in ( select id from doctor_info where hospit_id = :hospitId and dept_code like concat(a.code, '%') )) as num ");
		sql.append(" from department_info a ");
		sql.append(" where a.parent_code = '0' ");
		if (!StringUtil.isNull(departmentInfo.getName())) {
			sql.append(" and a.name like concat(concat('%', :name), '%') ");
		}
		if (!StringUtil.isNull(departmentInfo.getHospitId())) {
			sql.append(" and a.hospit_id = :hospitId ");
		}
		sql.append(" order by num desc ");
		queryPageList(sql.toString(), departmentInfo, page, DepartmentInfo.class);
	}

	public DepartmentInfo getDepartmentDetail(DepartmentInfo departmentInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT name, to_char(a.introduce) as introduce, a.code ");
		sql.append(" from department_info a ");
		sql.append(" where 1 = 1 ");
		if (!StringUtil.isNull(departmentInfo.getCode())) {
			List<String> deptCodeList = Arrays.asList(departmentInfo.getCode().split(","));
			  sql.append(" and a.code in ( ");
	  		  for (int i = 0; deptCodeList.size() > i; i++) {
	  			  if (i == deptCodeList.size() - 1) {
					  sql.append("'" + deptCodeList.get(i) + "'");
				   } else {
					  sql.append("'" + deptCodeList.get(i) + "',");
				   }
			  }
			  sql.append(") ");
		}
		if (!StringUtil.isNull(departmentInfo.getHospitId())) {
			sql.append(" and a.hospit_id = :hospitId ");
		}
		return queryOne(sql.toString(), departmentInfo, DepartmentInfo.class);
	}

	public void getDepartStatsList(DepartStatsDto info, PageModel page) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" (select b.name from department_info b where b.code = a.parent_code and b.parent_code = '0') as departName, ");
		sql.append(" a.name as outName, ");
		sql.append(" (select count(1) from subscribe_info c where c.doctor_id in (select id from doctor_info b where a.code = b.dept_code and b.hospit_id = :hospitalId) ");
		if(!StringUtil.isNull(info.getStartDate())) {
			sql.append(" and to_date(to_char(c.create_time, 'yyyy-MM-dd'), 'yyyy-mm-dd') >= to_date(:startDate, 'yyyy-mm-dd') ");
		}
		if(!StringUtil.isNull(info.getEndDate())) {
			sql.append(" and to_date(to_char(c.create_time, 'yyyy-MM-dd'), 'yyyy-mm-dd') <= to_date(:endDate, 'yyyy-mm-dd') ");
		}
		sql.append(" ) as subNum, ");
		
		sql.append(" (select count(1) from subscribe_info c where c.status = '2' and c.doctor_id in (select id from doctor_info b where a.code = b.dept_code and b.hospit_id = :hospitalId ) ");
		if(!StringUtil.isNull(info.getStartDate())) {
			sql.append(" and to_date(to_char(c.create_time, 'yyyy-MM-dd'), 'yyyy-mm-dd') >= to_date(:startDate, 'yyyy-mm-dd') ");
		}
		if(!StringUtil.isNull(info.getEndDate())) {
			sql.append(" and to_date(to_char(c.create_time, 'yyyy-MM-dd'), 'yyyy-mm-dd') <= to_date(:endDate, 'yyyy-mm-dd') ");
		}
		sql.append(" ) as medNum, ");
		sql.append(" (select count(1) from subscribe_info c where c.status = '4' and c.doctor_id in (select id from doctor_info b where a.code = b.dept_code and b.hospit_id = :hospitalId) ");
		if(!StringUtil.isNull(info.getStartDate())) {
			sql.append(" and to_date(to_char(c.create_time, 'yyyy-MM-dd'), 'yyyy-mm-dd') >= to_date(:startDate, 'yyyy-mm-dd') ");
		}
		if(!StringUtil.isNull(info.getEndDate())) {
			sql.append(" and to_date(to_char(c.create_time, 'yyyy-MM-dd'), 'yyyy-mm-dd') <= to_date(:endDate, 'yyyy-mm-dd') ");
		}
		sql.append(" ) as canalNum, ");
		sql.append(" (select count(1) from subscribe_info c where c.status = '3' and c.doctor_id in (select id from doctor_info b where a.code = b.dept_code and b.hospit_id = :hospitalId) ");
		if(!StringUtil.isNull(info.getStartDate())) {
			sql.append(" and to_date(to_char(c.create_time, 'yyyy-MM-dd'), 'yyyy-mm-dd') >= to_date(:startDate, 'yyyy-mm-dd') ");
		}
		if(!StringUtil.isNull(info.getEndDate())) {
			sql.append(" and to_date(to_char(c.create_time, 'yyyy-MM-dd'), 'yyyy-mm-dd') <= to_date(:endDate, 'yyyy-mm-dd') ");
		}
		sql.append(" ) as overNum ");
		
		sql.append(" from department_info a ");
		sql.append(" where a.parent_code != 0 ");
		sql.append(" and a.hospit_id = :hospitalId ");
		if (!StringUtil.isNull(info.getDepartCode())) {
			sql.append(" and a.parent_code = :departCode ");
		}
		if (!StringUtil.isNull(info.getOutCode())) {
			sql.append(" and a.code = :outCode ");
		}
		queryPageList(sql.toString(), info, page, DepartStatsDto.class);
	}

	public List<DepartmentInfo> getDepartmentListNoPage(DepartmentInfo info) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT name, LISTAGG( to_char(code), ',') WITHIN GROUP(ORDER BY code) AS code ");
		sql.append(" from department_info a ");
		sql.append(" where 1 = 1 ");
		if (!StringUtil.isNull(info.getName())) {
			sql.append(" and a.name like concat(concat('%', :name), '%') ");
		}
		if (!StringUtil.isNull(info.getHospitId())) {
			sql.append(" and a.hospit_id = :hospitId ");
		}
		if (!StringUtil.isNull(info.getParentCode())) {
			sql.append(" and a.parent_code like concat(:parentCode, '%') ");
		}
		sql.append(" group by name ");
		return queryList(sql.toString(), info, DepartmentInfo.class);
	}
	
	public DepartmentInfo getDepartByHisAndHos(String hospitalId, String hisId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.* ");
		sql.append(" from department_info a ");
		sql.append(" where a.hospit_id = :hospitalId ");
		sql.append(" and a.his_id = :hisId ");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("hospitalId", hospitalId);
		paramMap.put("hisId", hisId);
		
		return queryOne(sql.toString(), paramMap, DepartmentInfo.class);
	}
}
