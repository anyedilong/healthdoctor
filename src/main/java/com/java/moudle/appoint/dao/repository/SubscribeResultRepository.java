package com.java.moudle.appoint.dao.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.appoint.domain.SubscribeResult;

public interface SubscribeResultRepository extends JpaRepository<SubscribeResult, String>{
	
	@Transactional
	@Modifying
	@Query(value = "update subscribe_result set evaluation_type = :evaluationType, evaluation_content = :evaluationContent  where id = :id", nativeQuery = true)
	void updateDocterEvaluate(@Param("evaluationType")String evaluationType, @Param("evaluationContent")String evaluationContent, @Param("id")String id);
	
	@Transactional
	@Modifying
	@Query(value = "update subscribe_result set COMPLAINT_CONTENT = :complaintContent where id = :id", nativeQuery = true)
	void updateDocterComplaint(@Param("complaintContent")String complaintContent, @Param("id")String id);
}
