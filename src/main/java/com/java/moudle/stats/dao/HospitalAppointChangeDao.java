package com.java.moudle.stats.dao;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.stats.dao.repository.HospitalAppointChangeRepository;
import com.java.moudle.stats.domain.HospitalAppointChange;
import com.java.moudle.stats.dto.StatsResultDto;
import com.java.until.StringUtil;
import com.java.until.dba.BaseDao;

@Named
public class HospitalAppointChangeDao extends BaseDao<HospitalAppointChangeRepository, HospitalAppointChange> {

   
	public List<String> queryYearByAppointChange(){
	      StringBuffer sql = new StringBuffer();
	      sql.append(" select to_char(change_date, 'yyyy') ");
	      sql.append(" from hospital_appoint_change ");
	      sql.append(" group by to_char(change_date, 'yyyy') ");
	      return queryList(sql.toString(), null, String.class);
	 }
	
	public List<String> queryMonthByAppointChange(String year){
	      StringBuffer sql = new StringBuffer();
	      sql.append(" select t.changeMonth ");
	      sql.append(" from (select to_number(to_char(change_date, 'mm')) as changeMonth  ");
	      sql.append(" 		from hospital_appoint_change  ");
	      sql.append(" 		where to_char(change_date, 'yyyy') = :year ");
	      sql.append(" 		group by to_char(change_date, 'mm')) t ");
	      sql.append(" order by t.changeMonth ");
	      Map<String, Object> paramMap = new HashMap<>();
	      paramMap.put("year", year);
	      return queryList(sql.toString(), paramMap, String.class);
	 }
	
	public List<StatsResultDto> getHospitalChangeStats(HospitalAppointChange info){
	      StringBuffer sql = new StringBuffer();
	      sql.append(" select b.name as name, count(a.id) as value ");
	      sql.append(" from hospital_appoint_change a  ");
	      sql.append(" join department_info b on a.dept_code = b.code  ");
	      sql.append(" where 1 = 1 and b.hospit_id = :hospitalId ");
	      if(!StringUtil.isNull(info.getYear())) {
	    	  String changeTimeStr = coverChangeTime(info.getYear(), info.getMonth());
	    	  sql.append(" and to_char(a.change_date, 'yyyy-MM') = '"+changeTimeStr+"' ");
	      }
	      if(!StringUtil.isNull(info.getChangeType())) {
	    	  List<String> types = Arrays.asList(info.getChangeType().split(","));
  			  sql.append(" and a.change_type in ( ");
	  		  for (int i = 0; types.size() > i; i++) {
	  			  if (i == types.size() - 1) {
					  sql.append("'" + types.get(i) + "'");
				   } else {
					  sql.append("'" + types.get(i) + "',");
				   }
			  }
  			  sql.append(") ");
	      }
	      if(!StringUtil.isNull(info.getAreaCode())) {
	    	  sql.append(" and a.area_code like concat(:areaCode, '%') ");
	      }
	      sql.append(" group by b.name ");
	      return queryList(sql.toString(), info, StatsResultDto.class);
	 }
	
	public List<StatsResultDto> getAppointChangeStats(HospitalAppointChange info){
	      StringBuffer sql = new StringBuffer();
	      sql.append(" select hospital_name as name, count(id) as value ");
	      sql.append(" from hospital_appoint_change  ");
	      sql.append(" where 1 = 1 ");
	      if(!StringUtil.isNull(info.getYear())) {
	    	  String changeTimeStr = coverChangeTime(info.getYear(), info.getMonth());
	    	  sql.append(" and to_char(change_date, 'yyyy-MM') = '"+changeTimeStr+"' ");
	      }
	      if(!StringUtil.isNull(info.getAreaCode())) {
	    	  sql.append(" and area_code like concat(:areaCode, '%') ");
	      }
	      if(!StringUtil.isNull(info.getChangeType())) {
	    	  List<String> types = Arrays.asList(info.getChangeType().split(","));
			  sql.append(" and change_type in ( ");
	  		  for (int i = 0; types.size() > i; i++) {
	  			  if (i == types.size() - 1) {
					  sql.append("'" + types.get(i) + "'");
				   } else {
					  sql.append("'" + types.get(i) + "',");
				   }
			  }
			  sql.append(") ");
	      }
	      sql.append(" group by hospital_name ");
	      return queryList(sql.toString(), info, StatsResultDto.class);
	 }
	/**
	 * @Description: 获取投诉的统计
	 * @param @param info
	 * @param @return
	 * @return List<StatsResultDto>
	 * @throws
	 */
	public List<StatsResultDto> getComplaintStats(HospitalAppointChange info){
	      StringBuffer sql = new StringBuffer();
	      sql.append(" select d.name as name, count(b.id) as value , d.code deptCode");
	      sql.append(" from subscribe_info a ");
	      sql.append(" join subscribe_result b on a.id = b.subscribe_id ");
	      sql.append(" join doctor_info c on a.doctor_id = c.id ");
	      sql.append(" join department_info d on c.dept_code = d.code ");
	      sql.append(" join hospital_info e on e.id = c.hospit_id ");
	      sql.append(" where b.complaint_content is not null and e.status = '2' ");
	      sql.append(" and c.hospit_id = :hospitalId ");
	      if(!StringUtil.isNull(info.getYear()) && StringUtil.isNull(info.getMonth())) {
	    	  sql.append(" and to_char(b.create_time, 'yyyy') = '"+info.getYear()+"' ");
	      }
	      if(!StringUtil.isNull(info.getYear()) && !StringUtil.isNull(info.getMonth())) {
	    	  String changeTimeStr = coverChangeTime(info.getYear(), info.getMonth());
	    	  sql.append(" and to_char(b.create_time, 'yyyy-MM') = '"+changeTimeStr+"' ");
	      }
	      sql.append(" group by d.name, d.code ");
	      return queryList(sql.toString(), info, StatsResultDto.class);
	 }
	
	/**
	 * @Description: 转换查询时间
	 * @param @param year
	 * @param @param month
	 * @param @return
	 * @param @throws Exception
	 * @return Date
	 * @throws
	 */
	private String coverChangeTime(String year, String month){
		try {
			if(month.length() < 2) {
				month = "0" + month;
			}
			return year+"-"+month;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
