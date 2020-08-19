package com.java.until;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.moudle.system.domain.SysUser;
import com.java.until.cache.CacheUntil;
import com.java.until.cache.RedisCacheEmun;
import com.java.until.ras.AesUtil;



public class SysUtil {

	/**
	 * <li>解析前端加密的授权</li> 
	 * <li>方法名称:getSecurityKey</li>
	 * <li>参数:@param client,username,token
	 * <li>参数:@return</li>
	 */
	public static final Map<String, Object> getSecurityKey(ServletRequest request) {
		try {
			Map<String, Object> resultMap = new HashMap<>();
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String token = httpRequest.getParameter("authToken");
			
			if (!StringUtil.isNull(token)) {
				//待授权中心完善后修改
				String decrypt = AesUtil.aesDecrypt(token);
				String[] decyptArr = decrypt.split(",");
				if(decyptArr.length == 3) {
					//获取redis里面的key（client+username）
					resultMap.put("securitykey", decyptArr[0] + decyptArr[1]);
					//登录验证中心获取的token
					resultMap.put("securitytoken", decyptArr[2]);
					return resultMap;
				}
			}
		}catch(Exception e) {
			System.err.println(e.toString());
		}
		return null;
	}
	
	/**
	 * 	获取登录用户信息
	 * @return
	 */
	public static SysUser sysUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> map = SysUtil.getSecurityKey(request);
			if(map == null) {
				return null;
			}
			//获取登录用户信息，为空查询数据库并保存到redis中
			String token = (String) map.get("securitytoken");
			return CacheUntil.get(RedisCacheEmun.USER_CACHE, token, SysUser.class);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return null;
	}
}
