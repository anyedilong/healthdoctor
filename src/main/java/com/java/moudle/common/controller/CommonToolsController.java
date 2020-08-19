package com.java.moudle.common.controller;


import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.java.until.AliyunSMSUtil;
import com.java.until.Base64CoverImageUtil;
import com.java.until.StringUtil;
import com.java.until.UUIDUtil;
import com.java.until.cache.CacheUntil;
import com.java.until.cache.RedisCacheEmun;

/**
 * <br>
 * <b>功能：</b>CustomerController<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@RestController
@RequestMapping("commontools")
public class CommonToolsController extends BaseController {

	@Value("${ftpAddress}")
    private String ftpAddress;
	
	/**
	 * @Description: 获取手机验证码
	 * @param @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping("getIdentCode")
	public String getIdentCode(String telephone) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
	        cal.add(Calendar.MINUTE, -1);
	        if(!StringUtil.isNull(telephone)) {
	        	//获取前五分钟是否发送过验证码
	        	//QuerySendDetailsResponse querySendDetails = AliyunSMSUtil.querySendDetails(telephone, cal.getTime());
	        	//从缓存中获取本次的验证码
		        String indentCodeTemp = CacheUntil.get(RedisCacheEmun.USER_CACHE, telephone, String.class);
	        	//校验验证码是否有一分钟 或者 缓存中验证码是否被使用过(使用后删除)
	        	if(StringUtil.isNull(indentCodeTemp)) {
	        		//发送的用户名和验证码
	        		String identCode = UUIDUtil.getIdentCode();
	        		SendSmsResponse sendSms = AliyunSMSUtil.sendSms(telephone, identCode);
	        		if("OK".equals(sendSms.getCode())) {
	        			CacheUntil.put(RedisCacheEmun.USER_CACHE, telephone, identCode);
	        			return jsonResult("", 0, "已发送");
	        		}else {
	        			return jsonResult("", 10000, "不要频繁点击");
	        		}
	        	}else {
	        		return jsonResult("", 10000, "您的验证码未过期，请查看");
	        	}
	        }
	        return jsonResult("", 10000, "请填写手机号");
		}catch(Exception e) {
			e.printStackTrace();
			return jsonResult(null, -1, "系统错误"); 
		}
	}
	
	/**
	 * @Description: 上传图片，保存到ftp上
	 * @param @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping("saveImage")
	@ResponseBody
	public String saveImage(String fileData) {
		try {
			if(!StringUtil.isNull(fileData)) {
				//ftp上传
				String imgPath = Base64CoverImageUtil.GenerateImage(ftpAddress, fileData);
				if(!"false".equals(imgPath)) {
					return jsonResult(ftpAddress + imgPath);
				}else {
					return jsonResult("", 10000, "上传的文件失败");
				}
			}else {
				return jsonResult("", 10000, "请选择上传的文件");
			}
		}catch(Exception e) {
			e.printStackTrace();
			return jsonResult("", -1, "系统错误"); 
		}
	}
	
}
