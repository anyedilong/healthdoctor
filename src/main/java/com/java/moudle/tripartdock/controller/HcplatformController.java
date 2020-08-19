package com.java.moudle.tripartdock.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.appoint.domain.SubscribeResult;
import com.java.moudle.appoint.service.SubscribeService;
import com.java.moudle.common.controller.BaseController;
import com.java.moudle.common.domain.InitDictDto;
import com.java.moudle.personal.domain.MedicalPersonnel;
import com.java.moudle.personal.service.MedicalPersonnelService;
import com.java.moudle.system.domain.PatientCard;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.dto.SysUserDto;
import com.java.moudle.system.service.PatientCardService;
import com.java.moudle.system.service.SysUserService;
import com.java.moudle.tripartdock.dto.PatientPersonDto;
import com.java.moudle.webservice.domain.DoctorInfo;
import com.java.moudle.webservice.service.DoctorInfoService;
import com.java.until.StringUtil;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;
import com.java.until.ras.BCrypt;


@RestController
@RequestMapping("${regpath}/hcplatform")
public class HcplatformController extends BaseController {

    @Inject
    private MedicalPersonnelService medicalPersonnelService; 
    @Inject
    private PatientCardService patientCardService;
    @Inject
    private SysUserService userService;
    @Inject
	private SubscribeService subscribeService;
	@Inject
	private DoctorInfoService doctorInfoService;
    
	/**
     * 	验证登陆者是否在预约挂号系统注册过
     */
    @RequestMapping("getAregUser")
    public String getAregUser(String username, String pwd) {
    	try {
    		//确定预约人是否为本系统用户
    		SysUserDto login = userService.getUserInfoByName(username);
    		if (login == null) {
    			SysUser user = new SysUser();
	        	user.setId(UUIDUtil.getUUID());
	        	user.setUsername(username);
	        	user.setPassword(BCrypt.hashpw(pwd, BCrypt.gensalt()));
	        	user.setStatus("1");
	        	user.setType("0");
	        	user.setPwd(pwd);
	        	user.setMobilePhone(username);
	        	user.setAuthorities("a8dfc8c609d943d89f3b0cfea84ab3aa");
	        	user.setUpdateTime(new Date());
	        	user.setUpdateUser(user.getId());
	        	userService.save(user);
	        	return jsonResult(pwd);
    		}else {
    			return jsonResult(login.getPwd());
    		}
    	} catch (Exception e) {
        	return jsonResult(e.toString(), -1, "查询就诊人信息列表异常！");
    	}
    }
	
    /**
     * 查询就诊人列表
     */
    @RequestMapping("getPatientList")
    public String getPatientList(String username, String hospitalId) {
    	try {
    		//确定预约人是否为本系统用户
    		SysUserDto user = userService.getUserDtoByUsername(username);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		
    		List<MedicalPersonnel> list = medicalPersonnelService.getHcPatientList(user.getId(), hospitalId);
    		if(list == null || list.size() == 0) {
    			list = new ArrayList<>();
    		}
    		return jsonResult(list);
    	} catch (Exception e) {
        	return jsonResult(e.toString(), -1, "查询就诊人信息列表异常！");
    	}
    }
    
    /**
     * 查询默认就诊人
     */
    @RequestMapping("getPatientDefaultInfo")
    public String getPatientDefaultInfo(String username, String hospitalId) {
    	try {
    		//确定预约人是否为本系统用户
    		SysUserDto user = userService.getUserDtoByUsername(username);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		MedicalPersonnel medicalPersonnel = medicalPersonnelService.getHcPatientDefaultInfo(user.getId(), hospitalId);
    		return jsonResult(medicalPersonnel);
    	} catch (Exception e) {
        	return jsonResult(e.toString(), -1, "查询默认就诊人信息异常！");
    	}
    }
   
	/**
	 * 查询就诊人基本信息
	 */
    @RequestMapping("getPatientBaseInfo")
    public String getPatientBaseInfo(String id, String hospitalId) {
    	try {
    		MedicalPersonnel medicalPersonnel = medicalPersonnelService.getHcPatientBaseInfo(id, hospitalId);
    		return jsonResult(medicalPersonnel);
    	} catch (Exception e) {
        	return jsonResult(e.toString(), -1, "查询就诊人信息异常！");
    	}
    }
    
    /**
     * 新增或者更新就诊人信息
     */
    @RequestMapping("checkPatientExist")
    public String checkPatientExist(String sfzh, String username) {
    	try {
    		//确定预约人是否为本系统用户
    		SysUserDto user = userService.getUserDtoByUsername(username);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		//查询就诊人是否已添加
    		int count = medicalPersonnelService.isExist(sfzh, "", user.getId());
    		if(count > 0) {
    			return jsonResult("", 1004, "就诊人已存在");
    		}else {
    			return jsonResult("", 0, "就诊人可以添加");
    		}
    	} catch (Exception e) {
        	return jsonResult(e.toString(), -1, "更新就诊人信息异常！");
    	}
    }
    
    /**
     * 新增或者更新就诊人信息
     */
    @RequestMapping("saveOrupdatePatientBaseInfo")
    public String saveOrupdatePatientBaseInfo(MedicalPersonnel m, String username) {
    	try {
    		//确定预约人是否为本系统用户
    		SysUserDto user = userService.getUserDtoByUsername(username);
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
     * 	删除就诊人信息 -> 更改就诊人状态信息
     */
    @RequestMapping("deletePatientBaseInfo")
    public String deletePatientBaseInfo(String id, String username) {
    	try {
    		//确定预约人是否为本系统用户
    		SysUserDto user = userService.getUserDtoByUsername(username);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		MedicalPersonnel med = medicalPersonnelService.get(id);
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
    public String setPatientDefault(String id, String username) {
    	try {
    		//确定预约人是否为本系统用户
    		SysUserDto user = userService.getUserDtoByUsername(username);
    		if (user == null) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		
    		medicalPersonnelService.setPatientDefault(user.getId(), id);
    		return jsonResult();
    	} catch (Exception e) {
        	return jsonResult(e.toString(), -1, "设置默认就诊人异常！");
    	}
    }
    
    /**
     * @Description: 获取就诊人的某个医院的就诊卡号
     * @param @param card
     * @param @param phone
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("getMpCard")
	public String getMpCard(String hospitalId, String mpId, String username) {
    	try{
    		//确定预约人是否为本系统用户
    		SysUserDto user = userService.getUserDtoByUsername(username);
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
     * @Description: 获取登录人的就诊人的就诊卡信息
     * @param @param card
     * @param @param phone
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("getMpCardInfoByUserId")
	public String getMpCardInfoByUserId(String username) {
    	try{
    		//确定预约人是否为本系统用户
    		SysUserDto user = userService.getUserDtoByUsername(username);
        	if(user == null || user.getId() == null) {
        		return jsonResult(null, 1000, "用户未登录");
        	}
        	List<PatientPersonDto> list = patientCardService.getCardNumInfoList(user.getId());
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
    @RequestMapping("saveMpCard")
	public String saveMpCard(PatientCard card, String username) {
    	try{
    		//确定预约人是否为本系统用户
    		SysUserDto user = userService.getUserDtoByUsername(username);
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
    @RequestMapping("deleteMpCard")
	public String deletesMpCard(String id, String username) {
    	try{
    		//确定预约人是否为本系统用户
    		SysUserDto user = userService.getUserDtoByUsername(username);
        	if(user == null || user.getId() == null) {
        		return jsonResult(null, 1000, "用户未登录");
        	}
    		
    		PatientCard info = patientCardService.get(id);
    		info.setStatus("1");
    		patientCardService.save(info);
    		return jsonResult();
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
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
	 * 获取预约信息列表
	 */
	@RequestMapping("getMySubscribeList")
	public String getMySubscribeList(String username, String startTime, String endTime, String status, String ifevaluate, PageModel page) {
		try {
			//确定预约人是否为本系统用户
    		SysUserDto user = userService.getUserDtoByUsername(username);
        	if(user == null || user.getId() == null) {
        		return jsonResult(null, 1000, "用户未登录");
        	}
    		subscribeService.getSubscribeInfoList(user.getId(), startTime, endTime, ifevaluate, status, page);
    		return jsonResultString(page);
		} catch (Exception e) {
			return jsonResult();
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
	public String getFollowDoctorInfo(String username) {
		try {
			//确定预约人是否为本系统用户
    		SysUserDto user = userService.getUserDtoByUsername(username);
        	if(user == null || user.getId() == null) {
        		return jsonResult(null, 1000, "用户未登录");
        	}
    		List<DoctorInfo> list = doctorInfoService.getFollowDoctorInfo(user.getId());
			return jsonResult(list);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1, "查询异常！");
		}
	}
	
	/**
	 * 医生关注保存
	 */
	@RequestMapping("saveHcDoctorConcern")
	public String saveHcDoctorConcern(String doctorId, String username) {
		try{
			//确定预约人是否为本系统用户
    		SysUserDto user = userService.getUserDtoByUsername(username);
        	if(user == null || user.getId() == null) {
        		return jsonResult(null, 1000, "用户未登录");
        	}
    		return jsonResult(doctorInfoService.saveDoctorConcern(doctorId, user.getId()));
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
	
	/**
	 * 查询医生是否被关注
	 */
	@RequestMapping("queryHcDoctorConcern")
	public String queryHcDoctorConcern(String doctorId, String username) {
		try{
			//确定预约人是否为本系统用户
    		SysUserDto user = userService.getUserDtoByUsername(username);
        	if(user == null || user.getId() == null) {
        		return jsonResult(null, 1000, "用户未登录");
        	}
    		return jsonResult(doctorInfoService.queryConcernInfo(doctorId, user.getId()));
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
	
	/**
	 * 取消医生关注
	 */
	@RequestMapping("quitHcDoctorConcern")
	public String quitHcDoctorConcern(String doctorId, String username) {
		try{
			//确定预约人是否为本系统用户
    		SysUserDto user = userService.getUserDtoByUsername(username);
        	if(user == null || user.getId() == null) {
        		return jsonResult(null, 1000, "用户未登录");
        	}
    		doctorInfoService.quitDoctorConcern(doctorId, user.getId());
    		return jsonResult();
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
	/**
     * 	验证登陆者是否在预约挂号系统注册过, 没有自动注册
     */
    @RequestMapping("syncHcCustomer")
	public String syncHcCustomer(String oldUsername, String newUsername, String pwd) {
    	try {
    		//确定预约人是否已是本系统用户
    		SysUserDto login = userService.getUserInfoByName(oldUsername);
    		if (login == null) {
    			SysUser user = new SysUser();
	        	user.setId(UUIDUtil.getUUID());
	        	user.setUsername(newUsername);
	        	user.setMobilePhone(newUsername);
	        	user.setPassword(BCrypt.hashpw(pwd, BCrypt.gensalt()));
	        	user.setStatus("1");
	        	user.setType("0");
	        	user.setPwd(pwd);
	        	user.setAuthorities("a8dfc8c609d943d89f3b0cfea84ab3aa");
	        	user.setUpdateTime(new Date());
	        	user.setUpdateUser(user.getId());
	        	userService.save(user);
    		}else {
    			SysUser user = userService.get(login.getId());
    			user.setUsername(newUsername);
    			user.setMobilePhone(newUsername);
    			user.setPassword(BCrypt.hashpw(pwd, BCrypt.gensalt()));
    			user.setPwd(pwd);
    			userService.save(user);
    		}
    		return jsonResult();
    	} catch (Exception e) {
        	return jsonResult(e.toString(), -1, "同步惠民平台用户异常！");
    	}
	}
}
