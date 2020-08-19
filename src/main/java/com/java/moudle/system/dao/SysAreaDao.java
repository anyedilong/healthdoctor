package com.java.moudle.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.system.dao.repository.SysAreaRepository;
import com.java.moudle.system.domain.SysArea;
import com.java.until.StringUtil;
import com.java.until.dba.BaseDao;


@Named
public class SysAreaDao extends BaseDao<SysAreaRepository, SysArea> {

	public List<SysArea> getAreaList(SysArea area){
	      StringBuffer sql = new StringBuffer();
	      sql.append(" select u.* ");
	      sql.append(" from sys_area u ");
	      sql.append(" where u.status = '1' and u.area_level = '3' ");
	      if(!StringUtil.isNull(area.getAreaCode())) {
	    	  sql.append(" and u.area_code like concat(:areaCode, '%') ");
	      }
	      return queryList(sql.toString(), area, SysArea.class);

	 }

	public List<SysArea> getAreaListById(String id) {
		String sql = "select * from SYS_AREA where parent_id = :parentId and status = 1";
		Map<String, String> param = new HashMap<String, String>();
		param.put("parentId", id);
		return queryList(sql, param, SysArea.class);
	}
   
}
