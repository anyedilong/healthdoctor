package com.java.moudle.tripartdock.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.java.moudle.common.domain.JsonResult;
import com.java.moudle.tripartdock.service.SyncCustomerService;

@Named
@Transactional(readOnly = false)
public class SyncCustomerServiceImpl implements SyncCustomerService {

	@Inject
	private RestTemplate restTemplate;
	
	@Override
	public JsonResult syncCustomerInfo(String oldUsername, String newUsername, String pwd) {
		String url = "http://hcplatform/hcplatform/healthdoctor/appoint/syncHcCustomer";
		url = url + "?oldUsername="+oldUsername + "&newUsername=" + newUsername + "&pwd=" + pwd + "&name=" + newUsername + "&sfzh=110110110110";
		ResponseEntity<String> postForEntity = restTemplate.getForEntity(url, String.class);
		String body = postForEntity.getBody();
		JsonResult result = JSONObject.parseObject(body, JsonResult.class);
		return result;
	}
	
}
