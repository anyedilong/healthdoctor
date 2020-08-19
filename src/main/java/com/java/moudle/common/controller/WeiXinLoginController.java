package com.java.moudle.common.controller;


import java.net.URLEncoder;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.java.moudle.common.domain.JsonResult;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.service.SysUserService;
import com.java.until.StringUtil;
import com.java.until.UUIDUtil;
import com.java.until.cache.CacheUntil;
import com.java.until.cache.RedisCacheEmun;
import com.java.until.wx.AuthUtil;

/**
 * <br>
 * <b>功能：</b>CustomerController<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@RestController
@RequestMapping("wxlogin")
public class WeiXinLoginController extends BaseController {

	@Value("${wx_open_appId}")
    private static String wxOpenAppId;
	@Value("${wx_open_appSecret}")
    private static String wxOpenAppSecret;
	@Value("${redirect_url}")
    private static String redirectUrl;
	
	@Inject
	private SysUserService userService;
	
	/**
	 * @Description: 返回微信二维码，可供扫描登录
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @throws
	 */
	 @RequestMapping(value = "/getTDC")
	public String wxLogin() {
		try {
			String url = "https://open.weixin.qq.com/connect/qrconnect?appid="+wxOpenAppId + 
			 		"&redirect_uri="+URLEncoder.encode(redirectUrl, "UTF-8") + "&response_type=code" + 
			 		"&scope=snsapi_login&state=STATE#wechat_redirect";
			return jsonResult(url);
		}catch(Exception e) {
			e.printStackTrace();
			return jsonResult("", -1, "服务出错，请联系管理员！！！");
		}
	}
	/**
	 * @Description: 扫描微信二维码后，业务处理
	 * @param @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping(value = "/TDC/callBack")
	public String callBack(){
		try {
			String code = request.getParameter("code");
			String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+wxOpenAppId
						+ "&secret="+wxOpenAppSecret
						+ "&code="+code
						+ "&grant_type=authorization_code";
			String authInfo = AuthUtil.doGet(url);
			JSONObject authInfoJson = JSON.parseObject(authInfo);
			String openid = authInfoJson.getString("openid");
			String token = authInfoJson.getString("access_token");
			String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+token
							+"&openid="+openid
							+"&lang=zh_CN";
			String userInfoStr = AuthUtil.doGet(infoUrl);
			JSONObject userJson = JSON.parseObject(userInfoStr);
			String nickname = userJson.getString("nickname");
			//有微信用户信息直接登录，没有跳到绑定手机号界面
			SysUser tempUser = new SysUser();
			tempUser.setOpenId(openid);
			SysUser user = userService.getUserInfoByCon(tempUser);
			if(user != null && !StringUtil.isNull(user.getMobilePhone())){
				//生成新的key值
//				String uuid = UUIDUtil.getUUID();
//				String baseSecurity = Base64.encodeToString(uuid.getBytes());
//				String newAuthToken = Base64.encodeToString((baseSecurity).getBytes());
//				CacheUntil.put(RedisCacheEmun.USER_CACHE, uuid, user);
				JsonResult jsonResult = new JsonResult();
//				jsonResult.setAuthorToke(newAuthToken);
				return null;
			}else{
				//没有注册过的用户，新增一条数据，前端跳到绑定手机号界面
				String id = UUIDUtil.getUUID();
				user.setId(id);
				user.setNickname(nickname);
				user.setOpenId(openid);
				userService.save(user);
				user.setOpenId("");
				return jsonResult(user);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return jsonResult("", -1, "服务出错，请联系管理员！！！");
		}
	 }
	
	/**
	 * @Description: 微信绑定手机号验证登录
	 * @param @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping("wxIndex")
	public String wxIndex(String id, String telephone, String identCode) {
		try {
	        SysUser user = userService.get(id);
	        //从缓存中获取本次的验证码
	        String indentCodeTemp = CacheUntil.get(RedisCacheEmun.USER_CACHE, telephone, String.class);
	        if(user != null && !StringUtil.isNull(identCode) && identCode.equals(indentCodeTemp)) {
	        	user.setMobilePhone(telephone);
	        	user.setType("0");
	        	user.setStatus("1");
	        	user.setUpdateTime(new Date());
	        	userService.save(user);
	        	CacheUntil.delete(RedisCacheEmun.USER_CACHE, telephone);
	        	//生成新的key值，并登录
				String uuid = UUIDUtil.getUUID();
//				String baseSecurity = Base64.encodeToString(uuid.getBytes());
//				String newAuthToken = Base64.encodeToString((baseSecurity).getBytes());
//				CacheUntil.put(RedisCacheEmun.USER_CACHE, uuid, user);
				JsonResult jsonResult = new JsonResult();
//				jsonResult.setAuthorToke(newAuthToken);
				return null;
	        }else {
	        	return jsonResult("", 10000, "您的验证码不正确，请核查");
	        }
		}catch(Exception e) {
			e.printStackTrace();
			return jsonResult(null, -1, "系统错误"); 
		}
	}
	
}
