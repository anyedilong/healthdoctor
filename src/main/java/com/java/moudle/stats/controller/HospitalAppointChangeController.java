package com.java.moudle.stats.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.stats.domain.HospitalAppointChange;
import com.java.moudle.stats.service.HospitalAppointChangeService;
import com.java.moudle.system.domain.SysUser;
import com.java.until.StringUtil;
import com.java.until.SysUtil;


@RestController
@RequestMapping("${regpath}/appointchange")
public class HospitalAppointChangeController extends BaseController {

	@Inject
	private HospitalAppointChangeService hospitalAppointChangeService;
	
	
	/**
	 * @Description: 获取排班变更日期（1.年 2.月）
	 * @param @param type
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("getAppointChangeList")
    public String getAppointChangeList(String type, String year){
    	try{
    		if(!StringUtil.isNull(type)) {
    			return jsonResult(hospitalAppointChangeService.getAppointChangeList(type, year));
    		}else {
    			return jsonResult(null, 10000, "获取日期的类型为空");
    		}
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
	
	/**
	 * @Description: 获取排班变更统计
	 * @param @param type
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("getAppointChangeStats")
    public String getAppointChangeStats(HospitalAppointChange info){
    	try{
    		if(!StringUtil.isNull(info)) {
    			SysUser user = SysUtil.sysUser(request, response);
    			info.setHospitalId(user.getHospitalId());
    			return jsonResult(hospitalAppointChangeService.getAppointChangeStats(info, user.getUsername()));
    		}else {
    			return jsonResult(null, 10000, "入参为空");
    		}
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
	
	/**
	 * @Description: 医生投诉统计
	 * @param @param type
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("getComplaintStats")
    public String getComplaintStats(HospitalAppointChange info){
    	try{
    		if(!StringUtil.isNull(info)) {
    			String hospitalId = SysUtil.sysUser(request, response).getHospitalId();
    			info.setHospitalId(hospitalId);
    			return jsonResult(hospitalAppointChangeService.getComplaintStats(info));
    		}else {
    			return jsonResult(null, 10000, "入参为空");
    		}
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
	
}
