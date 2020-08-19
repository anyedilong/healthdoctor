package com.java.moudle.webservice.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.jws.WebService;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.java.moudle.appoint.dao.SubscribeInfoDao;
import com.java.moudle.appoint.dao.SubscribeInfoHistoryDao;
import com.java.moudle.appoint.domain.SubscribeInfo;
import com.java.moudle.appoint.domain.SubscribeInfoHistory;
import com.java.moudle.common.domain.JsonResult;
import com.java.moudle.common.domain.ProcessStatus;
import com.java.moudle.stats.dao.HospitalAppointChangeDao;
import com.java.moudle.stats.domain.HospitalAppointChange;
import com.java.moudle.webservice.dao.DepartmentInfoDao;
import com.java.moudle.webservice.dao.DoctorInfoDao;
import com.java.moudle.webservice.domain.DepartmentInfo;
import com.java.moudle.webservice.domain.DoctorInfo;
import com.java.moudle.webservice.service.HisDataExchangeWebService;
import com.java.until.StringUtil;
import com.java.until.UUIDUtil;


@WebService(serviceName = "hisDataExchangeService",
	targetNamespace="http://service.webservice.moudle.java.com/",//指定你想要的名称空间，通常使用使用包名反转
	endpointInterface="com.java.moudle.webservice.service.HisDataExchangeWebService")//服务接口全路径, 指定做SEI（Service EndPoint Interface）服务端点接口
@Component
@Transactional(readOnly = false)
public class HisDataExchangeWebServiceImpl implements HisDataExchangeWebService {

	@Inject
	private DepartmentInfoDao departmentInfoDao;
	@Inject
	private DoctorInfoDao doctorInfoDao;
	@Inject
	private SubscribeInfoDao subscribeInfoDao;
	@Inject
	private SubscribeInfoHistoryDao subscribeInfoHistoryDao;
	@Inject
	private HospitalAppointChangeDao hospitalAppointChangeDao;
	
	@Override
	public String getDeptInfo(String deptjson) {
		List<String> resultList = new ArrayList<>();
		if(StringUtil.isNull(deptjson))
			return JSON.toJSONString(jsonResult("", 10000, "上传科室数据为空"));
		List<DepartmentInfo> depList = JSON.parseArray(deptjson, DepartmentInfo.class);
		for(DepartmentInfo info : depList) {
			try {
				info.setHisId(info.getId());
				info.setId(UUIDUtil.getUUID());
				info.setState("1");
				info.setCreateUser("admin");
				info.setCreateTime(new Date());
				departmentInfoDao.save(info);
			}catch(Exception e) {
				e.printStackTrace();
				resultList.add(info.getHisId());
			}
		}
		if(resultList.size() == 0) {
			return JSON.toJSONString(jsonResult("", 200, "上传科室数据成功"));
		}else {
			return JSON.toJSONString(jsonResult(resultList, 10000, "上传科室数据失败"));
		}
	}

	@Override
	public String getDoctorlInfo(String doctorjson) {
		List<String> resultList = new ArrayList<>();
		if(StringUtil.isNull(doctorjson))
			return JSON.toJSONString(jsonResult("", 10000, "上传医生数据为空"));
		List<DoctorInfo> docList = JSON.parseArray(doctorjson, DoctorInfo.class);
		for(DoctorInfo info : docList) {
			try {
				info.setHisId(info.getId());
				info.setId(UUIDUtil.getUUID());
				info.setStatus("1");
				info.setCreateUser("admin");
				info.setCreateTime(new Date());
				doctorInfoDao.save(info);
			}catch(Exception e) {
				e.printStackTrace();
				resultList.add(info.getHisId());
			}
		}
		if(resultList.size() == 0) {
			return JSON.toJSONString(jsonResult("", 200, "上传医生数据成功"));
		}else {
			return JSON.toJSONString(jsonResult(resultList, 10000, "上传医生数据失败"));
		}
	}

	@Override
	public String updateSubStatus(String subId, String subResult) {
		try {
			SubscribeInfoHistory hisInfo = new SubscribeInfoHistory();
			SubscribeInfo subscribeInfo = subscribeInfoDao.getDoctorInfoById(subId);
			BeanUtils.copyProperties(subscribeInfo, hisInfo);
			//更新预约表
			subscribeInfo.setStatus(subResult);
			subscribeInfoDao.save(subscribeInfo);
			//保存预约历史表
			hisInfo.setSubscribeId(hisInfo.getId());
			hisInfo.setId(UUIDUtil.getUUID());
			subscribeInfoHistoryDao.save(hisInfo);
			return JSON.toJSONString(jsonResult("", 200, "更新成功"));
		}catch(Exception e) {
			e.printStackTrace();
			return JSON.toJSONString(jsonResult("", 10000, e.getMessage()));
		}
	}
	
	@Override
	public String getHospitalChange(String changejson) {
		List<String> resultList = new ArrayList<>();
		if(StringUtil.isNull(changejson))
			return JSON.toJSONString(jsonResult("", 10000, "上传变更数据为空"));
		List<HospitalAppointChange> list = JSON.parseArray(changejson, HospitalAppointChange.class);
		for(HospitalAppointChange info : list) {
			try {
				info.setId(UUIDUtil.getUUID());
				info.setCreateTime(new Date());
				hospitalAppointChangeDao.save(info);
			}catch(Exception e) {
				e.printStackTrace();
				resultList.add(info.getChangeType());
			}
		}
		if(resultList.size() == 0) {
			return JSON.toJSONString(jsonResult("", 200, "上传预约变更数据成功"));
		}else {
			return JSON.toJSONString(jsonResult(resultList, 10000, "上传变更数据失败"));
		}
	}
	
	private JsonResult jsonResult(Object data, int retCode, String retMsg) {
		ProcessStatus status = new ProcessStatus(retCode, retMsg);
		JsonResult result = new JsonResult(data, status);
		return result;
	}

}
