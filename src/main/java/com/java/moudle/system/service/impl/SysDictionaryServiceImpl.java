package com.java.moudle.system.service.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.SysDictionaryDao;
import com.java.moudle.system.domain.SysDictionary;
import com.java.moudle.system.service.SysDictionaryService;


@Named
@Transactional(readOnly = false)
public class SysDictionaryServiceImpl extends BaseServiceImpl<SysDictionaryDao, SysDictionary> implements SysDictionaryService{

	
	

}
