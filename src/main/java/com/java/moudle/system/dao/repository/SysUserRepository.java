package com.java.moudle.system.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.system.domain.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, String>{

	@Transactional
	@Modifying
	@Query(value = "update sys_user set password = :newPassword, pwd = :password where id = :id", nativeQuery = true)
	int updatePassword(@Param("id")String id, @Param("newPassword")String newPassword, @Param("password")String password);

	@Query(value = "select * from sys_user where username = :oldMobilePhone", nativeQuery = true)
	SysUser queryUserInfoByMobilePhone(@Param("oldMobilePhone")String oldMobilePhone);

	@Transactional
	@Modifying
	@Query(value = "update sys_user set username = :mobilePhone where id = :userId", nativeQuery = true)
	int updateMobilePhone(@Param("userId")String userId, @Param("mobilePhone")String mobilePhone);
}
