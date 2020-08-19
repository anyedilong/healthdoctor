package com.java.until.cache;

import org.springframework.data.redis.core.RedisTemplate;
import com.java.config.InstanceFactory;

public enum RedisCacheEmun {
	
	 USER_CACHE(InstanceFactory.getInstance(RedisTemplate.class, "userCache"), 60*60),
	 DICT_CACHE(InstanceFactory.getInstance(RedisTemplate.class, "dictCache"));
	 
	 private RedisTemplate<String, Object> redisTemplate;
	 private long liveTime = 0;// 过期时间
	 
	 private RedisCacheEmun(RedisTemplate<String, Object> redisTemplate) {
			this.redisTemplate = redisTemplate;
		}

		private RedisCacheEmun(RedisTemplate<String, Object> redisTemplate, long liveTime) {
			this.redisTemplate = redisTemplate;
			this.liveTime = liveTime;
		}

		public RedisTemplate<String, Object> getRedisTemplate() {
			return redisTemplate;
		}

		public long getLiveTime() {
			return liveTime;
		}

}
