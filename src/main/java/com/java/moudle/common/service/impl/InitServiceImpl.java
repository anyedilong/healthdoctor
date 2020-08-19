package com.java.moudle.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.domain.InitDictDto;
import com.java.moudle.common.service.InitService;
import com.java.moudle.system.dao.SysDictionaryDao;
import com.java.until.StringUtil;
import com.java.until.cache.CacheUntil;
import com.java.until.cache.RedisCacheEmun;

@Named
@Transactional(readOnly = false)
public class InitServiceImpl implements InitService {

	private static ReentrantLock reenLock = new ReentrantLock();
	@Inject
	private SysDictionaryDao dictionaryDao;
	
	/**
	 * @Description 初始化预约挂号的字典列表
	 * @author sen
	 * @Date 2019年12月10日 下午8:41:46
	 */
	@Override
	public void InitData() {
		try {
			Object cacheObj = CacheUntil.get(RedisCacheEmun.DICT_CACHE, CacheUntil.DICT_ITEM, Map.class);
			if (null != cacheObj) {
				return;
			}
			
			if(reenLock.tryLock()) {
				Object cacheObj1 = CacheUntil.get(RedisCacheEmun.DICT_CACHE, CacheUntil.DICT_ITEM, Map.class);
				if (null == cacheObj1) {
					//查询字典list
					List<InitDictDto> dictList = dictionaryDao.queryDictList();
					Map<String, List<InitDictDto>> dictMapping = new HashMap<String, List<InitDictDto>>();
					for (InitDictDto dict : dictList) {
						String parentCode = StringUtil.toString(dict.getParentCode());
		
						List<InitDictDto> itemList;
						if (!dictMapping.containsKey(parentCode)) {
							itemList = new ArrayList<InitDictDto>();
							dictMapping.put(parentCode, itemList);
						} else {
							itemList = dictMapping.get(parentCode);
						}
						itemList.add(dict);
					}
					
					CacheUntil.put(RedisCacheEmun.DICT_CACHE, CacheUntil.DICT_ITEM, dictMapping);
				}
				reenLock.unlock();
			}else {
				Thread.sleep(10000);
				this.InitData();
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
