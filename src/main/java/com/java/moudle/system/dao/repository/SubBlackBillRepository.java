package com.java.moudle.system.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.system.domain.SubBlackBill;

public interface SubBlackBillRepository  extends JpaRepository<SubBlackBill, String>{
	
	
	@Transactional
	@Modifying
	@Query(value = " delete from sub_black_bill where create_time <= (sysdate - 91) ", nativeQuery = true)
	void deleteBeforeThreeMonthData();
	
}
