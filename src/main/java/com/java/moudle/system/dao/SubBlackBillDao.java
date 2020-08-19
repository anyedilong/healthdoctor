package com.java.moudle.system.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.system.dao.repository.SubBlackBillRepository;
import com.java.moudle.system.domain.SubBlackBill;
import com.java.until.dba.BaseDao;


@Named
public class SubBlackBillDao extends BaseDao<SubBlackBillRepository, SubBlackBill> {

	
	public void deleteBeforeThreeMonthData() {
		repository.deleteBeforeThreeMonthData();
	}
	
	public int queryCapable(String userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from sub_black_bill where user_id = :userId");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		return queryOne(sql.toString(), paramMap, Integer.class);
	}
	
}
