package com.java.moudle.documentGuidelines.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.common.domain.InitDictDto;
import com.java.moudle.documentGuidelines.domain.RegistrationGuide;
import com.java.moudle.documentGuidelines.service.RegistrationGuideService;
import com.java.moudle.system.domain.SysUser;
import com.java.until.SysUtil;
import com.java.until.dba.PageModel;


@RestController
@RequestMapping("${regpath}/regguide")
public class RegistrationGuideController extends BaseController {

    @Inject
    private RegistrationGuideService registrationGuideService;
   
	/**
	 * 获取挂号指南列表 不分页
	 */
    @RequestMapping("getGuideList")
    public String getGuideList(String startTime, String endTime, String classificationid) {
    	try {
    		List<RegistrationGuide> guideList = registrationGuideService.getGuideList(startTime, endTime, classificationid);
    		return jsonResult(guideList);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonResult(e.toString(),-1 ,"未知异常");
    	}
    }
    
    /**
     * 获取挂号指南列表 分页
     */
    @RequestMapping("getGuideListPaging")
    public String getGuideListPaging(PageModel page, String startTime, String endTime, String classificationid) {
    	try {
    		// 分页对象
    		if(page == null){
				page = new PageModel();
			}
    		registrationGuideService.getGuideList(page, startTime, endTime, classificationid);
    		return jsonResult(page);
    	} catch (Exception e) {
    		return jsonResult(e.toString(),-1 ,"未知异常");
    	}
    }
    
    /**
     * 获取挂号指南详情
     */
    @RequestMapping("getGuideInfoById")
    public String getGuideInfoById(String id) {
    	try {
    		RegistrationGuide guideInfo = registrationGuideService.getGuideInfoById(id);
    		return jsonResult(guideInfo);
    	} catch (Exception e) {
    		return jsonResult(e.toString(),-1 ,"未知异常");
    	}
    }
    
    /**
     * 添加/修改挂号指南
     */
    @RequestMapping("saveOrUpdateGuideInfo")
    public String saveOrUpdateGuideInfo(RegistrationGuide registrationGuide) {
    	try {
    		// 设置更新人
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		registrationGuide.setUpdateUser(user.getId());
    		registrationGuideService.saveOrUpdateGuideInfo(registrationGuide);
    		return jsonResult();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonResult(e.toString(),-1 ,"未知异常");
    	}
    }
    
    /**
     * 删除挂号指南
     */
    @RequestMapping("deleteGuideInfo")
    public String deleteGuideInfo(String id) {
    	try {
    		registrationGuideService.deleteOrFrozenGuideInfo(id, "3");
    		return jsonResult();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonResult(e.toString(),-1 ,"未知异常");
    	}
    }
    
    /**
     * 冻结挂号指南
     */
    @RequestMapping("frozenGuideInfo")
    public String frozenGuideInfo(String id) {
    	try {
    		registrationGuideService.deleteOrFrozenGuideInfo(id, "2");
    		return jsonResult();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonResult(e.toString(),-1 ,"未知异常");
    	}
    }
    
    /**
     * 获取挂号指导分类
     */
    @RequestMapping("getGuideClassify")
    public String getGuideClassify() {
    	try {
    		List<InitDictDto> list = registrationGuideService.getGuideClassify();
    		return jsonResult(list);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonResult(e.toString(),-1 ,"未知异常");
    	}
    }
}
