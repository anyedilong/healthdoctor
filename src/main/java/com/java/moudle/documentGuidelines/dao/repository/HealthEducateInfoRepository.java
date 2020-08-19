package com.java.moudle.documentGuidelines.dao.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.java.moudle.documentGuidelines.domain.HealthEducateInfo;

import io.lettuce.core.dynamic.annotation.Param;

public interface HealthEducateInfoRepository extends JpaRepository<HealthEducateInfo, String> {

	@Transactional
	@Modifying
	@Query(value = "update health_educate_info set status = 3 where id = :id", nativeQuery = true)
	int deleteHealthEducateInfo(@Param("id")String id);

	@Transactional
	@Modifying
	@Query(value = "update health_educate_info set status = :status where id = :id", nativeQuery = true)
	int auditHealthEducateInfo(@Param("id")String id, @Param("status")String status);
}
