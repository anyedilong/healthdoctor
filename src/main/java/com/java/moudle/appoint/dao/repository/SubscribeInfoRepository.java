package com.java.moudle.appoint.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.appoint.domain.SubscribeInfo;

public interface SubscribeInfoRepository extends JpaRepository<SubscribeInfo, String>{

	@Query(value = "select * from subscribe_info where medical_personnel_id in (select id from medical_personnel where user_id = :userId) and status = 1", nativeQuery = true)
	List<SubscribeInfo> getMySubscribeList(@Param("userId")String userId);
	
	
	@Transactional
	@Modifying
	@Query(value = "update subscribe_info set valid_flg = :flg where create_user = :userId and status = '3'", nativeQuery = true)
	void updateSubValidFlg(@Param("userId")String userId, @Param("flg")String flg);
}
