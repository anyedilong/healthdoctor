package com.java.moudle.appoint.service.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.appoint.dao.SubscribeInfoHistoryDao;
import com.java.moudle.appoint.domain.SubscribeInfoHistory;
import com.java.moudle.appoint.service.SubscribeHistoryService;
import com.java.moudle.common.service.impl.BaseServiceImpl;

@Named
@Transactional(readOnly = false)
public class SubscribeHistoryServiceImpl  extends BaseServiceImpl<SubscribeInfoHistoryDao, SubscribeInfoHistory> implements SubscribeHistoryService{

	
}
