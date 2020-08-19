package com.java.moudle.stats.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.appoint.service.SubscribeService;
import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.domain.SysUser;
import com.java.until.SysUtil;

@RestController
@RequestMapping("${regpath}/reservationStatistics")
public class ReservationStatisticsController extends BaseController{
	
    @Inject
    private SubscribeService subscribeService;
	
    /**
     * 本年度预约统计 总览
     */
    @RequestMapping("reservationStatistics")
    public String reservationStatistics() {
    	try {
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		
    		String hospitId = user.getHospitalId();
			// 医院未入驻拦截
			if (judgeHospitalWhetherSettledIn()) {
				hospitId = "医院未入驻";
			}
			if ("admin".equals(user.getUsername())) {
    			hospitId = "admin";
    		}
    		int year = Calendar.getInstance().get(Calendar.YEAR);
    		Map<String, String> obj = subscribeService.reservationStatistics(year, hospitId);
    		return jsonResult(obj);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
		}
    }
    
    /**
     * 本年度预约统计 详情
     */
    @RequestMapping("reservationStatisticsEvery")
    public String reservationStatisticsEvery(String status) {
    	try {
    		int year = Calendar.getInstance().get(Calendar.YEAR);
    		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		String hospitId = user.getHospitalId();
    		if ("admin".equals(user.getUsername())) {
    			hospitId = "admin";
    		}
    		if (judgeHospitalWhetherSettledIn()) {
    			hospitId = "医院未入驻";
    		}
//    		String hospitId = "185";
    		Map<String, String> obj = subscribeService.reservationStatisticsEvery(year, month, hospitId, status);
    		return jsonResult(obj);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
		}
    }
    
    /**
     * 近期就诊率
     */
    @RequestMapping("recentVisitRate")
    public String recentVisitRate() {
    	try {
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		String hospitId = user.getHospitalId();
    		if ("admin".equals(user.getUsername())) {
    			hospitId = "admin";
    		}
    		if (judgeHospitalWhetherSettledIn()) {
    			hospitId = "医院未入驻";
    		}
    		
    		List<String> obj = subscribeService.recentVisitRate(hospitId);
    		return jsonResult(obj);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
		}
    }
    
    /**
     * 近一月度居民预约趋势统计 / 折线图
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("appointmentTrendStatistics")
    public String appointmentTrendStatistics() {
    	try {
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		String hospitId = user.getHospitalId();
    		if (judgeHospitalWhetherSettledIn()) {
    			hospitId = "医院未入驻";
    		}
    		if ("admin".equals(user.getUsername())) {
    			hospitId = "admin";
    		}
			Map<String, List> obj = subscribeService.trendStatistics(hospitId);
    		return jsonResult(obj);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
		}
    }

    /**
     * 近两年预约同比分析/近两年就诊同比分析
     */
    @ResponseBody
    @RequestMapping(value = "comparativeAnalysis")
    public String comparativeAnalysis(String status, String callback) {
    	try {
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		String hospitId = user.getHospitalId();
    		if (judgeHospitalWhetherSettledIn()) {
    			hospitId = "医院未入驻";
    		}
			if ("admin".equals(user.getUsername())) {
    			hospitId = "admin";
    		}
//    		String hospitId = "1";
    		Map<String, List<Integer>> obj = subscribeService.comparativeAnalysis(hospitId, status);
    		return jsonResult(obj);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonResult(null, -1, "异常错误");
    	}
    }
}
