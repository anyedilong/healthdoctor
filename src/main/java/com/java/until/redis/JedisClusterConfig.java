package com.java.until.redis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import redis.clients.jedis.JedisPoolConfig;



@Configuration
public class JedisClusterConfig {
	
	
	    @NacosValue(value="${spring.redis.cache.expireSeconds}",autoRefreshed=true)
	    private int expireSeconds;
	    @NacosValue(value="${spring.redis.cache.clusterNodes}",autoRefreshed=true)
	    private String clusterNodes;
	    @NacosValue(value="${spring.redis.cache.commandTimeout}",autoRefreshed=true)
	    private int commandTimeout;
	    @NacosValue(value="${spring.redis.pool.max-active}",autoRefreshed=true)
	    private long maxActive;
	    @NacosValue(value="${spring.redis.pool.max-idle}",autoRefreshed=true)
	    private int maxIdle;
	    @NacosValue(value="${spring.redis.pool.max-wait}",autoRefreshed=true)
	    private int maxWait;
	    @NacosValue(value="${spring.redis.pool.min-idle}",autoRefreshed=true)
	    private int minIdle;
	     @NacosValue(value="${spring.redis.pool.test-on-borrow}",autoRefreshed=true)
	    private boolean  testOnBorrow;
	    
	    /**
	     * 配置 Redis 连接池信息
	     */
	    @Bean(name="poolConfig")
	    public JedisPoolConfig getJedisPoolConfig() {
	        JedisPoolConfig jedisPoolConfig =new JedisPoolConfig();
	        jedisPoolConfig.setMaxIdle(maxIdle);
	        jedisPoolConfig.setMaxWaitMillis(maxWait);
	        jedisPoolConfig.setTestOnBorrow(testOnBorrow);

	        return jedisPoolConfig;
	    }

	   /**
     * 注意：
     * redisCluster，并且可以直接注入到其他类中去使用
     * @return
     */
    @Bean(name="redisClusterConfiguration")
    public RedisClusterConfiguration  getJedisCluster() {

    	 RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
     //    redisClusterConfiguration.setMaxRedirects();

         List<RedisNode> nodeList = new ArrayList<>();

         String[] cNodes =clusterNodes.split(",");
         //分割出集群节点
         for(String node : cNodes) {
             String[] hp = node.split(":");
             nodeList.add(new RedisNode(hp[0], Integer.parseInt(hp[1])));
         }
         redisClusterConfiguration.setClusterNodes(nodeList);

         return redisClusterConfiguration;
    }
    
    @Bean(name="jedisConnectionFactory")
    @DependsOn({"redisClusterConfiguration","poolConfig"})
    public JedisConnectionFactory getJedisConnectionFactory(RedisClusterConfiguration redisClusterConfiguration, JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration,jedisPoolConfig);
        return jedisConnectionFactory;
    }
    
    /**
     * 用户缓存
     * @param redisConnectionFactory
     * @return
     */
    @Bean(name="userCache")
    @DependsOn({"jedisConnectionFactory"})
    public RedisTemplate<String, Object> userCacheTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setValueSerializer(new JsonRedisSerializer());
        redisTemplate.setKeySerializer(new JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
    /**
     * 字典缓存
     * @param redisConnectionFactory
     * @return
     */
    @Bean(name="dictCache")
    @DependsOn({"jedisConnectionFactory"})
    public RedisTemplate<String,Object> dictCacheTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setValueSerializer(new JsonRedisSerializer());
        redisTemplate.setKeySerializer(new JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
