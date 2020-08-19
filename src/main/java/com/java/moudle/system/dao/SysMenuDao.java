package com.java.moudle.system.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.system.dao.repository.SysMenuRepository;
import com.java.moudle.system.domain.SysMenu;
import com.java.until.dba.BaseDao;

@Named
public class SysMenuDao extends BaseDao<SysMenuRepository, SysMenu> {

   
	//查询用户操作权限菜单 
	public List<SysMenu> getMenuTree(String userId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		StringBuffer sql = new StringBuffer();
		sql.append(" select m.id, m.name, m.url  ")
		.append("   from sys_role_user ur ")
		.append("   join sys_role r on ur.role_id=r.id and r.status='1' ")
		.append("   join sys_role_menu rm on rm.role_id=r.id ")
		.append("   join sys_menu m on m.status='1' and m.id = rm.menu_id ")
		.append("  where ur.user_id = :userId ")
		.append("  order BY to_number(m.order_num) ");
		return queryList(sql.toString(), paramMap, SysMenu.class);
	}
	 
}
