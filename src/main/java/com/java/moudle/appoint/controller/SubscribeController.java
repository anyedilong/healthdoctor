package com.java.moudle.appoint.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.appoint.domain.SubscribeResult;
import com.java.moudle.appoint.service.SubscribeService;
import com.java.moudle.common.controller.BaseController;
import com.java.moudle.common.domain.InitDictDto;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.webservice.domain.DoctorInfo;
import com.java.moudle.webservice.service.DoctorInfoService;
import com.java.until.SysUtil;
import com.java.until.dba.PageModel;

@RestController
@RequestMapping("${regpath}/subscribe")
public class SubscribeController extends BaseController{
	
	@Inject
	private SubscribeService subscribeService;
	
	@Inject
	private DoctorInfoService doctorInfoService;
	
	/**
	 * 获取预约信息列表
	 */
	@RequestMapping("getMySubscribeList")
	public String getMySubscribeList(String startTime, String endTime, String status, String ifevaluate, PageModel page) {
		try {
			SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		subscribeService.getSubscribeInfoList(user.getId(), startTime, endTime, ifevaluate, status, page);
    		return jsonResultString(page);
		} catch (Exception e) {
			return jsonResult();
		}
	}
	
	/**
	 * 获取评论条件
	 */
	@RequestMapping("getComment")
	public String getComment() {
		try {
			List<InitDictDto> list = subscribeService.getComment();
			return jsonResult(list);
		} catch (Exception e) {
			return jsonResult("", -1, "异常错误");
		}
		
	}
	
	/**
	 * 评论与投诉
	 */
	@RequestMapping("docterEvaluate")
	public String docterEvaluate(SubscribeResult subscribeResult) {
		try {
			subscribeService.docterEvaluate(subscribeResult);
			return jsonResult();
		} catch (Exception e) {
			return jsonResult(e.toString(), -1, "评论异常！");
		}
	}
	
	/**
	 * 关注医生列表
	 */
	@RequestMapping("getFollowDoctorInfo")
	public String getFollowDoctorInfo() {
		try {
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		List<DoctorInfo> list = doctorInfoService.getFollowDoctorInfo(user.getId());
			return jsonResult(list);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1, "查询异常！");
		}
	}
	
	/**
	 * 获取评价列表
	 */
	@RequestMapping("getEvaluateDoctorInfo")
	public String getEvaluateDoctorInfo(PageModel page, String year, String month, String deptCode) {
		try {
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		String time = year + "/" + month;
    		subscribeService.getEvaluateList(page, time, deptCode, user.getHospitalId());
			return jsonResult(page);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1, "查询异常！");
		}
	}
	
	/**
	 * 获取未评价数量
	 */
	@RequestMapping("getEvaluateCount")
	public String getEvaluateCount() {
		try {
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		BigDecimal count = subscribeService.getEvaluateCount(user.getId());
			return jsonResult(count);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1, "查询异常！");
		}
	}
}
