package com.java.until.dba;

import java.io.BufferedReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.java.until.CommonException;
import com.java.until.DateUtils;
import com.java.until.StringUtil;

public class EntityManagerDao {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	protected EntityManager entityManager;
	
	//查询集合
	
	protected <T> List<T> queryList(String sql,Object param,Class<T> clazz){
		Map<String, Object> map = new HashMap<String, Object>();
		if (param != null) {
			if (param instanceof Map) {
				map = (Map<String, Object>) param;
			} else {
				if (param.getClass() instanceof Class<?>) {
					getParamMap(sql, map, param);
				}
			}
		}
		List<T> list = queryResult(sql,map, 0, 0, clazz);
		return list;
	}
	//分页查询
	protected <T> void queryPageList(String sql,Object param,PageModel page,Class<T> clazz){
		 Map<String,Object> map=new HashMap<String,Object>();
		 if(param!=null) {
			  if(param instanceof Map) {
				  map=(Map<String, Object>) param;
			  }else {
				  if(param.getClass() instanceof Class<?>) {
					  getParamMap(sql, map, param);
				  }
			  }
			  
		 }
		 long count=queryCount(sql, map);
		 page.setCount(count);
		 List<T> list = queryResult(sql,map,page.getNumIndex()-1,page.getPageSize(), clazz);
		 if(list!=null&&list.size()>0) {
			 page.setList(list);
		 }
	}

	// 查询单条
	protected <T> T queryOne(String sql, Object param, Class<T> clazz) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (param != null) {
			if (param instanceof Map) {
				map = (Map<String, Object>) param;
			} else {
				if (param.getClass() instanceof Class<?>) {
					getParamMap(sql, map, param);
				}
			}
		}
		List<T> list = queryResult(sql, map, 0, 1, clazz);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	//分页查询条数
	protected long  queryCount(String sql,Map<String,Object> map) {
		String countsql=String.format("select count(1) from (%s) tmp_count",sql);
		NativeQuery query = createQuery(countsql,map);
		Object obj=query.getSingleResult();
		if(obj!=null) {
			return StringUtil.toLong(obj);
		}
		return 0;
	}

	// 查询
	protected <T> List<T> queryResult(String sql, Map<String, Object> param, int firstResult, int maxResults,
			Class<T> clazz) {
		NativeQuery query = createQuery(sql, param);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (firstResult > 0) {
			query.setFirstResult(firstResult);
		}
		if (maxResults > 0) {
			query.setMaxResults(maxResults);
		}
		entityManager.close();
		List<Map<String, Object>> list = query.getResultList();
		
		logger.info(String.format("SQL为:%s", sql));
		logger.info(String.format("参数为:%s", param));
		
		if (list != null && list.size() > 0) {
			if (clazz == Map.class) {
				return (List<T>) list;
			} else if (clazz == String.class || clazz == Integer.class || clazz == Long.class || clazz == Double.class
					|| clazz == Float.class || clazz == Date.class) {
				return mapToBasicType(clazz,list);
			}else {
				 
				return getlist(list,clazz);
			}

		}
		return null;
	}

	

	private NativeQuery createQuery(String sql, Map<String, Object> param) {
		NativeQuery query = createNativeQuery(sql);
		if (param != null) {
			for (Map.Entry<String, Object> entry : param.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}

		return query;
	}

	private NativeQuery createNativeQuery(String sql) {
		Session session = entityManager.unwrap(Session.class);
		return session.createNativeQuery(sql);
	}
	 
	public <T> List<T> mapToBasicType(Class<T> clazz,List<Map<String, Object>> resultMapList){
        if(resultMapList!=null&&resultMapList.size()>0){
            List list = new ArrayList();
            for(Map<String, Object> resultMap : resultMapList){
                for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
                       list.add(getValue(entry.getValue(), clazz));
                }
            }
            return list;
        }
        return  null;
	}
	
	//list转换
	 private <T> List<T> getlist(List<Map<String,Object>> returnList,Class<T> clazz) {
		 List<T> list=new ArrayList<>();
		 Map<String,Field> fieldMap=getField(clazz);
		 for(Map<String,Object> map:returnList) {
			 T t=getObject(map,fieldMap,clazz);
			 list.add(t);
		 }
		return list;
	 }
	 private <T> T getObject(Map<String, Object> returnmap,Map<String,Field> fieldMap,Class<T> clazz) {
		      try {
		
		      T object=clazz.newInstance();
		      for(Map.Entry<String,Object> entry:returnmap.entrySet()) {
		    	  String key=entry.getKey().toUpperCase().replace("_","");
		    	  Object value=entry.getValue();
		    	  if(value!=null) {
		    	     if(fieldMap.containsKey(key)){
		    	    	   Field field=fieldMap.get(key);
							Method  method=clazz.getDeclaredMethod(setFiledName(field.getName()),field.getType());
							method.invoke(object,getValue(value,field.getType()));
							
		    	    }	  
		    	  }
		      }
		      return object;
		      } catch (Exception e) {
		    		e.printStackTrace();
		}
		return null;
	}
	 
	 private Object getValue(Object value,Type type) {
		 if(type==int.class || type==Integer.class) {
			return StringUtil.toInteger(value);
		 }else if (value instanceof Clob) {
			 return ClobToString((Clob)value);
		 }else if(type==String.class){
			 return StringUtil.toString(value);
		 }else if(type==boolean.class){
			 return StringUtil.toBoolean(value);
		 }else if(type==Double.class || type==double.class){
			 return StringUtil.toDouble(value);
		 }else if(type==long.class || type==Long.class){
			 return StringUtil.toLong(value);
		 }else if(value instanceof Date){
			 return DateUtils.parseDate(value);
		 }else {
            if (type instanceof Map) {
                Map<String, Field> fieldMap = getField((Class) type);
                return mapToObject(fieldMap, (Class) type, (Map) value);
            }
        }
		return null;
	}
	 
    public String ClobToString(Clob clob) {
    	try {
            String reString = "";
            Reader is = clob.getCharacterStream();
            BufferedReader br = new BufferedReader(is);
            String s = br.readLine();
            StringBuffer sb = new StringBuffer();
            while (s != null) {
                sb.append(s);
                s = br.readLine();
            }
            reString = sb.toString();
            if(br!=null){
                br.close();
            }
            if(is!=null){
                is.close();
            }
            return reString;
    	} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//通过set赋值
	 private String setFiledName(String fieldName){
		 StringBuffer sb=new StringBuffer("set");
		 if (!StringUtil.isNull(fieldName)) {
		     String one=fieldName.substring(0,1);
		     sb.append(one.toUpperCase());
		     if(fieldName.length()>1){
		    	 sb.append(fieldName.substring(1));
		    	 return sb.toString();
		     }else {
		    	 return sb.toString();
		     }
		 }else {
			 return null;
		 }
	 }

	 //获取实体字段改驼峰式
	 private <T> Map<String,Field> getField(Class<T> clazz){
		 Map<String,Field> map=new HashMap<String,Field>();
		 Field[] fields=clazz.getDeclaredFields();
		 for(Field field:fields) {
				String fieldName = field.getName();
				fieldName=fieldName.toUpperCase().replace("_","");
				map.put(fieldName,field);
		 }
		  return map;
	 }

	// 实体转map
	private void getParamMap(String sql, Map<String, Object> map, Object param) {
		Pattern pat = Pattern.compile(":[0-9a-zA-z|-|_|.]*");
		Matcher mat = pat.matcher(sql);
		while (mat.find()) {
			String key = mat.group(0).substring(1);
			if (!map.containsKey(key)) {
				Object value = getValue(key, param);
				map.put(key, value);
			}
		}
	}

	// 实体类通过name取value
	private Object getValue(String fieldname, Object param) {
		try {
			if (param != null) {
				Method method = param.getClass().getDeclaredMethod(getMethod(fieldname));
				return method.invoke(param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommonException(String.format("%s类中无属性%s", param.getClass().getName(), fieldname));
		}
		return null;
	}

	private String getMethod(String fieldname) {
		StringBuffer stringBuffer = new StringBuffer("get");
		if (!StringUtil.isNull(fieldname)) {
			String one = fieldname.substring(0, 1);
			if (!one.equals(fieldname.toUpperCase())) {
				stringBuffer.append(one.toUpperCase());
			} else {
				stringBuffer.append(one);
			}
			if (fieldname.length() > 1) {
				stringBuffer.append(fieldname.substring(1));
			}
			return stringBuffer.toString();
		}

		return null;
	}
	private <T> T mapToObject(Map<String, Field> fieldMap, Class<T> clazz, Map<String, Object> resultMap){
        if (resultMap != null) {
            try {
                T object = clazz.newInstance();
                for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
                    String key = entry.getKey();
                    key = key.toUpperCase().replace("_", "");
                    Object value = entry.getValue();
                    if (null == value) {
                        continue;
                    }
                    if (fieldMap.containsKey(key)) {
                        Field field = fieldMap.get(key);
                        // 得到property对应的setter方法
                        try {
                            Method method = clazz.getMethod(setFiledName(field.getName()), field.getType());
                            method.invoke(object, getValue(value, field.getType()));
                        } catch (Exception e) {
                        	logger.error(String.format("%s字段转换失败", key));
                        	e.printStackTrace();
                        }
                    }
                }
                return object;
            } catch (InstantiationException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return null;
    }
}
