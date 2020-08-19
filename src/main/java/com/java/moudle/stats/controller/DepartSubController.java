package com.java.moudle.stats.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.stats.dto.DepartStatsDto;
import com.java.moudle.stats.service.DepartStatsService;
import com.java.until.StringUtil;
import com.java.until.SysUtil;
import com.java.until.dba.PageModel;


@RestController
@RequestMapping("${regpath}/departstats")
public class DepartSubController extends BaseController {

	@Inject
	private DepartStatsService departStatsService;
	
	
	/**
	 * @Description: 获取科室预约统计
	 * @param @param type
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("getDepartStatsList")
    public String getDepartStatsList(DepartStatsDto info, PageModel page){
    	try{
    		if(!StringUtil.isNull(info)) {
    			String hospitalId = SysUtil.sysUser(request, response).getHospitalId();
    			info.setHospitalId(hospitalId);
    			departStatsService.getDepartStatsList(info, page);
    			return jsonResult(page);
    		}else {
    			return jsonResult(null, 10000, "获取日期的类型为空");
    		}
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
	
	
	
}
