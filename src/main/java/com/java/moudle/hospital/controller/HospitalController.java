package com.java.moudle.hospital.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.hospital.domain.HospitalInfo;
import com.java.moudle.hospital.service.HospitalInfoService;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.service.SysUserService;
import com.java.until.CommonUtils;
import com.java.until.StringUtil;
import com.java.until.SysUtil;
import com.java.until.dba.PageModel;

/**
 * @ClassName: DoctorArrangementController
 * @Description: 从his获取医生的可预约情况
 * @author Administrator
 * @date 2019年12月2日
 */
@RestController
@RequestMapping("${regpath}/hospital")
public class HospitalController extends BaseController {

	@Inject
	private HospitalInfoService hospitalInfoService;
	@Inject
	private SysUserService sysUserService;
	
	/**
	 * @Description: 获取医院列表(分页) 
	 * @param @return
	 * @return JsonResult 
	 */
	@RequestMapping("getHospitalPage")
	public String getHospitalPage(HospitalInfo info, PageModel page) {
		try {
			if (info != null) {
				hospitalInfoService.getHospitalPage(info, page);
				return jsonResult(page);
			} else {
				return jsonResult(null, 10001, "参数为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(null, 10001, "系统错误");
		}
	}
	
	/**
	 * @Description: 医院保存 
	 * @param @return
	 * @return JsonResult 
	 */
	@RequestMapping("saveHospitalInfo")
	public String saveHospitalInfo(HospitalInfo info) {
		try {
			if (info != null) {
				String username = SysUtil.sysUser(request, response).getUsername();
				//管理员注册医疗机构时不需要审核
				if("admin".equals(username)) {
					info.setStatus("2");
				}else {
					info.setStatus("1");
				}
				SysUser paramUser = new SysUser();
				paramUser.setNickname(info.getName());
				SysUser user = sysUserService.getUserInfoByCon(paramUser);
				if(!"admin".equals(user.getUsername()) && !StringUtil.isNull(user.getHospitalId()) && StringUtil.isNull(info.getId())) 
					return jsonResult(null, 10001, "该用户已注册过医疗机构");
				//验证his地址是否可用
				Boolean flag = CommonUtils.hisUrlIsReach(info.getHisInterfaceUrl());
				if(!flag) {
					return jsonResult(null, 10000, "his地址不可用");
				}
				hospitalInfoService.saveHospitalInfo(info, user);
				return jsonResult();
			} else {
				return jsonResult(null, 10001, "参数为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(null, 10001, "系统错误");
		}
	}
	
	/**
	 * @Description: 更新医院的状态
	 * @param @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping("updateHospitalStatus")
	public String updateHospitalStatus(String id, String status, String remark) {
		try {
			if (id != null) {
				hospitalInfoService.updateHospitalStatus(id, status, remark);
				return jsonResult();
			} else {
				return jsonResult(null, 10001, "参数为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(null, 10001, "系统错误");
		}
	}
	
}
