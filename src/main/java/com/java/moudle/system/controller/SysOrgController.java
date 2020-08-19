package com.java.moudle.system.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.service.SysOrgService;


@RestController
@RequestMapping("${regpath}/org")
public class SysOrgController extends BaseController {

    @Inject
    private SysOrgService orgService;
   
	

}
