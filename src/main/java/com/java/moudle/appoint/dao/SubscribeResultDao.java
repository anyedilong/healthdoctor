package com.java.moudle.appoint.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.java.moudle.appoint.dao.repository.SubscribeResultRepository;
import com.java.moudle.appoint.domain.SubscribeResult;
import com.java.until.dba.BaseDao;

@Named
public class SubscribeResultDao extends BaseDao<SubscribeResultRepository, SubscribeResult> {

	@Autowired
	SubscribeResultRepository subscribeResultRepository;

	public void updateDocterEvaluate(SubscribeResult subscribeResult) {
		subscribeResultRepository.updateDocterEvaluate(subscribeResult.getEvaluationType(), subscribeResult.getEvaluationContent(), subscribeResult.getId());
	}

	public void updateDocterComplaint(SubscribeResult subscribeResult) {
		subscribeResultRepository.updateDocterComplaint(subscribeResult.getComplaintContent(), subscribeResult.getId());
	}
}
