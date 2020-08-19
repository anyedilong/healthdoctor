package com.java.moudle.system.service;


import com.java.moudle.common.service.BaseService;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.dto.SysUserDto;
import com.java.until.dba.PageModel;


public interface SysUserService extends BaseService<SysUser>{

	SysUserDto getUserDtoByUsername(String username) throws Exception;
	//根据条件查询用户的账号和密码
    SysUserDto getUserInfoByName(String username)throws Exception;
    //根据条件查询用户信息
    SysUser getUserInfoByCon(SysUser user)throws Exception;
    // 更新密码
	int updatePassword(String id, String newPassword, String password);
	// 修改绑定手机号
	int updateMobilePhone(SysUser user, String mobilePhone);
	//获取后台用户的list(后台管理)
	void getUserList(String startDate, String endDate, PageModel page) throws Exception;;
}
