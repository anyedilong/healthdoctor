package com.java.moudle.webservice.service.impl;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.appoint.dao.DoctorConcernDao;
import com.java.moudle.appoint.domain.DoctorConcern;
import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.webservice.dao.DoctorInfoDao;
import com.java.moudle.webservice.domain.DoctorInfo;
import com.java.moudle.webservice.service.DoctorInfoService;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;

@Named
@Transactional(readOnly = false)
public class DoctorInfoServiceImpl extends BaseServiceImpl<DoctorInfoDao, DoctorInfo> implements DoctorInfoService {

	@Inject
	private DoctorConcernDao doctorConcernDao;
	
	@Override
	public void getDoctorList(DoctorInfo doctorInfo, PageModel page) throws Exception {
		dao.getDoctorList(doctorInfo, page);
	}

	@Override
	public DoctorInfo getDoctorInfoById(String id) {
		 return dao.getDoctorInfoById(id);
	}
	
	@Override
	public DoctorInfo getDoctorInfoByHisId(String id, String deptCode, String hospitId) {
		 return dao.getDoctorInfoByHisId(id, deptCode, hospitId);
	}

	@Override
	public String getDoctorNumByHospitId(String hospitId) throws Exception {
		return dao.getDoctorNumByHospitId(hospitId, "");
	}

	@Override
	public String saveDoctorConcern(String doctorId, String userId) throws Exception {
		if(!dao.exists(doctorId)) {
			return "医生信息未上传";
		}
		String num = doctorConcernDao.queryConcernInfo(doctorId, userId);
		if(Integer.parseInt(num) > 0) {
			return "该医生已关注，不要重复关注";
		}
		DoctorConcern info = new DoctorConcern();
		info.setId(UUIDUtil.getUUID());
		info.setDoctorId(doctorId);
		info.setUserId(userId);
		doctorConcernDao.save(info);
		return "关注成功";
	}

	@Override
	public String queryConcernInfo(String doctorId, String userId) throws Exception {
		return doctorConcernDao.queryConcernInfo(doctorId, userId);
	}

	@Override
	public void quitDoctorConcern(String doctorId, String userId) throws Exception {
		doctorConcernDao.quitDoctorConcern(doctorId, userId);
	}

	@Override
	public List<DoctorInfo> getFollowDoctorInfo(String id) {
		return dao.getFollowDoctorInfo(id);
	}

	@Override
	public void getFollowDoctorList(DoctorInfo info, PageModel page) {
		dao.getFollowDoctorList(info, page);
	}

	@Override
	public List<DoctorInfo> getDoctorListNoPage(DoctorInfo info) {
		return dao.getDoctorListNoPage(info);
	}

	@Override
	public DoctorInfo getDoctorByHisAndHos(String hospitalId, String hisId) {
		return dao.getDoctorByHisAndHos(hospitalId, hisId);
	}
}
