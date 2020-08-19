package com.java.moudle.documentGuidelines.controller;


import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.documentGuidelines.domain.SysAboutUs;
import com.java.moudle.documentGuidelines.service.SysAboutUsService;

@RestController
@RequestMapping("${regpath}/sysAboutUs")
public class SysAboutUsController extends BaseController{
	
	@Inject
	SysAboutUsService sysAboutUsService;
	
	/**
	 * 查看关于我们
	 */
	@RequestMapping("getAboutInfo")
	public String getAboutInfo() {
		try {
			List<SysAboutUs> sysAboutUs = sysAboutUsService.getSysAboutUsInfo();
			return jsonResult(sysAboutUs);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1 ,"异常错误");
		}
	}
	
	/**
	 * 修改添加
	 */
	@RequestMapping("addOrUpdateAboutInfo")
	public String addOrUpdateAboutInfo(SysAboutUs sysAboutUs) {
		try {
			sysAboutUsService.addOrUpdateAboutInfo(sysAboutUs);
			return jsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1 ,"异常错误");
		}
	}
	
}
