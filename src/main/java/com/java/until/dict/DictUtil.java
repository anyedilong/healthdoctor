package com.java.until.dict;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;
import com.java.config.InstanceFactory;
import com.java.moudle.common.domain.InitDictDto;
import com.java.moudle.common.service.InitService;
import com.java.moudle.common.utils.properties.PropertiesUtil;
import com.java.until.StringUtil;
import com.java.until.cache.CacheUntil;
import com.java.until.cache.RedisCacheEmun;

public class DictUtil {


	/**
	 * 
	 * <li>描述:查询字典信息</li>
	 * <li>方法名称:getDictMapping</li>
	 * <li>参数:@return</li>
	 * <li>返回类型:Map<String,LinkedHashMap<String,String>></li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	public static Map<String, List<InitDictDto>> getDictMapping() {

		Map<String, List<InitDictDto>> dictMapping = (Map<String, List<InitDictDto>>) CacheUntil
				.get(RedisCacheEmun.DICT_CACHE, CacheUntil.DICT_ITEM, Map.class);
		if (null == dictMapping) {
			InstanceFactory.getInstance(InitService.class).InitData();
			dictMapping = (Map<String, List<InitDictDto>>) CacheUntil.get(RedisCacheEmun.DICT_CACHE, CacheUntil.DICT_ITEM,
					Map.class);
		}

		// 判断 如果 类型不正确，则转换
		for (Entry<String, List<InitDictDto>> entry : dictMapping.entrySet()) {
			String key = entry.getKey();
			List dictList = entry.getValue();

			if (dictList instanceof JSONArray) {
				JSONArray a = ((JSONArray) dictList);
				dictList = a.parseArray(a.toJSONString(), InitDictDto.class);
				dictMapping.put(key, dictList);
			} else {
				break;
			}
		}
		return dictMapping;
	}

	/**
	 * 
	 * @Description 获取字典值
	 * @param parentCode
	 * @param code
	 * @return
	 * @author sen
	 * @Date 2016年12月19日 下午1:27:00
	 */
	public static List<InitDictDto> getDict(String parentCode) {
		try {
			Map<String, List<InitDictDto>> dictMapping = getDictMapping();

			if (dictMapping.containsKey(parentCode)) {
				List<InitDictDto> itemList = dictMapping.get(parentCode);

				return itemList;
			}

		} catch (Exception e) {
			// return "";
		}

		return null;

	}

	/**
	 * 
	 * @Description 获取字典值
	 * @param parentCode
	 * @param codes
	 * @return
	 * @author sen
	 * @Date 2016年12月19日 下午1:27:00
	 */
	public static String getDictValue(String parentCode, String codes) {
		if (StringUtil.isNull(codes)) {
			return "";
		}

		String _parentCode = PropertiesUtil.getDict(parentCode);
		String rsStr = "";
		try {
			if (!StringUtil.isNull(codes)) {

				String[] codeArray = codes.split(",");

				List<InitDictDto> itemList = getDict(_parentCode);

				if (itemList != null) {
					InitDictDto qDict = new InitDictDto();
					qDict.setParentCode(_parentCode);
					for (String code : codeArray) {
						qDict.setCode(code);
						int index = itemList.indexOf(qDict);

						if (index >= 0) {
							InitDictDto dict = itemList.get(index);
							if (null != dict) {
								rsStr += dict.getName() + ",";
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// return "";
		}

		if (!StringUtil.isNull(rsStr) && rsStr.length() > 0) {
			rsStr = rsStr.substring(0, rsStr.length() - 1);
		}
		return rsStr;
	}
	
}
