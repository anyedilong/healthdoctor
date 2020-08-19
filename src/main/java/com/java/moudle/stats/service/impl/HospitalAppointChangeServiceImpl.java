package com.java.moudle.stats.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.stats.dao.HospitalAppointChangeDao;
import com.java.moudle.stats.domain.HospitalAppointChange;
import com.java.moudle.stats.dto.StatsResultDto;
import com.java.moudle.stats.service.HospitalAppointChangeService;

@Named
@Transactional(readOnly = false)
public class HospitalAppointChangeServiceImpl extends BaseServiceImpl<HospitalAppointChangeDao, HospitalAppointChange> implements HospitalAppointChangeService{

	@Override
	public List<String> getAppointChangeList(String type, String year) throws Exception {
		List<String> list = new ArrayList<>();
		if("1".equals(type)) {
			//查询排班变更表中年份
			list = dao.queryYearByAppointChange();
		}else if("2".equals(type)) {
			//查询排班变更表中月份
			list = dao.queryMonthByAppointChange(year);
		}
		return list;
	}

	@Override
	public Map<String, Object> getAppointChangeStats(HospitalAppointChange info, String username) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		List<String> xList = new ArrayList<>();
		List<String> yList = new ArrayList<>();
		List<StatsResultDto> list = new ArrayList<>();
		if(!"admin".equals(username)) {
			//医疗机构管理员
			list = dao.getHospitalChangeStats(info);
		}else {
			//超级管理员
			list = dao.getAppointChangeStats(info);
		}
		if(list != null && list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				xList.add(list.get(i).getName());
				yList.add(list.get(i).getValue());
			}
		}
		resultMap.put("xList", xList);
		resultMap.put("yList", yList);
		return resultMap;
	}

	@Override
	public Map<String, Object> getComplaintStats(HospitalAppointChange info) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		List<String> xList = new ArrayList<>();
		List<String> yList = new ArrayList<>();
		List<String> codeList = new ArrayList<String>();
		
		//医疗机构管理员
		List<StatsResultDto> list = dao.getComplaintStats(info);
		if(list != null && list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				xList.add(list.get(i).getName());
				yList.add(list.get(i).getValue());
				codeList.add(list.get(i).getDeptCode());
			}
		}
		resultMap.put("xList", xList);
		resultMap.put("yList", yList);
		resultMap.put("codeList", codeList);
		return resultMap;
	}
	
	
}
