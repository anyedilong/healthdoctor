package com.java.moudle.personal.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.personal.dao.MedicalPersonnelDao;
import com.java.moudle.personal.dao.repository.MedicalPersonnelRepository;
import com.java.moudle.personal.domain.MedicalPersonnel;
import com.java.moudle.personal.service.MedicalPersonnelService;
import com.java.until.StringUtil;
import com.java.until.UUIDUtil;


@Named
@Transactional(readOnly = false)
public class MedicalPersonnelServiceImpl extends BaseServiceImpl<MedicalPersonnelDao, MedicalPersonnel> implements MedicalPersonnelService{

	@Autowired
	private MedicalPersonnelRepository medicalPersonnelRepository;
	
	@Autowired
	MedicalPersonnelDao medicalPersonnelDao;
	
	@Override
	public List<MedicalPersonnel> getPatientList(String userId) {
		return medicalPersonnelDao.getPatientList(userId);
	}

	@Override
	public List<MedicalPersonnel> getHcPatientList(String userId, String hospitalId) {
		return medicalPersonnelDao.getHcPatientList(userId, hospitalId);
	}
	
	@Override
	public MedicalPersonnel getPatientDefaultInfo(String userId) {
		MedicalPersonnel info = dao.getPatientDefaultInfo(userId);
		info.setXb("1".equals(info.getXb()) ? "男" : "女");
		return info;
	}
	
	@Override
	public MedicalPersonnel getPatientBaseInfo(String id) {
		MedicalPersonnel info = dao.getPatientBaseInfo(id);
		info.setXb(("1".equals(info.getXb()) ? "男" : "女"));
		return info;
	}

	@Override
	public void saveOrupdatePatientBaseInfo(MedicalPersonnel m) {
		if ("1".equals(m.getPatientSign())) {
			medicalPersonnelRepository.setPatientUnDefault(m.getUserId());
		} else if (m.getPatientSign() == null) {
			m.setPatientSign("0");
		}
		if (StringUtil.isNull(m.getId())) {
			m.setId(UUIDUtil.getUUID());
			m.setStatus("1");
		}
		m.setUpdateTime(new Date());
		if (StringUtils.isNotBlank(m.getXb()))
			m.setXb("男".equals(m.getXb()) ? "1" : "2");
		dao.save(m);
	}

	@Override
	public void deletePatientBaseInfo(String id) {
		// 查询就诊人信息
		MedicalPersonnel m = dao.getPatientBaseInfo(id);
		// 不是默认的直接删除
		medicalPersonnelRepository.deletePatientBaseInfo(id);
		// 默认的 删除后再设置下一个人为默认就诊人
		if ("1".equals(m.getPatientSign())) {
			medicalPersonnelRepository.setPatientPatientSignNew();
		}
	}

	@Override
	public void setPatientDefault(String userId, String id) {
		// 把所有人设置为非默认
		medicalPersonnelRepository.setPatientUnDefault(userId);
		// 把指定人设置为默认
		medicalPersonnelRepository.setPatientDefault(userId, id);
	}

	@Override
	public MedicalPersonnel getHcPatientDefaultInfo(String id, String hospitalId) {
		return dao.getHcPatientDefaultInfo(id, hospitalId);
	}

	@Override
	public MedicalPersonnel getHcPatientBaseInfo(String id, String hospitalId) {
		return dao.getHcPatientBaseInfo(id, hospitalId);
	}

	@Override
	public int isExist(String sfzh, String phone, String userId) {
		return dao.isExist(sfzh, phone, userId);
	}

}
