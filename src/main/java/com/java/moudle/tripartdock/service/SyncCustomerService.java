package com.java.moudle.tripartdock.service;

import com.java.moudle.common.domain.JsonResult;

public interface SyncCustomerService   {

	JsonResult syncCustomerInfo(String oldUsername, String newUsername, String pwd);
}
