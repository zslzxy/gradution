/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.management.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsl.common.entity.Goods;
import com.zsl.common.entity.KillGoods;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author 张世林 (张世林@yiji.com)
 * @version 1.0
 * @filename com.zsl.management.config
 * @since 2019/2/15
 */
@Configuration
public class RedisConfig {

	@Bean("redisTemplate")
	@Primary
	public RedisTemplate<String, Goods> redisTemplate(RedisConnectionFactory factory) {

		RedisTemplate<String, Goods> template = new RedisTemplate<>();

		RedisSerializer<String> redisSerializer = new StringRedisSerializer();

		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Goods.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		template.setConnectionFactory(factory);
		//key序列化方式
		template.setKeySerializer(redisSerializer);
		//value序列化
		template.setValueSerializer(jackson2JsonRedisSerializer);
		//value hashmap序列化
		template.setHashValueSerializer(jackson2JsonRedisSerializer);

		return template;
	}

	@Bean("cacheManager")
	@Primary
	public CacheManager cacheManager(RedisConnectionFactory factory) {
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Goods.class);

		//解决查询缓存转换异常的问题
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		// 配置序列化（解决乱码的问题）
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
				.disableCachingNullValues();

		RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
				.cacheDefaults(config)
				.build();
		return cacheManager;
	}


}
