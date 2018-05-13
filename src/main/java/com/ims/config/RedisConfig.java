package com.ims.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * 类名:com.ims.config.RedisConfig
 * 描述:redis缓存配置类
 * 编写者:陈骑元
 * 创建时间:2018年4月5日 上午9:25:04
 * 修改说明:
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
	
	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.password}")
	private String password;
	
	@Value("${spring.redis.timeout}")
	private int timeout;

	@Value("${spring.redis.pool.max-active}")
	private int maxActive;
	
	@Value("${spring.redis.pool.max-wait}")
	private long maxWaitMillis;
	
	@Value("${spring.redis.pool.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.pool.min-idle}")
	private int minIdle;

	

	@Value("${redis.cache.expiration:3600}")
	private Long expiration;
	
	
	@Bean
	public JedisPool redisPoolFactory() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
	    jedisPoolConfig.setMaxIdle(maxIdle);
	    jedisPoolConfig.setMinIdle(minIdle);
	    jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
	    jedisPoolConfig.setMaxTotal(maxActive);
	    
		JedisPool jedisPool =null;
		if(StringUtils.isNotBlank(password)){
			jedisPool=new JedisPool(jedisPoolConfig, host, port, timeout, password);
		}else{
			jedisPool = new JedisPool(jedisPoolConfig, host, port);
		}

		return jedisPool;
	}

	@Bean
	public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
		RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
		rcm.setDefaultExpiration(expiration);
		return rcm;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new JdkSerializationRedisSerializer());
		return template;
	}

}
