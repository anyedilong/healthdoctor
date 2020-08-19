package com.java.moudle.personal.service;


import java.util.List;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.personal.domain.MedicalPersonnel;


public interface MedicalPersonnelService extends BaseService<MedicalPersonnel>{

	List<MedicalPersonnel> getPatientList(String userId);
	
	List<MedicalPersonnel> getHcPatientList(String userId, String hospitalId);
	
	MedicalPersonnel getPatientBaseInfo(String id);

	void saveOrupdatePatientBaseInfo(MedicalPersonnel m);

	void deletePatientBaseInfo(String id);

	MedicalPersonnel getPatientDefaultInfo(String createUser);

	void setPatientDefault(String createUser, String id);
	
	MedicalPersonnel getHcPatientDefaultInfo(String id, String hospitalId);
	MedicalPersonnel getHcPatientBaseInfo(String id, String hospitalId);
	
	int isExist(String sfzh, String phone, String userId);
}
