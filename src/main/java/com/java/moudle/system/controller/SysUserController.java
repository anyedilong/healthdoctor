package com.java.moudle.system.controller;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.domain.SysRoleUser;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.dto.SysUserDto;
import com.java.moudle.system.service.SysRoleUserService;
import com.java.moudle.system.service.SysUserService;
import com.java.moudle.tripartdock.service.SyncCustomerService;
import com.java.until.StringUtil;
import com.java.until.SysUtil;
import com.java.until.UUIDUtil;
import com.java.until.cache.CacheUntil;
import com.java.until.cache.RedisCacheEmun;
import com.java.until.dba.PageModel;
import com.java.until.ras.BCrypt;


@RestController
@RequestMapping("${regpath}/user")
public class SysUserController extends BaseController {

	@Inject
	private RestTemplate restTemplate;
    @Inject
    private SysUserService userService;
    @Inject
    private SysRoleUserService roleUserService;
    @Inject
    private SyncCustomerService syncCustomerService;
    
    /**
     * @Description: 验证用户名是否注册
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("isPhoneExist")
    public String isPhoneExist(String telephone) {
    	try {
    		SysUserDto user = userService.getUserInfoByName(telephone);
    		if(user != null) {
    			return jsonResult("", 10000, "手机号码不能重复注册，请重新输入");
    		}
	        return jsonResult("");
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonResult(e.toString(), -1, "操作异常！");
    	}
    }
    
    /**
     * @Description: 获取用户信息(网页版)
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getUserInfo")
    public String getUserDtoByUsername() {
    	try {
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
	        return jsonResult(user);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonResult(e.toString(), -1, "操作异常！");
    	}
    }
    
    /**
     * 验证密码是否正确
     */
    @RequestMapping("verificationPassword")
    public String verificationPassword(String password) {
    	try {
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		//user = userService.getUserInfoByCon(user);
    		if (BCrypt.checkpw(password, user.getPassword())) {
    			return jsonResult();
    		} else {
    			return jsonResult("" , 1003, "密码错误");
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonResult(e.toString(), -1, "操作异常！");
    	}
    }
    
    /**
     * @Description: 用户注册(网页版)
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("register")
    public String register(String username, String identCode, String password) {
    	try {
			SysUser paramUser = new SysUser();
        	paramUser.setUsername(username);
        	SysUser user = userService.getUserInfoByCon(paramUser);
        	if(user != null) {
        		return jsonResult("", 10000, "该手机号已经被注册");
        	}
        	//从缓存中获取本次的验证码
	        String indentCodeTemp = CacheUntil.get(RedisCacheEmun.USER_CACHE, username, String.class);
        	if(!StringUtil.isNull(identCode) && identCode.equals(indentCodeTemp)) {
	        	CacheUntil.delete(RedisCacheEmun.USER_CACHE, username);
	        	user = new SysUser();
	        	user.setId(UUIDUtil.getUUID());
	        	user.setUsername(username);
	        	user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
	        	user.setStatus("1");
	        	user.setType("0");
	        	user.setPwd(password);
	        	user.setAuthorities("a8dfc8c609d943d89f3b0cfea84ab3aa");
	        	user.setUpdateTime(new Date());
	        	userService.save(user);
	        	//根据业务需求，向惠民平台同步预约挂号的用户 TODO
	        	syncCustomerService.syncCustomerInfo(username, username, password);
	        	//调用认证中心
	        	String url = "http://oa/oa/oauth/token?grant_type=password&username="+username+"&password="+password+"&client_id=appointment&client_secret=123456";
				ResponseEntity<String> postForEntity = restTemplate.getForEntity(url, String.class);
				String body = postForEntity.getBody();
				String token = JSONObject.parseObject(body).getString("access_token");
				return jsonResult(token);
			}else {
				return jsonResult("", 10000, "验证码输入错误");
			}
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonResult(e.toString(), -1, "操作异常！");
    	}
    }
    /**
     * 	修改密码(网页版)
     */
    @RequestMapping("updatePassword")
    public String updatePassword(String password) {
    	try {
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
        	// 修改密码
        	String newpassword = BCrypt.hashpw(password, BCrypt.gensalt());
        	userService.updatePassword(user.getId(), newpassword, password);
        	//根据业务需求，向惠民平台同步预约挂号的用户 TODO
        	SysUser sysUser = userService.get(user.getId());
        	syncCustomerService.syncCustomerInfo(sysUser.getUsername(), sysUser.getUsername(), password);
        	return jsonResult();
    	} catch (Exception e) {
    		return jsonResult(e.toString(), -1, "操作异常！");
    	}
    }
    
    /**
     * 	验证手机号验证码正确(网页版)
     */
    @RequestMapping("verificationPhone") 
    public String verificationPhone(String identCode) {
    	try {
    		SysUser user = SysUtil.sysUser(request, response);
    		// 验证验证码准确
	        String indentCodeTemp = CacheUntil.get(RedisCacheEmun.USER_CACHE, user.getUsername(), String.class);
        	if(StringUtil.isNull(identCode) || !identCode.equals(indentCodeTemp)) {
        		return jsonResult("验证码错误!", 10000, "验证码错误!");
        	}
        	CacheUntil.delete(RedisCacheEmun.USER_CACHE, user.getUsername());
    		return jsonResult("验证码正确");
    	} catch (Exception e) {
    		return jsonResult(e.toString(), -1, "操作异常！");
		}
    }
    
    /**
     * 	更换手机号(网页版)
     */
    @RequestMapping("updateMobilePhone")
    public String updateMobilePhone(String newMobilePhone) {
    	try {
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			jsonResult("", 1004, "用户未登录");
    		}
    		// 更新手机号
        	int result = userService.updateMobilePhone(user, newMobilePhone);
        	if (result == 0) {
        		return jsonResult("可能没有这个旧手机号的绑定信息", 10001, "修改密码失败！");
        	} else if (result == -1) {
        		return jsonResult("", 10002, "您输入的手机号码已经被绑定");
        	} else if (result == -2) {
        		return jsonResult("", 10003, "新手机号与旧手机号不能相同");
        	} else {
        		//根据业务需求，向惠民平台同步预约挂号的用户 TODO
            	SysUser sysUser = userService.get(user.getId());
            	syncCustomerService.syncCustomerInfo(sysUser.getUsername(), newMobilePhone, sysUser.getPwd());
        		user.setUsername(newMobilePhone);
        		return jsonResult("修改手机号成功！");
        	}
    	} catch (Exception e) {
    		return jsonResult(e.toString(), -1, "操作异常！");
    	}
    }
    /**
     * @Description: 获取后台用户的集合(后台管理)
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getUserList")
    public String getUserList(String startDate, String endDate, PageModel page) {
    	try {
    		userService.getUserList(startDate, endDate, page);
	        return jsonResult(page);
    	} catch (Exception e) {
    		return jsonResult(e.toString(), -1, "操作异常！");
    	}
    }
    /**
     * @Description: 用户保存(后台管理)
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("saveUserInfo")
    public String saveUserInfo(String username, String nickname, String password) {
    	try {
    		SysUser tempUser = SysUtil.sysUser(request, response);
    		if (tempUser == null) {
    			jsonResult("", 1004, "用户未登录");
    		}
			SysUser paramUser = new SysUser();
        	paramUser.setUsername(username);
        	SysUser user = userService.getUserInfoByCon(paramUser);
        	if(user != null) {
        		return jsonResult("", 10000, "该用户名已经被注册");
        	}
        	String userId = UUIDUtil.getUUID();
        	user = new SysUser();
        	user.setId(userId);
        	user.setUsername(username);
        	user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        	user.setNickname(nickname);
        	user.setStatus("1");
        	user.setType("1");
        	user.setAuthorities("a8dfc8c609d943d89f3b0cfea84ab3aa");
        	user.setUpdateTime(new Date());
        	user.setUpdateUser(tempUser.getId());
        	userService.save(user);
        	
        	SysRoleUser roleUser = new SysRoleUser();
        	roleUser.setId(UUIDUtil.getUUID());
        	roleUser.setRoleId("a8dfc8c609d943d89f3b0cfea84ab3aa");
        	roleUser.setUserId(userId);
        	roleUserService.save(roleUser);
	        return jsonResult();
    	} catch (Exception e) {
    		return jsonResult(e.toString(), -1, "操作异常！");
    	}
    }
    /**
     * 	用户列表修改密码(后台管理)
     */
    @RequestMapping("updatePwd")
    public String updatePwd(String id, String password) {
    	try {
    		SysUser paramUser = new SysUser();
        	paramUser.setId(id);
        	SysUser user = userService.getUserInfoByCon(paramUser);
    		if(user != null) {
    			user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
    			userService.save(user);
    		}
			return jsonResult();
    	} catch (Exception e) {
    		return jsonResult(e.toString(), -1, "操作异常！");
    	}
    }
    /**
     * 	用户自己修改密码(后台管理)
     */
    @RequestMapping("updateUserPwd")
    public String updateUserPwd(String oldPassword, String newPassword) {
    	try {
    		SysUser user = SysUtil.sysUser(request, response);
    		if(user != null && BCrypt.checkpw(oldPassword, user.getPassword())) {
    			user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
    			userService.save(user);
    			return jsonResult();
    		}else {
    			return jsonResult("", 10000, "原来的密码不正确");
    		}
    	} catch (Exception e) {
    		return jsonResult(e.toString(), -1, "操作异常！");
    	}
    }
    
    /**
     * 	忘记密码
     */
    @RequestMapping("resetPassword")
    public String resetPassword(String telephone, String identCode, String password) {
    	try {
    		if (StringUtil.isNull(telephone) || StringUtil.isNull(identCode) || StringUtil.isNull(password)) {
    			return jsonResult("", 1004, "请填写正确的数据");
    		}
    		SysUserDto user = userService.getUserDtoByUsername(telephone);
    		if(user == null) {
    			return jsonResult("", 1004, "请填写已经注册的手机号");
    		}
    		//从缓存中获取本次的验证码
	        String indentCodeTemp = CacheUntil.get(RedisCacheEmun.USER_CACHE, user.getUsername(), String.class);
        	if(!StringUtil.isNull(identCode) && identCode.equals(indentCodeTemp)) {
        		// 修改密码
            	String newpassword = BCrypt.hashpw(password, BCrypt.gensalt());
            	userService.updatePassword(user.getId(), newpassword, password);
            	return jsonResult();
        	}else {
        		return jsonResult("", 1004, "请填写正确的验证码");
        	}
    	} catch (Exception e) {
    		return jsonResult(e.toString(), -1, "操作异常！");
    	}
    }
}
