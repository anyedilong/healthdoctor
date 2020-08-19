package com.java.moudle.system.service;

import java.util.List;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.system.domain.PatientCard;
import com.java.moudle.tripartdock.dto.PatientPersonDto;

public interface PatientCardService extends BaseService<PatientCard> {

	
	List<PatientCard> getCardNum(String hospitalId, String mpId);
	
	List<PatientPersonDto> getCardNumInfoList(String userId);
	
	void deleteMpCard(String id);
			
}
