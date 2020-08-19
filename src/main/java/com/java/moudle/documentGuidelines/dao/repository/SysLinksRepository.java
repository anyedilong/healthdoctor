package com.java.moudle.documentGuidelines.dao.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.java.moudle.documentGuidelines.domain.SysLinks;

import io.lettuce.core.dynamic.annotation.Param;

public interface SysLinksRepository  extends JpaRepository<SysLinks, String>{

	@Transactional
	@Modifying
	@Query(value = "update SYS_LINKS set status = 3 where id = :id", nativeQuery = true)
	int deleteSysLinksInfo(@Param("id")String id);

}
