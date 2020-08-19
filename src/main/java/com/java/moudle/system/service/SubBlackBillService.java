package com.java.moudle.system.service;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.system.domain.SubBlackBill;

public interface SubBlackBillService extends BaseService<SubBlackBill> {

	
	int queryCapable(String userId) throws Exception;
	
	void deleteBeforeThreeMonthData();
	
}
