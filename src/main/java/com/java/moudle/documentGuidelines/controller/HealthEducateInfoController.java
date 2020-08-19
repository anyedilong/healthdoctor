package com.java.moudle.documentGuidelines.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.java.moudle.common.controller.BaseController;
import com.java.moudle.documentGuidelines.domain.HealthEducateInfo;
import com.java.moudle.documentGuidelines.service.HealthEducateInfoService;
import com.java.moudle.hospital.domain.HospitalInfo;
import com.java.moudle.hospital.service.HospitalInfoService;
import com.java.moudle.system.domain.SysUser;
import com.java.until.StringUtil;
import com.java.until.SysUtil;
import com.java.until.dba.PageModel;


@RestController
@RequestMapping("${regpath}/healthEducateInfo")
public class HealthEducateInfoController extends BaseController {
	
	@Inject
	HealthEducateInfoService healthEducateInfoService;
	
	@Inject
	HospitalInfoService hospitalInfoService;
	/**
	 * 查询健康科普列表分页
	 */
	@RequestMapping("getHealthEducateInfoList")
	public String getHealthEducateInfoList(PageModel page,String startTime, String endTime, String hospitalId) {
		try {
			SysUser user = SysUtil.sysUser(request, response);

			// 医院未入驻拦截
			if (judgeHospitalWhetherSettledIn()) {
				return jsonResult("", 9927, "医院未入驻");
			}
			
			if (user != null) {
				// 判断是不是卫健委账户
				String userName = user.getUsername();
				if ("admin".equals(userName)) {
					hospitalId = "admin";
				} else if ("1".equals(user.getType())) {
					hospitalId = user.getHospitalId();
				}
			} else {
				user = new SysUser();
				user.setType("0");
			}
			
			// 分页对象
    		if(page == null){
				page = new PageModel();
			}
			healthEducateInfoService.getHealthEducateInfoList(page, startTime, endTime, hospitalId, user.getType());
			@SuppressWarnings("unchecked")
			List<HealthEducateInfo> list = page.getList();
			for (int a = 0; a < page.getList().size(); a++) {
				HealthEducateInfo info = list.get(a);
				HospitalInfo hospitalInfo = hospitalInfoService.getHospitalDetail(info.getHospitalId());
				if(hospitalInfo != null)
					info.setHospitalName(hospitalInfo.getName());
			}
			return jsonResult(page);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1, "异常错误");
		}
	}
	
	/**
	 * 查看健康科普详情
	 */
	@RequestMapping("getHealthEducateInfo")
	public String getHealthEducateInfo(String id) {
		try {
			// 医院未入驻拦截
			if (judgeHospitalWhetherSettledIn()) {
				return jsonResult("", 9927, "医院未入驻");
			}
			
			HealthEducateInfo info = healthEducateInfoService.getHealthEducateInfo(id);
			HospitalInfo hospitalInfo = hospitalInfoService.getHospitalDetail(info.getHospitalId());
			if(hospitalInfo != null)
				info.setHospitalName(hospitalInfo.getName());
			return jsonResult(info);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1, "异常错误");
		}
	}
	
	/**
	 * 删除健康科普 / 更改状态
	 */
	@RequestMapping("deleteHealthEducateInfo")
	public String deleteHealthEducateInfo(String id) {
		try {
			// 医院未入驻拦截
			if (judgeHospitalWhetherSettledIn()) {
				return jsonResult("", 9927, "医院未入驻");
			}
			
			healthEducateInfoService.deleteHealthEducateInfo(id);
			return jsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1, "异常错误");
		}
	}
	
	/**
	 * 添加健康科普 / 修改健康科普
	 */
	@RequestMapping("addOrUpdateHealthEducateInfo")
	public String addOrUpdateHealthEducateInfo(HealthEducateInfo healthEducateInfo) {
		try {
			// 医院未入驻拦截
			if (judgeHospitalWhetherSettledIn()) {
				return jsonResult("", 9927, "医院未入驻");
			}
			SysUser user = SysUtil.sysUser(request, response);
			if (StringUtil.isNull(user)) {
				return jsonResult("用户未登录", 1004, "用户未登录");
			}
			
			// 判断是不是卫健委账户
			String userName = user.getUsername();
			
			// 卫健委的不需要审核
			if ("admin".equals(userName)) {
				healthEducateInfo.setStatus("1");
				
			// 医院提交的需要审核
			} else if ("1".equals(user.getType())) {
				healthEducateInfo.setStatus("4");
				
			// 其他的废弃
			} else {
				healthEducateInfo.setStatus("3");
			}
			healthEducateInfo.setUpdateUser(user.getId());
			healthEducateInfo.setHospitalId(user.getHospitalId());

			healthEducateInfoService.addOrUpdateHealthEducateInfo(healthEducateInfo);
			return jsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1, "异常错误");
		}
	}
	
	/**
	 * 保存健康科普不提交
	 */
	@RequestMapping("saveHealthEducateInfo")
	public String saveHealthEducateInfo(HealthEducateInfo healthEducateInfo) {
		try {
			SysUser user = SysUtil.sysUser(request, response);
			if (StringUtil.isNull(user)) {
				return jsonResult("用户未登录", 1004, "用户未登录");
			}
			// 医院未入驻拦截
			if (judgeHospitalWhetherSettledIn()) {
				return jsonResult("", 9927, "医院未入驻");
			}
			healthEducateInfo.setStatus("0");
			// 保存接口状态为0
			healthEducateInfo.setUpdateUser(user.getId());
			healthEducateInfo.setHospitalId(user.getHospitalId());
			healthEducateInfoService.addOrUpdateHealthEducateInfo(healthEducateInfo);
			return jsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1, "异常错误");
		}
	}
	
	/**
	 * 审核健康科普
	 */
	@RequestMapping("auditHealthEducateInfo")
	public String auditHealthEducateInfo(String id, String status) {
		try {
			SysUser user = SysUtil.sysUser(request, response);
			if (!"admin".equals(user.getUsername()) && "1".equals(status)) {
				return jsonResult("", 1001, "权限不足");
			}
			healthEducateInfoService.auditHealthEducateInfo(id, status);
			return jsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1, "异常错误");
		}
	}
}
