package com.java.until.cache;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;
import com.java.until.JsonUntil;
import com.java.until.StringUtil;

public class CacheUntil {
   
	//预约挂号字典
	public static final String DICT_ITEM = "dict_healthdoctor_code";
	
	/**
	 * 
	 * @Description redis存储数据
	 * @param redisCacheEmun
	 * @param key
	 * @param value
	 * @author sen
	 * @Date 2016年11月17日 下午1:39:44
	 */
	public static void put(RedisCacheEmun redisCacheEmun, Object key, Object value) {
		put(redisCacheEmun.getRedisTemplate(), key, value, redisCacheEmun.getLiveTime());
	}

	/**
	 * 
	 * @Description 获取redis缓存
	 * @param redisCacheEmun
	 * @param key
	 * @return
	 * @author sen
	 * @Date 2016年11月17日 下午1:42:00
	 */
	public static <T> T get(RedisCacheEmun redisCacheEmun, Object key, Class<T> clazz) {
		return get(redisCacheEmun.getRedisTemplate(), key, clazz, redisCacheEmun.getLiveTime());
	}

	/**
	 * @Description 获取redis缓存 集合
	 * @param redisCacheEmun
	 * @param key
	 * @param clazz
	 * @return
	 * @author sen
	 * @Date 2017年1月17日 下午1:33:30
	 */
	public static <T> List<T> getArray(RedisCacheEmun redisCacheEmun, Object key, Class<T> clazz) {
		return getArray(redisCacheEmun.getRedisTemplate(), key, clazz, redisCacheEmun.getLiveTime());
	}

	public static void put(RedisTemplate<String, Object> redisTemplate, Object key, Object value, long liveTime) {

		if (null == value) {
			return;
		}
		if (value instanceof String) {
			if (StringUtil.isNull(value.toString())) {
				return;
			}
		}

		final String keyf = key.toString();
		if (StringUtil.isNull(keyf)) {
			return;
		}
		final Object valuef = value;
		final long liveTimef = liveTime;
		redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyb = keyf.getBytes();

				// 将数据转为json
				String json = JSON.toJSONString(valuef, JsonUntil.getSerializeconfigcamelcase());

				byte[] valueb = StringUtil.getBytes(json);
				connection.set(keyb, valueb);

				if (liveTimef > 0) {
					connection.expire(keyb, liveTimef);
				}

				return 1L;
			}
		});
	}
	
	public static <T> T get(RedisTemplate<String, Object> redisTemplate, Object key, final Class<T> clazz,
					long liveTime) {
				final String keyf = key.toString();
				System.out.println(keyf + "剩余失效" + redisTemplate.getExpire(key.toString()));
				final long liveTimef = liveTime;
				if (StringUtil.isNull(keyf)) {
					return null;
				}
				Object object;
				object = redisTemplate.execute(new RedisCallback<Object>() {
					public Object doInRedis(RedisConnection connection) throws DataAccessException {
						byte[] keyb = keyf.getBytes();
						byte[] value = connection.get(keyb);

						if (liveTimef > 0) {
							connection.expire(keyb, liveTimef);
						}
						if (value == null) {
							return null;
						}
						String json = StringUtil.toString(value);
						// 将json----》object
						Object obj = null;
						try {
							obj = JSON.parseObject(json, clazz,JsonUntil.getParserconfigcamelcase());
						} catch (Exception e) {
							e.printStackTrace();
						}
						return obj;
					}

				});

				return (T) object;
			}

		
		public static <T> List<T> getArray(RedisTemplate<String, Object> redisTemplate, Object key, final Class<T> clazz,
					long liveTime) {
				final String keyf = key.toString();
				System.out.println(keyf + "剩余失效" + redisTemplate.getExpire(key.toString()));
				final long liveTimef = liveTime;
				if (StringUtil.isNull(keyf)) {
					return null;
				}

				Object object;
				object = redisTemplate.execute(new RedisCallback<Object>() {
					public Object doInRedis(RedisConnection connection) throws DataAccessException {
						byte[] keyb = keyf.getBytes();
						byte[] value = connection.get(keyb);

						if (liveTimef > 0) {
							connection.expire(keyb, liveTimef);
						}
						if (value == null) {
							return null;
						}
						String json = StringUtil.toString(value);
						// 将json----》object
						List<T> objArray = null;
						try {
							objArray = JSON.parseArray(json, clazz);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return objArray;
					}

				});

				return (List<T>) object;
			}
		
		/**
		 * 
		 * @Description 删除缓存
		 * @param redisCacheEmun
		 * @param key
		 * @author sen
		 * @Date 2016年11月17日 下午1:42:29
		 */
		public static void delete(RedisCacheEmun redisCacheEmun, Object key) {
			delete(redisCacheEmun.getRedisTemplate(), key);
		}
		
		public static void delete(RedisTemplate<String, Object> redisTemplate, Object key) {
			final String keyf = StringUtil.toString(key);
			if (StringUtil.isNull(keyf)) {
				return;
			}
			redisTemplate.execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					try {
						//connection.multi();
						byte[] keyb = keyf.getBytes();
						connection.del(keyb);
						//connection.exec();
					}catch(Exception e) {
						connection.discard();
					}
					return null;
				}
			});
		}

}
