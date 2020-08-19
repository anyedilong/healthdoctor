package com.java.moudle.hospital.service.impl;


import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.hospital.dao.HospitalInfoDao;
import com.java.moudle.hospital.domain.HospitalInfo;
import com.java.moudle.hospital.service.HospitalInfoService;
import com.java.moudle.system.dao.SysUserDao;
import com.java.moudle.system.domain.SysUser;
import com.java.until.StringUtil;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;

@Named
@Transactional(readOnly = false)
public class HospitalInfoServiceImpl extends BaseServiceImpl<HospitalInfoDao, HospitalInfo> implements HospitalInfoService {

	@Inject
	private SysUserDao userDao;
	
	@Override
	public void getHospitalList(HospitalInfo hospitalInfo, PageModel page) throws Exception {
		dao.getHospitalList(hospitalInfo, page);
	}

	@Override
	public String getHospitalNum() throws Exception {
		return dao.getHospitalNum();
	}

	@Override
	public String getHospitalAreaNum() throws Exception {
		return dao.getHospitalAreaNum();
	}

	@Override
	public String getHisUrlByHospitId(String id) throws Exception {
		return dao.getHisUrlByHospitId(id);
	}

	@Override
	public HospitalInfo getHospitalDetail(String id) throws Exception {
		return dao.getHospitalDetail(id);
	}

	@Override
	public void getHospitalPage(HospitalInfo info, PageModel page) {
		dao.getHospitalPage(info, page);
	}

	@Override
	public void updateHospitalStatus(String id, String status, String remark) throws Exception {
		dao.updateHospitalStatus(id, status, remark);
	}

	@Override
	public void saveHospitalInfo(HospitalInfo info, SysUser user) throws Exception {
		String hospitId = info.getId();
		if(StringUtil.isNull(info.getId())) {
			hospitId = UUIDUtil.getUUID();
			//首次注册医疗机构时，需更新用户信息
			user.setHospitalId(hospitId);
			userDao.save(user);
		}
		info.setId(hospitId);
		info.setCreateTime(new Date());
		info.setCreateUser(user.getId());
		info.setRecommend("1");
		dao.save(info);
	}

	@Override
	public HospitalInfo getHospitalInfoById(String hospitalId) {
		return dao.get(hospitalId);
	}

	@Override
	public List<HospitalInfo> getHospitalListNoPage(HospitalInfo hospitalInfo) {
		return dao.getHospitalListNoPage(hospitalInfo);
	}
    
}
