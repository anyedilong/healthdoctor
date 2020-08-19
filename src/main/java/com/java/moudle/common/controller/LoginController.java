package com.java.moudle.common.controller;


import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.java.moudle.system.dto.SysUserDto;
import com.java.moudle.system.service.SysUserService;
import com.java.until.StringUtil;
import com.java.until.SysUtil;
import com.java.until.cache.CacheUntil;
import com.java.until.cache.RedisCacheEmun;
import com.java.until.ras.AesUtil;

/**
 * <br>
 * <b>功能：</b>CustomerController<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@RestController
@RequestMapping("login")
public class LoginController extends BaseController {

	@Inject
	private RestTemplate restTemplate;
	@Inject
	private SysUserService userService;
	@Value("${identCode}")
	private String identCode;
	
	
	/**
	 * @Description 根据用户名和秘密从授权中心获取授权码
	 * @return
	 * @author tz
	 */
	@RequestMapping("oauth")
	public String oauth(String username, String password, String clientIp) {
		try {
			String url = "http://oa/oa/oauth/token?grant_type=password&username="+username+
					"&password="+password+"&client_id=appointment&client_secret=123456&clientIp="+clientIp;
			ResponseEntity<String> postForEntity = restTemplate.getForEntity(url, String.class);
			String body = postForEntity.getBody();
			String token = JSONObject.parseObject(body).getString("access_token");
			return jsonResult(token);
		}catch(Exception e) {
			e.printStackTrace();
			return jsonResult(null, -1, "认证失败"); 
		}
	}
	
	/**
	 * @Description 验证用户是否在惠民平台登录过，自动登录
	 * @return
	 * @author tz
	 */
	@RequestMapping("singleLogin")
	public String singleLogin(String clientIp) {
		try {
			String key = AesUtil.aesEncrypt(clientIp+identCode);
			String aesUsername = CacheUntil.get(RedisCacheEmun.USER_CACHE, key, String.class);
			if(aesUsername == null) {
				return jsonResult(null);
			}
			String username = AesUtil.aesDecrypt(aesUsername);
			if(username == null) {
				CacheUntil.delete(RedisCacheEmun.USER_CACHE, key);
				return jsonResult(null);
			}
			SysUserDto userDto = getUserDto(username);
			String url = "http://oa/oa/oauth/token?grant_type=password&username="+userDto.getUsername()+
					"&password="+userDto.getPwd()+"&client_id=appointment&client_secret=123456&clientIp="+clientIp;
			ResponseEntity<String> postForEntity = restTemplate.getForEntity(url, String.class);
			String body = postForEntity.getBody();
			String token = JSONObject.parseObject(body).getString("access_token");
			
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("token", token);
			resultMap.put("username", username);
			return jsonResult(resultMap);
		}catch(Exception e) {
			e.printStackTrace();
			return jsonResult(null, 0, ""); 
		}
	}
	
	/**
	 * @Description 根据用户名查询用户信息
	 * @return
	 * @author tz
	 */
	@RequestMapping("getUserInfo")
	public String getUserInfo(String username) {
		try {
			SysUserDto info = userService.getUserDtoByUsername(username);
			return JSONObject.toJSONString(info);
		}catch(Exception e) {
			e.printStackTrace();
			return JSONObject.toJSONString(e.getMessage()); 
		}
	}
	
	/**
	 * @Description 用户退出
	 * @return
	 * @author tz
	 */
	@RequestMapping("exit")
	public String exit(String clientIp) {
		try {
			//公司局域网同用一个公网ip；先清除单点登录的缓存
			if(!StringUtil.isNull(clientIp)) {
		 		String key = AesUtil.aesEncrypt(clientIp+identCode);
			 	if(!StringUtil.isNull(key)) {
			 		//删除单点登录的缓存的用户账号
			 		CacheUntil.delete(RedisCacheEmun.USER_CACHE, key);
			 	}
		 	}
			
			Map<String, Object> map = SysUtil.getSecurityKey(request);
			if(map == null) {
				return jsonResult(null, 1004,"用户未登录"); 
			}
			// 客户端请求的缓存key
			String securitykey = (String) SysUtil.getSecurityKey(request).get("securitykey");
			// 验证缓存中无数据，需要重新获取缓存信息
			String jsonStr = CacheUntil.get(RedisCacheEmun.USER_CACHE, securitykey, String.class);
			if (null == jsonStr) {
				return jsonResult(null, 1004,"用户未登录"); 
			}
			//从缓存中获取token (key的值待定)
			String refreshToken = "refresh_auth:" + JSONObject.parseObject(jsonStr).getString("refreshToken");
			//删除权限中心的缓存token信息
			CacheUntil.delete(RedisCacheEmun.USER_CACHE, securitykey);
			//删除权限中心的缓存用户信息
			CacheUntil.delete(RedisCacheEmun.USER_CACHE, refreshToken);
			
			return jsonResult();
		}catch(Exception e) {
			e.printStackTrace();
			return jsonResult(null,-1,"系统错误"); 
		}
	}
	
	/**
	 * @Description 根据用户名查询用户信息
	 * @return
	 * @author tz
	 */
	private SysUserDto getUserDto(String username) {
		try {
			SysUserDto info = userService.getUserInfoByName(username);
			return info;
		}catch(Exception e) {
			e.printStackTrace();
			return null; 
		}
	}
	
}
