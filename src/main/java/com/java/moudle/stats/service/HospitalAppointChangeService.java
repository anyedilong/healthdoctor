package com.java.moudle.stats.service;

import java.util.List;
import java.util.Map;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.stats.domain.HospitalAppointChange;

public interface HospitalAppointChangeService  extends BaseService<HospitalAppointChange> {

	List<String> getAppointChangeList(String type, String year) throws Exception;
	
	Map<String, Object> getAppointChangeStats(HospitalAppointChange info, String username) throws Exception;
	
	Map<String, Object> getComplaintStats(HospitalAppointChange info) throws Exception;
}
