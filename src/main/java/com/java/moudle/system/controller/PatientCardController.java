package com.java.moudle.system.controller;


import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.domain.PatientCard;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.service.PatientCardService;
import com.java.until.SysUtil;
import com.java.until.UUIDUtil;


@RestController
@RequestMapping("${regpath}/patientcard")
public class PatientCardController extends BaseController {

    @Inject
    private PatientCardService patientCardService;
   
    /**
     * @Description: 保存就诊人的就诊卡信息
     * @param @param card
     * @param @param phone
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("getMpCard")
	public String save(String hospitalId, String mpId) {
    	try{
        	SysUser user = SysUtil.sysUser(request, response);
        	if(user == null || user.getId() == null) {
        		return jsonResult(null, 1000, "用户未登录");
        	}
        	List<PatientCard> list = patientCardService.getCardNum(hospitalId, mpId);
    		return jsonResult(list);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 保存就诊人的就诊卡信息
     * @param @param card
     * @param @param phone
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("save")
	public String save(PatientCard card) {
    	try{
    		SysUser user = SysUtil.sysUser(request, response);
        	if(user == null || user.getId() == null) {
        		return jsonResult(null, 1000, "用户未登录");
        	}
    		card.setId(UUIDUtil.getUUID());
    		card.setStatus("0");
    		card.setCreateUser(user.getId());
    		card.setCreateTime(new Date());
    		patientCardService.save(card);
    		return jsonResult();
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 删除就诊人的就诊卡信息
     * @param @param card
     * @param @param phone
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("delete")
	public String delete(String id) {
    	try{
    		PatientCard info = patientCardService.get(id);
    		info.setStatus("1");
    		patientCardService.save(info);
    		return jsonResult();
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
}
