package com.java.moudle.personal.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.personal.domain.MedicalPersonnel;

public interface MedicalPersonnelRepository extends JpaRepository<MedicalPersonnel, String>{
	
	@Transactional
	@Modifying
	@Query(value = "update medical_personnel set status = '0' where id = :id", nativeQuery = true)
	int deletePatientBaseInfo(@Param("id")String id);

	@Transactional
	@Modifying
	@Query(value = "update medical_personnel set patient_sign = '0' where user_id = :userId and status = 1", nativeQuery = true)
	int setPatientUnDefault(@Param("userId")String userId);

	@Transactional
	@Modifying
	@Query(value = "update medical_personnel set patient_sign = '1' where user_id = :userId and id = :id and status = 1", nativeQuery = true)
	int setPatientDefault(@Param("userId")String userId, @Param("id")String id);

	@Transactional
	@Modifying
	@Query(value = "update medical_personnel set patient_sign = '1' where id = (select id from (select * from medical_personnel where status = 1 order by patient_sign desc, update_time desc) where rownum = 1)", nativeQuery = true)
	void setPatientPatientSignNew();

}
