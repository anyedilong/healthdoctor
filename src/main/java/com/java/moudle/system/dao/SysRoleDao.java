package com.java.moudle.system.dao;


import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.system.dao.repository.SysRoleRepository;
import com.java.moudle.system.domain.SysRole;
import com.java.until.dba.BaseDao;

@Named
public class SysRoleDao extends BaseDao<SysRoleRepository, SysRole> {

	public SysRole getRoleInfoByUserId(String userId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.* ");
		sql.append(" from sys_role a ");
		sql.append(" join sys_role_user b on a.id = b.role_id ");
		sql.append(" where b.user_id = :userId ");
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId);
		return queryOne(sql.toString(), param, SysRole.class);
	}
}
