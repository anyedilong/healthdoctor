package com.java.moudle.documentGuidelines.dao;


import javax.inject.Named;

import com.java.moudle.documentGuidelines.dao.repository.SysCallUsRepository;
import com.java.moudle.documentGuidelines.domain.SysCallUs;
import com.java.until.dba.BaseDao;

@Named
public class SysCallUsDao extends BaseDao<SysCallUsRepository, SysCallUs> {
	
}
