package com.java.moudle.common.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.java.moudle.common.domain.JsonResult;
import com.java.moudle.common.domain.ProcessStatus;
import com.java.moudle.hospital.domain.HospitalInfo;
import com.java.moudle.hospital.service.HospitalInfoService;
import com.java.moudle.system.domain.SysUser;
import com.java.until.SysUtil;

public class BaseController {

	@Resource
	protected HttpServletRequest request;

	@Resource
	protected HttpServletResponse response;

	/**
	 * 客户端返回字符串
	 * 
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
			response.setContentType(type);
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 
	 * <li>描述:结果集 默认状态为0</li>
	 * <li>方法名称:jsonResult</li>
	 * <li>参数:@param data
	 * <li>参数:@return</li>
	 * <li>返回类型:JsonResult</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	public String jsonResult(Object data) {
		JsonResult result = new JsonResult(data);
		return result(result);
	}

	public String jsonResult() {
		JsonResult result = new JsonResult(null);
		return result(result);
	}

	/**
	 * 
	 * <li>描述:结果集加状态</li>
	 * <li>方法名称:jsonResult</li>
	 * <li>参数:@param data
	 * <li>参数:@param status
	 * <li>参数:@return</li>
	 * <li>返回类型:JsonResult</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	public String jsonResult(Object data, ProcessStatus status) {
		JsonResult result = new JsonResult(data, status);
		return result(result);
	}

	/**
	 * 
	 * <li>描述:</li>
	 * <li>方法名称:jsonResult</li>
	 * <li>参数:@param data 结果
	 * <li>参数:@param propertyKey 配置文件中的key
	 * <li>参数:@return</li>
	 * <li>返回类型:JsonResult</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	public String jsonResult(Object data, String propertyKey) {
		ProcessStatus status = new ProcessStatus();
		JsonResult result = new JsonResult(data, status);
		return result(result);
	}

	/**
	 * 
	 * <li>描述:</li>
	 * <li>方法名称:jsonResult</li>
	 * <li>参数:@param data
	 * <li>参数:@param retCode 状态码
	 * <li>参数:@param retMsg 描述
	 * <li>参数:@return</li>
	 * <li>返回类型:JsonResult</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	public String jsonResult(Object data, int retCode, String retMsg) {
		ProcessStatus status = new ProcessStatus(retCode, retMsg);
		JsonResult result = new JsonResult(data, status);
		return result(result);
	}

	public String result(JsonResult result) {
		String resultString = request.getParameter("callback") + "(" + JSON.toJSONString(result) + ")";
		return resultString;
	}
	
	public String jsonResultString(Object data) {
		JsonResult result = new JsonResult(data);
		String resultString = request.getParameter("callback") + "(" + JSON.toJSONString(result, SerializerFeature.WriteDateUseDateFormat) + ")";
		return resultString;
	}
	
	@Inject
	private HospitalInfoService hospitalInfoService;
	
	/**
	 * 判断医院是否入驻
	 */
	public boolean judgeHospitalWhetherSettledIn() {
		try {
			SysUser user = SysUtil.sysUser(request, response);
			
			// 未登录网站端放行
			if (user == null) {
				return false;
			}
			
			// 卫健委的放行
			if ("admin".equals(user.getUsername())) {
				return false;
			}
			
			// 网页端放行
			if ("0".equals(user.getType())) {
				return false;
			}
			
			// 医院未入驻的拦截
			HospitalInfo info = hospitalInfoService.getHospitalInfoById(user.getHospitalId());

			if (info == null || !"2".equals(info.getStatus())) {
				return true;
			}
			
			// 其他的放行
			return false;
		} catch (Exception e) {
    		e.printStackTrace();
    		
    		// 异常的拦截
    		return true;
		}
	}
}
