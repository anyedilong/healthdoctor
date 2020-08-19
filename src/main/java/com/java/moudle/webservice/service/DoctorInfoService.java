package com.java.moudle.webservice.service;

import java.util.List;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.webservice.domain.DoctorInfo;
import com.java.until.dba.PageModel;

public interface DoctorInfoService extends BaseService<DoctorInfo> {

	
	void getDoctorList(DoctorInfo doctorInfo, PageModel page) throws Exception;
	//根据id获取医生
	DoctorInfo getDoctorInfoById(String id);
	//根据hisId获取医生
	DoctorInfo getDoctorInfoByHisId(String id, String deptCode, String hospitId);
	//获取某个医院的医生总数
	String getDoctorNumByHospitId(String hospitId) throws Exception;
	//保存医生关注
	String saveDoctorConcern(String doctorId, String userId) throws Exception;
	//查询医生关注
	String queryConcernInfo(String doctorId, String userId) throws Exception;
	//取消医生关注
	void quitDoctorConcern(String doctorId, String userId) throws Exception;
	// 根据用户id查询医生列表
	List<DoctorInfo> getFollowDoctorInfo(String id);
	// 查询关注医生列表
	void getFollowDoctorList(DoctorInfo info, PageModel page);
	// 查询医生列表不分页
	List<DoctorInfo> getDoctorListNoPage(DoctorInfo info);
	//根据hisId和医院查询医生
	DoctorInfo getDoctorByHisAndHos(String hospitalId, String hisId);
}
