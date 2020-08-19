package com.java.moudle.system.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.domain.SysArea;
import com.java.moudle.system.service.SysAreaService;


@RestController
@RequestMapping("${regpath}/area")
public class SysAreaController extends BaseController {

    @Inject
    private SysAreaService areaService;
   
	
    /**
     * @Description: 根据父级区划code获取下一级的区划
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getAreaList")
    public String getAreaList(SysArea area){
    	try{
    		//String areaCode = area.getAreaCode().substring(0, 4);
    		//area.setAreaCode(areaCode);
    		List<SysArea> list = areaService.getAreaList(area);
    		return jsonResult(list);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * 根据id查询下一级区划列表信息
     */
    @RequestMapping("getAreaListById")
    public String getAreaListById(String id){
    	try{
    		//System.out.println(id);
    		List<SysArea> list = areaService.getAreaListById(id);
    		return jsonResult(list);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
}
