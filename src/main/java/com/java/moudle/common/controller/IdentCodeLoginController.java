package com.java.moudle.common.controller;


import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.java.moudle.system.dto.SysUserDto;
import com.java.moudle.system.service.SysUserService;
import com.java.until.StringUtil;
import com.java.until.cache.CacheUntil;
import com.java.until.cache.RedisCacheEmun;

/**
 * <br>
 * <b>功能：</b>CustomerController<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@RestController
@RequestMapping("identCodelogin")
public class IdentCodeLoginController extends BaseController {

	@Inject
	private RestTemplate restTemplate;
	@Inject
	private SysUserService userService;

	/**
	 * @Description: 验证码登录
	 * @param @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping("index")
	public String index(String telephone, String identCode, String clientIp) {
		try {
        	SysUserDto user = userService.getUserInfoByName(telephone);
        	if(user == null) {
        		return jsonResult("", 10000, "该手机号未注册，请先注册");
        	}
	        //从缓存中获取本次的验证码
	        String indentCodeTemp = CacheUntil.get(RedisCacheEmun.USER_CACHE, telephone, String.class);
	        if(!StringUtil.isNull(identCode) && identCode.equals(indentCodeTemp)) {
	        	CacheUntil.delete(RedisCacheEmun.USER_CACHE, telephone);
	        	String url = "http://oa/oa/oauth/token?grant_type=password&username="+user.getUsername()+
	        			"&password="+user.getPwd()+"&client_id=appointment&client_secret=123456&clientIp="+clientIp;
				ResponseEntity<String> postForEntity = restTemplate.getForEntity(url, String.class);
				String body = postForEntity.getBody();
				String token = JSONObject.parseObject(body).getString("access_token");
				return jsonResult(token);
			}else {
				return jsonResult("", 10000, "验证码不正确");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(null, -1, e.getMessage());
		}
	}
	
}
