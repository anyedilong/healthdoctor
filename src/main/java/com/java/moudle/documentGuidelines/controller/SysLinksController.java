package com.java.moudle.documentGuidelines.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.documentGuidelines.domain.SysLinks;
import com.java.moudle.documentGuidelines.service.SysLinksService;
import com.java.until.StringUtil;
import com.java.until.dba.PageModel;


@RestController
@RequestMapping("${regpath}/sysLinks")
public class SysLinksController extends BaseController {
	
	@Inject
	SysLinksService sysLinksService;
	
	/**
	 * 查询列表
	 */
	@RequestMapping("getSysLinksInfoList")
	public String getSysLinksInfoList(String pageNo, String pageSize, String startTime, String endTime) {
		try {
			PageModel page = new PageModel();
			if (StringUtil.isNull(pageNo) || StringUtil.isNull(pageSize)) {
				List<SysLinks> list = sysLinksService.getSysLinksInfoList(startTime, endTime);
				page.setList(list);
			} else {
				page.setPageNo(Integer.valueOf(pageNo));
				page.setPageSize(Integer.parseInt(pageSize));
				sysLinksService.getSysLinksInfoList(page, startTime, endTime);
			}
			return jsonResult(page);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1, "异常错误");
		}
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping("getSysLinksInfo")
	public String getSysLinksInfo(String id) {
		try {
			SysLinks info = sysLinksService.getSysLinksInfo(id);
			return jsonResult(info);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1, "异常错误");
		}
	}
	
	/**
	 * 删除 / 更改状态
	 */
	@RequestMapping("deleteSysLinksInfo")
	public String deleteSysLinksInfo(String id) {
		try {
			sysLinksService.deleteSysLinksInfo(id);
			return jsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1, "异常错误");
		}
	}
	
	/**
	 * 添加 / 修改
	 */
	@RequestMapping("addOrUpdateSysLinksInfo")
	public String addOrUpdateSysLinksInfo(SysLinks SysLinks) {
		try {
			sysLinksService.addOrUpdateSysLinksInfo(SysLinks);
			return jsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(e.toString(), -1, "异常错误");
		}
	}
}
