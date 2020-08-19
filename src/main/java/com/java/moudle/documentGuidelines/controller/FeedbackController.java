package com.java.moudle.documentGuidelines.controller;

import java.util.Date;

import javax.inject.Inject;

import org.bouncycastle.pqc.jcajce.provider.rainbow.SignatureSpi.withSha224;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.java.moudle.common.controller.BaseController;
import com.java.moudle.documentGuidelines.domain.Feedback;
import com.java.moudle.documentGuidelines.service.FeedbackService;
import com.java.moudle.system.domain.SysUser;
import com.java.until.AliyunSMSUtil;
import com.java.until.StringUtil;
import com.java.until.SysUtil;
import com.java.until.UUIDUtil;


@RestController
@RequestMapping("${regpath}/feedback")
public class FeedbackController extends BaseController {

    @Inject
    private FeedbackService FeedbackService;
  
    
	/**
	 * 添加意见反馈
	 */
    @RequestMapping("addOrUpdateFeedback")
    public String addOrUpdateFeedback(Feedback feedback) {
    	try {
    		SysUser user = SysUtil.sysUser(request, response);
    		if (user == null || StringUtil.isNull(user.getId())) {
    			return jsonResult("", 1004, "用户未登录");
    		}
    		if (StringUtil.isNull(feedback.getId())) {
    			feedback.setId(UUIDUtil.getUUID());
    			feedback.setFeedbackTime(new Date());
    			feedback.setFeedbackUser(user.getId());
    			feedback.setStatus("0");
    		} else {
    			feedback.setStatus("1");
    			feedback.setHandleUser(user.getId());
    			feedback.setFeedbackTime(new Date());
    		}
    		FeedbackService.save(feedback);
    		return jsonResult();
    	} catch (Exception e) {
			return jsonResult("", -1, "异常错误");
		}
    }
}
