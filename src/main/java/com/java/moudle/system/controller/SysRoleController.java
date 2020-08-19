package com.java.moudle.system.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.service.SysRoleService;


@RestController
@RequestMapping("${regpath}/role")
public class SysRoleController extends BaseController {

    @Inject
    private SysRoleService roleService;
   
	

}
