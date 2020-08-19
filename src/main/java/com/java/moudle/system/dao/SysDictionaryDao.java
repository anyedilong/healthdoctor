package com.java.moudle.system.dao;

import java.util.List;

import javax.inject.Named;

import com.java.moudle.common.domain.InitDictDto;
import com.java.moudle.system.dao.repository.SysDictionaryRepository;
import com.java.moudle.system.domain.SysDictionary;
import com.java.until.dba.BaseDao;

@Named
public class SysDictionaryDao extends BaseDao<SysDictionaryRepository, SysDictionary> {

	public List<InitDictDto> queryDictList() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id as id, a.parent_code as parentCode, a.name, a.code ");
		sql.append(" from sys_dictionary a ");
		sql.append(" where a.delete_flg = '0' ");
		return queryList(sql.toString(), null, InitDictDto.class);
	}

}
