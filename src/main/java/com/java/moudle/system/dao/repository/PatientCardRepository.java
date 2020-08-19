package com.java.moudle.system.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.system.domain.PatientCard;

public interface PatientCardRepository  extends JpaRepository<PatientCard, String>{
	
	@Transactional
	@Modifying
	@Query(value = "update patient_card set status = '1' where id = :id", nativeQuery = true)
	int deleteMpCard(@Param("id")String id);
	
}
