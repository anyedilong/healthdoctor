package com.java.moudle.appoint.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.java.moudle.appoint.domain.SubscribeInfo;
import com.java.moudle.appoint.domain.SubscribeResult;
import com.java.moudle.appoint.dto.SysSubscribeDto;
import com.java.moudle.common.domain.InitDictDto;
import com.java.moudle.common.service.BaseService;
import com.java.until.dba.PageModel;

public interface SubscribeService  extends BaseService<SubscribeInfo> {

	void docterEvaluate(SubscribeResult subscribeResult);
	//获取某个医院的预约总数
	String getSubNumByHospitId(String hospitId) throws Exception;

	Map<String, String> reservationStatistics(int year, String hospitId);

	Map<String, String> reservationStatisticsEvery(int year, int month, String hospitId, String status);

	List<String> recentVisitRate(String hospitId);

	Map<String, List> trendStatistics(String hospitId);

	Map<String, List<Integer>> comparativeAnalysis(String hospitId, String status);
	//根据医生标识获取预约的数量
	Map<String, Object> getSubNumAndConcernNum(String doctorId);
	
	void getSubscribeInfoList(String userId, String startTime, String endTime, String ifevaluate, String status, PageModel page);
	
	List<InitDictDto> getComment();
	// 获取评论列表
	void getEvaluateList(PageModel page, String time, String deptCode, String hospitalId);
	// 未评价数量
	BigDecimal getEvaluateCount(String userId);
	
	List<SubscribeInfo> getOverTimeSubList();
	List<String> getOverTimeStatusList();
	int getOverTimeStatusByUserId(String userId);
	void updateSubValidFlg(String userId, String vailFlg);
	SysSubscribeDto querySubscribeDetail(String subId);
}
