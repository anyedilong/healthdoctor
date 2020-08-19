package com.java.moudle.personal.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.personal.domain.MedicalPersonnel;
import com.java.moudle.personal.service.MedicalPersonnelService;
import com.java.moudle.system.domain.SysUser;
import com.java.until.StringUtil;
import com.java.until.SysUtil;


@RestController
@RequestMapping("${personpath}/medperson")
public class MedicalPersonnelController extends BaseController {

    @Inject
    private MedicalPersonnelService medicalPersonnelService;    
    
    /**
     * 查询就诊人列表
     */
    @RequestMapping("getPatientList")
    public String getPatientList() {
    	try {
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		
    		List<MedicalPersonnel> list = medicalPersonnelService.getPatientList(user.getId());
    		return jsonResult(list);
    	} catch (Exception e) {
        	return jsonResult(e.toString(), -1, "查询就诊人信息列表异常！");
    	}
    }
    
    /**
     * 查询默认就诊人
     */
    @RequestMapping("getPatientDefaultInfo")
    public String getPatientDefaultInfo() {
    	try {
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		MedicalPersonnel medicalPersonnel = medicalPersonnelService.getPatientDefaultInfo(user.getId());
    		return jsonResult(medicalPersonnel);
    	} catch (Exception e) {
        	return jsonResult(e.toString(), -1, "查询默认就诊人信息异常！");
    	}
    }
   
	/**
	 * 查询就诊人基本信息
	 */
    @RequestMapping("getPatientBaseInfo")
    public String getPatientBaseInfo(String id) {
    	try {
    		MedicalPersonnel medicalPersonnel = medicalPersonnelService.getPatientBaseInfo(id);
    		return jsonResult(medicalPersonnel);
    	} catch (Exception e) {
        	return jsonResult(e.toString(), -1, "查询就诊人信息异常！");
    	}
    }
    
    /**
     * 新增或者更新就诊人信息
     */
    @RequestMapping("saveOrupdatePatientBaseInfo")
    public String saveOrupdatePatientBaseInfo(MedicalPersonnel m) {
    	try {
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		if(StringUtil.isNull(m.getId())) {
    			//查询就诊人是否已添加
        		int count = medicalPersonnelService.isExist(m.getSfzh(), m.getPhone(), user.getId());
        		if(count > 0 && StringUtil.isNull(m.getId())) {
        			return jsonResult("", 1004, "就诊人已存在");
        		}
    			List<MedicalPersonnel> list = medicalPersonnelService.getPatientList(user.getId());
        		if(list != null && list.size() > 7) {
        			return jsonResult("", 1004, "就诊人最多添加7个");
        		}
    		}
    		m.setUserId(user.getId());
    		m.setUpdateUser(user.getId());
    		medicalPersonnelService.saveOrupdatePatientBaseInfo(m);
    		return jsonResult();
    	} catch (Exception e) {
        	return jsonResult(e.toString(), -1, "更新就诊人信息异常！");
    	}
    }
    
    /**
     * 删除就诊人信息 -> 更改就诊人状态信息
     */
    @RequestMapping("deletePatientBaseInfo")
    public String deletePatientBaseInfo(String id) {
    	try {
    		MedicalPersonnel med = medicalPersonnelService.get(id);
    		SysUser user = SysUtil.sysUser(request, response);
    		if(!user.getUsername().equals(med.getPhone())) {
    			medicalPersonnelService.deletePatientBaseInfo(id);
    			return jsonResult();
    		}else {
    			return jsonResult("", 1001, "不能删除本人信息");
    		}
    	} catch (Exception e) {
        	return jsonResult(e.toString(), -1, "删除就诊人信息异常！");
    	}
    }
    
    /**
     * 设置默认就诊人
     */
    @RequestMapping("setPatientDefault")
    public String setPatientDefault(String id) {
    	try {
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		
    		medicalPersonnelService.setPatientDefault(user.getId(), id);
    		return jsonResult();
    	} catch (Exception e) {
        	return jsonResult(e.toString(), -1, "设置默认就诊人异常！");
    	}
    }
}
