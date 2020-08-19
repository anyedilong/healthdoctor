package com.java.moudle.appoint.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.appoint.domain.DoctorConcern;


public interface DoctorConcernRepository extends JpaRepository<DoctorConcern, String>{

	@Transactional
	@Modifying
	@Query(value = "delete from doctor_concern where doctor_id = :doctorId and user_id = :userId", nativeQuery = true)
	void quitDoctorConcern(@Param("doctorId") String doctorId, @Param("userId") String userId);

}
