package com.java.moudle.system.service.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.SysUserDao;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.dto.SysUserDto;
import com.java.moudle.system.service.SysUserService;
import com.java.until.dba.PageModel;


@Named
@Transactional(readOnly = false)
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUser> implements SysUserService{

	
	@Override
	public SysUserDto getUserDtoByUsername(String username) throws Exception {
		return dao.getUserDtoByUsername(username);
	}
	
	@Override
	public SysUserDto getUserInfoByName(String username) throws Exception {
		return dao.getUserInfoByName(username);
	}
	
	@Override
	public SysUser getUserInfoByCon(SysUser user) throws Exception {
		return dao.getUserInfoByCon(user);
	}
	
	@Override
	public int updatePassword(String id, String newPassword, String password) {
		return dao.updatePassword(id, newPassword, password);
	}

	@Override
	public int updateMobilePhone(SysUser user, String mobilePhone) {
		if (user.getUsername().equals(mobilePhone)) {
			return -2; // 新旧手机号不能相同
		}
		SysUser userInfo = dao.queryUserInfoByMobilePhone(mobilePhone);
		if (userInfo != null) {
			return -1; // 这个手机号已经被绑定了
		}
		// 修改绑定手机号
		int result = dao.updateMobilePhone(user.getId(), mobilePhone);
		
		return result;
	}

	@Override
	public void getUserList(String startDate, String endDate, PageModel page) throws Exception {
		dao.getUserList(startDate, endDate, page);
	}

	
}
