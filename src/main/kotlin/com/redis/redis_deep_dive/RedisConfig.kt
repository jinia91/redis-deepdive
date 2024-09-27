package com.redis.redis_deep_dive

import io.lettuce.core.resource.ClientResources
import io.micrometer.observation.ObservationRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.connection.lettuce.observability.MicrometerTracingAdapter
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

@Configuration
class ObservabilityConfiguration {
    @Bean
    fun clientResources(observationRegistry: ObservationRegistry): ClientResources? {
        return ClientResources.builder()
            .tracing(MicrometerTracingAdapter(observationRegistry, "my-redis-cache"))
            .build()
    }

    @Bean
    fun lettuceConnectionFactory(clientResources : ClientResources) : LettuceConnectionFactory {
        val clientConfig = LettuceClientConfiguration.builder()
            .clientResources(clientResources).build()
        val redisConfiguration = RedisStandaloneConfiguration(
            "localhost", 8882
        )
        return LettuceConnectionFactory(redisConfiguration, clientConfig)
    }
}