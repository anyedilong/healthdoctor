package com.java.moudle.webservice.service;

import java.util.List;
import java.util.Map;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.webservice.domain.DepartmentInfo;
import com.java.until.dba.PageModel;

public interface DepartmentInfoService extends BaseService<DepartmentInfo> {
	
	//获取科室的集合
	void getDepartmentList(DepartmentInfo departmentInfo, PageModel page) throws Exception;
	void getDepartmentInfoList(DepartmentInfo departmentInfo, PageModel page) throws Exception;
	//获取科室的详情
	DepartmentInfo getDepartmentDetail(DepartmentInfo departmentInfo) throws Exception;
	//获取科室的医生数量和预约数量
	Map<String, Object> getDoctorNumAndSubNumByDept(String hospitalId, String deptCode) throws Exception;
	//获取科室的集合 不分页
	List<DepartmentInfo> getDepartmentListNoPage(DepartmentInfo info);
	//根据医院和hisId获取科室信息
	DepartmentInfo getDepartByHisAndHos(String hospitalId, String hisId);
}
