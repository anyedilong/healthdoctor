package com.java.until.redis;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.java.until.StringUtil;

public class JsonRedisSerializer implements RedisSerializer<Object> {

	private final Charset charset;

	public JsonRedisSerializer() {
		this(Charset.forName("UTF8"));
	}

	public JsonRedisSerializer(Charset charset) {
		Assert.notNull(charset);
		this.charset = charset;
	}

	@Override
	public String deserialize(byte[] bytes) {
		return bytes == null ? null : new String(bytes, this.charset);
	}

	@Override
	public byte[] serialize(Object Object) throws SerializationException {
		if (Object == null) {
			return new byte[0];
		}

		// 将数据转为json
		String json = JSON.toJSONString(Object);

		return StringUtil.getBytes(json);
	}

}
