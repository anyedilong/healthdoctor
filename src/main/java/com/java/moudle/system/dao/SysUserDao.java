package com.java.moudle.system.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.system.dao.repository.SysUserRepository;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.dto.SysUserDto;
import com.java.until.StringUtil;
import com.java.until.dba.BaseDao;
import com.java.until.dba.PageModel;

@Named
public class SysUserDao extends BaseDao<SysUserRepository, SysUser> {

	public SysUserDto getUserDtoByUsername(String username) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select u.id, u.username, u.password, u.type, u.authorities ");
		sql.append(" from sys_user u ");
		sql.append(" where u.status = '1' ");
		if (!StringUtil.isNull(username)) {
			sql.append(" and  u.username like concat('%', concat(:username, '%')) ");
			map.put("username", username);
		}
		return queryOne(sql.toString(), map, SysUserDto.class);
	}
	
	public SysUserDto getUserInfoByName(String username) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select u.id, u.username, u.pwd");
		sql.append(" from sys_user u ");
		sql.append(" where u.status = '1' ");
		sql.append(" and u.username = :username ");
		map.put("username", username);
		return queryOne(sql.toString(), map, SysUserDto.class);
	}

	public SysUser getUserInfoByCon(SysUser user) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select u.* ");
		sql.append(" from sys_user u ");
		sql.append(" where u.status = '1' ");
		if (!StringUtil.isNull(user.getId())) {
			sql.append(" and  u.id = :id ");
			map.put("id", user.getId());
		}
		if (!StringUtil.isNull(user.getMobilePhone())) {
			sql.append(" and  u.mobile_phone = :phone ");
			map.put("phone", user.getMobilePhone());
		}
		if (!StringUtil.isNull(user.getOpenId())) {
			sql.append(" and  u.open_id = :openId ");
			map.put("openId", user.getOpenId());
		}
		if (!StringUtil.isNull(user.getNickname())) {
			sql.append(" and  u.nickname = :nickname ");
			map.put("nickname", user.getNickname());
		}
		if (!StringUtil.isNull(user.getUsername())) {
			sql.append(" and  u.username = :username ");
			map.put("username", user.getUsername());
		}
		return queryOne(sql.toString(), map, SysUser.class);
	}

	public int updatePassword(String id, String newPassword, String password) {
		return repository.updatePassword(id, newPassword, password);
	}

	public SysUser queryUserInfoByMobilePhone(String oldMobilePhone) {
		return repository.queryUserInfoByMobilePhone(oldMobilePhone);
	}

	public int updateMobilePhone(String userId, String newMobilePhone) {
		return repository.updateMobilePhone(userId, newMobilePhone);
	}

	public void getUserList(String startDate, String endDate, PageModel page) throws Exception {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select u.id, u.username, u.nickname, ");
		sql.append(" (select b.username from sys_user b where b.id = u.update_user ) as optionName ");
		sql.append(" from sys_user u ");
		sql.append(" where u.status = '1' and type = '1' and username != 'admin'");
		if (!StringUtil.isNull(startDate)) {
			sql.append(" and u.update_time >= to_date(:startDate, 'yyyy-MM-dd') ");
			map.put("startDate", startDate);
		}
		if (!StringUtil.isNull(endDate)) {
			sql.append(" and u.update_time <= to_date(:endDate, 'yyyy-MM-dd') ");
			map.put("endDate", endDate);
		}
		queryPageList(sql.toString(), map, page, SysUser.class);
	}
}
