package com.java.moudle.documentGuidelines.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.documentGuidelines.domain.RegistrationGuide;

public interface RegistrationGuideRepository extends JpaRepository<RegistrationGuide, String>{

	@Transactional
	@Modifying
	@Query(value = "update registration_guide set status = :status where id = :id", nativeQuery = true)
	void deleteOrFrozenGuideInfo(@Param("id")String id, @Param("status")String status);
}
