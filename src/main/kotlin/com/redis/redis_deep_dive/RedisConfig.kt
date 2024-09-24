package com.redis.redis_deep_dive

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(
    private val connectionFactory: RedisConnectionFactory
) {
    @Bean
    fun redisTemplate(): RedisTemplate<String, String> {
        val redisTemplate = RedisTemplate<String, String>()
        redisTemplate.connectionFactory = connectionFactory
        redisTemplate.setDefaultSerializer(StringRedisSerializer.UTF_8)
        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }
}