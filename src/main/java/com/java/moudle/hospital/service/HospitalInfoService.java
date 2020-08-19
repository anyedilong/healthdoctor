package com.java.moudle.hospital.service;

import java.util.List;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.hospital.domain.HospitalInfo;
import com.java.moudle.system.domain.SysUser;
import com.java.until.dba.PageModel;

public interface HospitalInfoService extends BaseService<HospitalInfo> {

	//获取医院的list
	void getHospitalList(HospitalInfo hospitalInfo, PageModel page) throws Exception;
	//获取医院的总个数
	String getHospitalNum() throws Exception;
	//获取医院所在区划的总个数
	String getHospitalAreaNum() throws Exception;
	//通过医院id查询his服务地址
	String getHisUrlByHospitId(String id) throws Exception;
	//查看医院的详情
	HospitalInfo getHospitalDetail(String id) throws Exception;
	//医院列表(分页)
    void getHospitalPage(HospitalInfo sysUser, PageModel page) throws Exception;
    //修改医院的状态
    void updateHospitalStatus(String id, String status, String remark) throws Exception;
    //保存医院信息
    void saveHospitalInfo(HospitalInfo info, SysUser user) throws Exception;
    // 根据医院id获取医院信息
	HospitalInfo getHospitalInfoById(String hospitalId);
	// 获取医院的list惠民不分页
	List<HospitalInfo> getHospitalListNoPage(HospitalInfo hospitalInfo);
}
