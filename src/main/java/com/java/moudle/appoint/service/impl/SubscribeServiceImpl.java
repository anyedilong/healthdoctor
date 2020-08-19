package com.java.moudle.appoint.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.appoint.dao.DoctorConcernDao;
import com.java.moudle.appoint.dao.SubscribeInfoDao;
import com.java.moudle.appoint.dao.SubscribeResultDao;
import com.java.moudle.appoint.domain.SubscribeInfo;
import com.java.moudle.appoint.domain.SubscribeResult;
import com.java.moudle.appoint.dto.SysSubscribeDto;
import com.java.moudle.appoint.service.SubscribeService;
import com.java.moudle.common.domain.InitDictDto;
import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.until.DateUntil;
import com.java.until.StringUtil;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;
import com.java.until.dict.DictUtil;

@Named
@Transactional(readOnly = false)
public class SubscribeServiceImpl  extends BaseServiceImpl<SubscribeInfoDao, SubscribeInfo> implements SubscribeService{

	@Autowired
	private SubscribeResultDao subscribeResultDao;
	@Autowired
	private SubscribeInfoDao subscribeInfoDao;
	@Autowired
	private DoctorConcernDao doctorConcernDao;

	@Override
	public void docterEvaluate(SubscribeResult subscribeResult) {
		// 判断是新增的还是已有的 新的新增
		if (StringUtil.isNull(subscribeResult.getId()) ||"0".equals(subscribeResult.getId()) || "undefined".equals(subscribeResult.getId())) {
			subscribeResult.setId(UUIDUtil.getUUID());
			subscribeResult.setCreateTime(new Date());
			subscribeResultDao.save(subscribeResult);
		} else {
			// 已有评论 更新
			if (!StringUtil.isNull(subscribeResult.getEvaluationType()) && !StringUtil.isNull(subscribeResult.getEvaluationContent())) {
				subscribeResultDao.updateDocterEvaluate(subscribeResult);
			} else if (!StringUtil.isNull(subscribeResult.getComplaintContent())) {
				subscribeResultDao.updateDocterComplaint(subscribeResult);
			}
		}
	}

	@Override
	public String getSubNumByHospitId(String hospitId) throws Exception {
		return dao.getSubNumByHospitId(hospitId);
	}

	@Override
	public Map<String, String> reservationStatistics(int year, String hospitId) {
		return subscribeInfoDao.reservationStatistics(year, hospitId);
	}

	@Override
	public Map<String, String> reservationStatisticsEvery(int year, int month, String hospitId, String status) {
		return subscribeInfoDao.reservationStatisticsEvery(year, month, hospitId, status);
	}

	@Override
	public List<String> recentVisitRate(String hospitId) {
		List<String> list = new ArrayList<String>();
		list.add(subscribeInfoDao.getRecentVisitRate(DateUntil.getDateCalDay(-7), DateUntil.getDateCalDay(0), hospitId).get("COUNT").toString());
		// 获取一月内就诊率
		list.add(subscribeInfoDao.getRecentVisitRate(DateUntil.getDateCalMonth(-1), DateUntil.getDateCalMonth(0), hospitId).get("COUNT").toString());
		// 获取6月内就诊率
		list.add(subscribeInfoDao.getRecentVisitRate(DateUntil.getDateCalMonth(-6), DateUntil.getDateCalMonth(0), hospitId).get("COUNT").toString());
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, List> trendStatistics(String hospitId) {
		// 返回值
		Map<String, List> resultMap = new HashMap<String, List>();
		
		// 月份列表
		List<String> monthList = new ArrayList<String>();
		String todayStart = DateUntil.getDateCalMonth(-1);
		for (int a = 0; !DateUntil.getDateCalDay(a).equals(todayStart); a--) {
			String time = DateUntil.getDateCalDay(a - 1);
			monthList.add(time);
		}
		
		List<Map> mapResult = subscribeInfoDao.trendStatistics(monthList, hospitId);
		
		// 格式转换
		for (int status = 1; status <= 4; status++) {
			ArrayList list = new ArrayList();
			for (int a = 0; a < monthList.size(); a++) {
				Map<String, BigDecimal> r = mapResult.get(status - 1);
				list.add(Integer.valueOf((r.get("DAY" + monthList.get(a).replace("/", ""))).toString()));
			}
			Collections.reverse(list); // 倒序排列 
			resultMap.put("status" + status, list);
		}
		Collections.reverse(monthList); // 倒序排列 
		resultMap.put("monthList", monthList);
		return resultMap;
	}

	@Override
	public Map<String, List<Integer>> comparativeAnalysis(String hospitId, String status) {
		// 返回结果
		Map<String, List<Integer>> resultMap = new HashMap<String, List<Integer>>();
		List<Integer> list = new ArrayList<Integer>();
		
		// 去年
		List<String> timeList = new ArrayList<String>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		for (int a = 1; a <= 12; a++) {
			String time = (year - 1) + "/" + (a < 10 ? "0" + a : a);
			timeList.add(time);
		}
		list.add(year - 1);
		list.add(year);
		resultMap.put("time", list);

		Map<String, BigDecimal> map = subscribeInfoDao.comparativeAnalysis(hospitId, timeList, status);
		list = new ArrayList<Integer>();
		for (int a = 0; a < timeList.size(); a++) {
			Integer num = Integer.valueOf(map.get("DATE" + timeList.get(a).replace("/", "")).toString());
			list.add(num);
		}
		resultMap.put("lastYear", list);
		
		// 今年
		timeList = new ArrayList<String>();
		for (int a = 1; a <= 12; a++) {
			String time = year + "/" + (a < 10 ? "0" + a : a);
			timeList.add(time);
		}

		map = subscribeInfoDao.comparativeAnalysis(hospitId, timeList, status);
		list = new ArrayList<Integer>();
		for (int a = 0; a < timeList.size(); a++) {
			Integer num = Integer.valueOf(map.get("DATE" + timeList.get(a).replace("/", "")).toString());
			list.add(num);
		}
		resultMap.put("year", list);
		
		return resultMap;
	}

	@Override
	public Map<String, Object> getSubNumAndConcernNum(String doctorId) {
		Map<String, Object> resultMap = new HashMap<>();
		//预约数量
		String subNum = dao.getSubNumByDoctorId(doctorId);
		//关注数量
		String concernNum = doctorConcernDao.getConcernNumByDoctorId(doctorId);
		resultMap.put("subNum", subNum);
		resultMap.put("concernNum", concernNum);
		return resultMap;
	}
	
	@Override
	public void getSubscribeInfoList(String userId, String startTime, String endTime, String ifevaluate, String status, PageModel page) {
		subscribeInfoDao.getSubscribeInfoList(userId, startTime, endTime, ifevaluate, status, page);
	}

	@Override
	public List<InitDictDto> getComment() {
		return DictUtil.getDict("1004");
	}

	@Override
	public void getEvaluateList(PageModel page, String time, String deptCode, String hospitalId) {
		dao.getEvaluateList(page, time, deptCode, hospitalId);
	}

	@Override
	public BigDecimal getEvaluateCount(String userId) {
		Map<String, BigDecimal> map = dao.getEvaluateCount(userId); 
		return map.get("COUNT");
	}

	@Override
	public List<SubscribeInfo> getOverTimeSubList() {
		return dao.getOverTimeSubList();
	}

	@Override
	public List<String> getOverTimeStatusList() {
		return dao.getOverTimeStatusList();
	}

	@Override
	public void updateSubValidFlg(String userId, String vailFlg) {
		dao.updateSubValidFlg(userId, vailFlg);
	}

	@Override
	public int getOverTimeStatusByUserId(String userId) {
		return dao.getOverTimeStatusByUserId(userId);
	}

	@Override
	public SysSubscribeDto querySubscribeDetail(String subId) {
		return dao.querySubscribeDetail(subId);
	}

}
