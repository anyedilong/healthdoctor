package com.java.moudle.documentGuidelines.dao;


import javax.inject.Named;

import com.java.moudle.documentGuidelines.dao.repository.SysAboutUsRepository;
import com.java.moudle.documentGuidelines.domain.SysAboutUs;
import com.java.until.dba.BaseDao;

@Named
public class SysAboutUsDao extends BaseDao<SysAboutUsRepository, SysAboutUs> {
	
}
