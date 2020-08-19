package com.java.moudle.system.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.common.domain.InitDictDto;
import com.java.moudle.system.domain.SysDictionary;
import com.java.moudle.system.service.SysDictionaryService;
import com.java.until.UUIDUtil;
import com.java.until.dict.DictUtil;


@RestController
@RequestMapping("${regpath}/dict")
public class SysDictionaryController extends BaseController {

    @Inject
    private SysDictionaryService dictionaryService;
   
    /**
     * @Description: 字段信息保存
     * @param @param info
     * @param @return
     * @return String
     * @throws
     */
	@RequestMapping("saveDictInfo")
    public String saveDictInfo(SysDictionary info) {
    	try {
    		info.setId(UUIDUtil.getUUID());
    		info.setDeleteFlg(0);
    		info.setUpdateTime(new Date());
    		dictionaryService.save(info);
    		return jsonResult();
    	}catch(Exception e) {
    		e.printStackTrace();
    		return jsonResult(e.getMessage(), -1, "系统错误");
    	}
    }
	/**
	 * @Description: 查询医院类型
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("queryDictAreaList")
    public String queryDictAreaList() {
    	try {
    		List<InitDictDto> dict = DictUtil.getDict("1002");
    		return jsonResult(dict);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return jsonResult(e.getMessage(), -1, "系统错误");
    	}
    }
    
	/**
	 * @Description: 查询字典信息
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("queryDictConList")
    public String queryDictConList(String code) {
    	try {
    		List<InitDictDto> dict = DictUtil.getDict(code);
    		return jsonResult(dict);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return jsonResult(e.getMessage(), -1, "系统错误");
    	}
    }
}
