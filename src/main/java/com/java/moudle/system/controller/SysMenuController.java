package com.java.moudle.system.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.domain.SysMenu;
import com.java.moudle.system.service.SysMenuService;
import com.java.until.SysUtil;

@RestController
@RequestMapping("${regpath}/menu")
public class SysMenuController extends BaseController {

	@Inject
	private SysMenuService menuService;

	/**
	 * 菜单树形查询
	 */
	@RequestMapping("getmenutree")
	public String getMenuTree() {
		try {
			String userId = SysUtil.sysUser(request, response).getId();
			List<SysMenu> list = menuService.getMenuTree(userId);
			return jsonResult(list);
		} catch (Exception e) {
			return jsonResult(null, 10001, "系统错误");
		}
	}

}
