package com.java.moudle.webservice.dao;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.webservice.dao.repository.DoctorInfoRepository;
import com.java.moudle.webservice.domain.DoctorInfo;
import com.java.until.StringUtil;
import com.java.until.dba.BaseDao;
import com.java.until.dba.PageModel;

@Named
public class DoctorInfoDao extends BaseDao<DoctorInfoRepository, DoctorInfo> {

   
	public void getDoctorList(DoctorInfo doctorInfo, PageModel page){
	      StringBuffer sql = new StringBuffer();
	      sql.append(" select distinct a.id, a.name, a.professional, a.outpat_type, a.dept_code, a.hospit_id, b.name as hospitalName, ");
	      sql.append(" to_char(a.depict) as depict, to_char(a.introduce) as introduce, a.image_url, a.status, a.his_id, c.name as deptName, c.his_id as deptId, ");
	      sql.append(" (select count(1) from subscribe_info d where d.doctor_id = a.id and a.status != '4') as count ");
	      sql.append(" from doctor_info a ");
	      sql.append(" join hospital_info b on a.hospit_id = b.id ");
	      sql.append(" join department_info c on a.dept_code = c.code ");
	      sql.append(" where 1 = 1 ");
	      if(!StringUtil.isNull(doctorInfo.getId())) {
	    	  sql.append(" and a.id = :id ");
	      }
	      if(!StringUtil.isNull(doctorInfo.getStatus())) {
	    	  sql.append(" and a.status = :status ");
	      }
	      if(!StringUtil.isNull(doctorInfo.getName())) {
	    	  sql.append(" and a.name like concat(concat('%', :name), '%') ");
	      }
	      if(!StringUtil.isNull(doctorInfo.getHospitId())) {
	    	  sql.append(" and a.hospit_id = :hospitId ");
	      }
	      if(!StringUtil.isNull(doctorInfo.getDepict())) {
	    	  sql.append(" and a.depict like concat(concat('%', :depict), '%') ");
	      }
	      if(!StringUtil.isNull(doctorInfo.getDeptCode())) {
	    	  List<String> deptCodeList = Arrays.asList(doctorInfo.getDeptCode().split(","));
  			  sql.append(" and (  ");
	  		  for (int i = 0; deptCodeList.size() > i; i++) {
	  			  if (i == deptCodeList.size() - 1 && deptCodeList.size() != 1) {
					  sql.append(" or a.dept_code like concat(" + deptCodeList.get(i) + ", '%')");
				   } else {
					  sql.append(" a.dept_code like concat(" + deptCodeList.get(i) + ", '%')");
				   }
			  }
  			 sql.append(") ");
	      }
	      if(!StringUtil.isNull(doctorInfo.getAreaCode())) {
	    	  sql.append(" and b.area_code like concat(:areaCode, '%') ");
	      }
	      sql.append(" order by count desc, status ");
	      queryPageList(sql.toString(), doctorInfo, page, DoctorInfo.class);
	 }
	 
	public String getDoctorNumByHospitId(String hospitId, String deptCode){
	      StringBuffer sql = new StringBuffer();
	      Map<String, Object> paramMap = new HashMap<>();
	      sql.append(" select count(1) ");
	      sql.append(" from doctor_info a ");
	      sql.append(" where 1 = 1 ");
	      if(!StringUtil.isNull(hospitId)) {
	    	  sql.append(" and a.hospit_id = :hospitId ");
	    	  paramMap.put("hospitId", hospitId);
	      }
	      if(!StringUtil.isNull(deptCode)) {
	    	  sql.append(" and a.dept_code like concat(:deptCode, '%') ");
	    	  paramMap.put("deptCode", deptCode);
	      }
	      return queryOne(sql.toString(), paramMap, String.class);
	 }

	public DoctorInfo getDoctorInfoById(String id) {
		HashMap<String, String> param = new HashMap<String, String>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id, a.name, a.professional, a.outpat_type, a.dept_code, a.hospit_id, b.name as hospitalName, ");
	      sql.append(" to_char(a.depict) as depict, to_char(a.introduce) as introduce, a.image_url, a.status, a.his_id, c.name as deptName, c.his_id as deptHisId ");
		sql.append(" from doctor_info a ");
		sql.append(" join hospital_info b on a.hospit_id = b.id ");
	    sql.append(" join department_info c on a.dept_code = c.code ");
	    sql.append(" where a.id = :id ");
		param.put("id", id);
		return queryOne(sql.toString(), param, DoctorInfo.class);
	}
	
	public DoctorInfo getDoctorInfoByHisId(String id, String deptCode, String hospitId) {
		HashMap<String, String> param = new HashMap<String, String>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id, a.name, a.professional, a.outpat_type, a.dept_code, a.hospit_id, b.name as hospitalName, ");
	      sql.append(" to_char(a.depict) as depict, to_char(a.introduce) as introduce, a.image_url, a.status, a.his_id, c.name as deptName ");
		sql.append(" from doctor_info a ");
		sql.append(" join hospital_info b on a.hospit_id = b.id ");
	    sql.append(" join department_info c on a.dept_code = c.code ");
	    sql.append(" where a.his_id = :id and b.id = :hospitId ");
	    sql.append(" and c.code = :deptCode ");
		param.put("id", id);
		param.put("deptCode", deptCode);
		param.put("hospitId", hospitId);
		return queryOne(sql.toString(), param, DoctorInfo.class);
	}

	public List<DoctorInfo> getFollowDoctorInfo(String id) {
		String sql = "select a.id, a.name, a.professional, a.outpat_type, a.dept_code, a.hospit_id, to_char(a.depict) as depict, to_char(a.introduce) as introduce, a.image_url, a.status, a.his_id, d.name deptName, h.name hospitalName from doctor_info a left join department_info d on a.dept_code = d.code left join hospital_info h on d.hospit_id = h.id right join doctor_concern c on a.id = c.doctor_id where c.user_id = :id";
		Map<String, String> param = new HashMap<String, String>();
		param.put("id", id);
		return queryList(sql, param, DoctorInfo.class);
	}

	public void getFollowDoctorList(DoctorInfo info, PageModel page) {
	    StringBuffer sql = new StringBuffer();
	    sql.append(" select distinct a.id, a.name, a.professional, a.outpat_type, a.dept_code, a.hospit_id, b.name as hospitalName, ");
	    sql.append(" to_char(a.depict) as depict, to_char(a.introduce) as introduce, a.image_url, a.status, a.his_id, c.name as deptName, c.his_id as deptId ");
	    sql.append(" from doctor_info a ");
	    sql.append(" join hospital_info b on a.hospit_id = b.id ");
	    sql.append(" join department_info c on a.dept_code = c.code ");
	    sql.append(" right join doctor_concern dc on a.id = dc.doctor_id ");
	    sql.append(" where 1 = 1 ");
	    if(!StringUtil.isNull(info.getId())) {
	   	  sql.append(" and a.id = :id ");
	    }
	    if(!StringUtil.isNull(info.getStatus())) {
	   	  sql.append(" and a.status = :status ");
	    }
	    if(!StringUtil.isNull(info.getName())) {
	   	  sql.append(" and a.name like concat(concat('%', :name), '%') ");
	    }
	    if(!StringUtil.isNull(info.getHospitId())) {
	   	  sql.append(" and a.hospit_id = :hospitId ");
	    }
	    if(!StringUtil.isNull(info.getDepict())) {
	    	  sql.append(" and a.depict like concat(concat('%', :depict), '%') ");
	      }
	    if (!StringUtil.isNull(info.getUserId())) {
	    	sql.append(" and dc.user_id = :userId ");
	    }
	    if(!StringUtil.isNull(info.getDeptCode())) {
	   	  List<String> deptCodeList = Arrays.asList(info.getDeptCode().split(","));
		  sql.append(" and ( ");
	  	  for (int i = 0; deptCodeList.size() > i; i++) {
	  		  if (i == deptCodeList.size() - 1 && deptCodeList.size() != 1) {
				  sql.append(" or a.dept_code like concat(" + deptCodeList.get(i) + ", '%')");
			   } else {
				  sql.append(" a.dept_code like concat(" + deptCodeList.get(i) + ", '%')");
			   }
		  }
		 sql.append(" ) ");
	    }
	    if (!StringUtil.isNull(info.getAreaCode())) {
	    	sql.append(" and b.area_code like concat(:areaCode, '%') ");
	    }
	    sql.append(" order by status ");
	    queryPageList(sql.toString(), info, page, DoctorInfo.class);
	}
	
	public static void main(String[] args) {
		StringBuffer sql = new StringBuffer();
	    sql.append(" select distinct a.id, a.name, a.professional, a.outpat_type, a.dept_code, a.hospit_id, b.name as hospitalName, ");
	    sql.append(" to_char(a.depict) as depict, to_char(a.introduce) as introduce, a.image_url, a.status, a.his_id, c.name as deptName ");
	    sql.append(" from doctor_info a ");
	    sql.append(" join hospital_info b on a.hospit_id = b.id ");
	    sql.append(" join department_info c on a.dept_code = c.code ");
	    System.out.println(sql.toString());
	}

	public List<DoctorInfo> getDoctorListNoPage(DoctorInfo info) {
	      StringBuffer sql = new StringBuffer();
	      sql.append(" select distinct a.id, a.name, a.professional, a.outpat_type, a.dept_code, a.hospit_id, b.name as hospitalName, ");
	      sql.append(" to_char(a.depict) as depict, to_char(a.introduce) as introduce, a.image_url, a.status, a.his_id, c.name as deptName ");
	      sql.append(" from doctor_info a ");
	      sql.append(" join hospital_info b on a.hospit_id = b.id ");
	      sql.append(" join department_info c on a.dept_code = c.code ");
	      sql.append(" where 1 = 1 ");
	      if(!StringUtil.isNull(info.getId())) {
	    	  sql.append(" and a.id = :id ");
	      }
	      if(!StringUtil.isNull(info.getStatus())) {
	    	  sql.append(" and a.status = :status ");
	      }
	      if(!StringUtil.isNull(info.getName())) {
	    	  sql.append(" and a.name like concat(concat('%', :name), '%') ");
	      }
	      if(!StringUtil.isNull(info.getHospitId())) {
	    	  sql.append(" and a.hospit_id = :hospitId ");
	      }
	      if(!StringUtil.isNull(info.getDepict())) {
	    	  sql.append(" and a.depict like concat(concat('%', :depict), '%') ");
	      }
	      if(!StringUtil.isNull(info.getDeptCode())) {
	    	  List<String> deptCodeList = Arrays.asList(info.getDeptCode().split(","));
			  sql.append(" and (  ");
	  		  for (int i = 0; deptCodeList.size() > i; i++) {
	  			  if (i == deptCodeList.size() - 1 && deptCodeList.size() != 1) {
					  sql.append(" or a.dept_code like concat(" + deptCodeList.get(i) + ", '%')");
				   } else {
					  sql.append(" a.dept_code like concat(" + deptCodeList.get(i) + ", '%')");
				   }
			  }
			 sql.append(") ");
	      }
	      sql.append(" order by status ");
	      return queryList(sql.toString(), info, DoctorInfo.class);
	}
	
	public DoctorInfo getDoctorByHisAndHos(String hospitId, String hisId){
	      StringBuffer sql = new StringBuffer();
	      Map<String, Object> paramMap = new HashMap<>();
	      sql.append(" select a.* ");
	      sql.append(" from doctor_info a ");
	      sql.append(" where a.hospit_id = :hospitId ");
	      sql.append(" and a.his_id = :hospitId ");
	      
	      paramMap.put("hospitId", hospitId);
	      paramMap.put("hisId", hisId);
	      return queryOne(sql.toString(), paramMap, DoctorInfo.class);
	 }
}
