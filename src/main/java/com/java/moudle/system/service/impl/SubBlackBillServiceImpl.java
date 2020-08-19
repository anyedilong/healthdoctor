package com.java.moudle.system.service.impl;


import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.SubBlackBillDao;
import com.java.moudle.system.domain.SubBlackBill;
import com.java.moudle.system.service.SubBlackBillService;

@Named
@Transactional(readOnly = false)
public class SubBlackBillServiceImpl extends BaseServiceImpl<SubBlackBillDao, SubBlackBill> implements SubBlackBillService {

	@Override
	public int queryCapable(String userId) throws Exception {
		return dao.queryCapable(userId);
	}

	@Override
	public void deleteBeforeThreeMonthData() {
		dao.deleteBeforeThreeMonthData();
	}
	
	
}
