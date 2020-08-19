package com.java.moudle.documentGuidelines.dao;


import javax.inject.Named;

import com.java.moudle.documentGuidelines.dao.repository.RegistrationGuideRepository;
import com.java.moudle.documentGuidelines.domain.RegistrationGuide;
import com.java.until.dba.BaseDao;

@Named
public class FeedbackDao extends BaseDao<RegistrationGuideRepository, RegistrationGuide> {
	
}
