package com.java.moudle.webservice.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.appoint.dao.SubscribeInfoDao;
import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.webservice.dao.DepartmentInfoDao;
import com.java.moudle.webservice.dao.DoctorInfoDao;
import com.java.moudle.webservice.domain.DepartmentInfo;
import com.java.moudle.webservice.service.DepartmentInfoService;
import com.java.until.dba.PageModel;

@Named
@Transactional(readOnly = false)
public class DepartmentInfoServiceImpl extends BaseServiceImpl<DepartmentInfoDao, DepartmentInfo> implements DepartmentInfoService {

	@Inject
	private SubscribeInfoDao subscribeInfoDao;
	@Inject
	private DoctorInfoDao doctorInfoDao;
	
	@Override
	public void getDepartmentList(DepartmentInfo departmentInfo, PageModel page) throws Exception {
		dao.getDepartmentList(departmentInfo, page);
	}
	
	@Override
	public void getDepartmentInfoList(DepartmentInfo departmentInfo, PageModel page) throws Exception {
		dao.getDepartmentInfoList(departmentInfo, page);
	}

	@Override
	public DepartmentInfo getDepartmentDetail(DepartmentInfo departmentInfo) throws Exception {
		return dao.getDepartmentDetail(departmentInfo);
	}

	@Override
	public Map<String, Object> getDoctorNumAndSubNumByDept(String hospitalId, String deptCode) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		//获取医生的数量
		String doctorNum = doctorInfoDao.getDoctorNumByHospitId(hospitalId, deptCode);
		//获取预约的数量
		String subNum = subscribeInfoDao.getSubNumByDeptCode(hospitalId, deptCode);
		resultMap.put("doctorNum", doctorNum);
		resultMap.put("subNum", subNum);
		return resultMap;
	}

	@Override
	public List<DepartmentInfo> getDepartmentListNoPage(DepartmentInfo info) {
		return dao.getDepartmentListNoPage(info);
	}
	
	@Override
	public DepartmentInfo getDepartByHisAndHos(String hospitalId, String hisId) {
		return dao.getDepartByHisAndHos(hospitalId, hisId);
	}
    
}
