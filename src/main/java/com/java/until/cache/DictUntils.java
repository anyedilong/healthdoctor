package com.java.until.cache;

import java.util.List;

public class DictUntils {
  
	
	@SuppressWarnings("unchecked")
	public static  <T> T getDict(String dictType,String dictCode,Class<T> clazz){
		   List<DictDto> list=CacheUntil.getArray(RedisCacheEmun.DICT_CACHE,dictType,DictDto.class);
		   if(list!=null&&list.size()>0) {
			   DictDto dictDto=list.stream().filter(f -> f.getDictCode().equals(dictCode)).findFirst().orElse(null);
			   if(dictDto!=null) {
				   return (T)dictDto.getDictName();
			   }
		   }
		return null;
		
	}

}

