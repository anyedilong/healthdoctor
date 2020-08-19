package com.java.moudle.system.service.impl;


import java.util.List;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.PatientCardDao;
import com.java.moudle.system.domain.PatientCard;
import com.java.moudle.system.service.PatientCardService;
import com.java.moudle.tripartdock.dto.PatientPersonDto;

@Named
@Transactional(readOnly = false)
public class PatientCardServiceImpl extends BaseServiceImpl<PatientCardDao, PatientCard> implements PatientCardService {

	@Override
	public List<PatientCard> getCardNum(String hospitalId, String mpId) {
		return dao.getCardNum(hospitalId, mpId);
	}

	@Override
	public List<PatientPersonDto> getCardNumInfoList(String userId) {
		return dao.getCardNumInfoList(userId);
	}

	@Override
	public void deleteMpCard(String id) {
		dao.deleteMpCard(id);
	}

	
}
