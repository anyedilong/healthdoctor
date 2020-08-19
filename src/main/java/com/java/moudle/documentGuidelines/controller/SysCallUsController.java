package com.java.moudle.documentGuidelines.controller;


import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.documentGuidelines.domain.SysCallUs;
import com.java.moudle.documentGuidelines.service.SysCallUsService;

@RestController
@RequestMapping("${regpath}/sysCallUs")
public class SysCallUsController extends BaseController{
	
	@Inject
	SysCallUsService sysCallUsService;
	
	/**
	 * 查看关于我们
	 */
	@RequestMapping("getCallInfo")
	public String getCallInfo() {
		try {
			List<SysCallUs> sysCallUs = sysCallUsService.getSysCallUsInfo();
			return jsonResult(sysCallUs);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1 ,"异常错误");
		}
	}
	
	/**
	 * 修改添加
	 */
	@RequestMapping("addOrUpdateCallInfo")
	public String addOrUpdateCallInfo(SysCallUs sysCallUs) {
		try {
			sysCallUs.setId("d1f409e3419b4e9686d1ec3e7ac105c4");
			sysCallUsService.addOrUpdateCallInfo(sysCallUs);
			return jsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1 ,"异常错误");
		}
	}
}
