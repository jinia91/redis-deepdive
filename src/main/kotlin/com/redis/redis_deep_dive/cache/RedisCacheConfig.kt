package com.redis.redis_deep_dive.cache

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.lettuce.core.RedisClient
import io.lettuce.core.TrackingArgs
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.support.caching.CacheAccessor
import io.lettuce.core.support.caching.ClientSideCaching
import java.time.Duration
import java.util.concurrent.Callable
import java.util.concurrent.ConcurrentHashMap
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisCommands
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
@EnableCaching
class RedisCacheConfig(
    private val redisConnectionFactory: RedisConnectionFactory,
) {
//    @Bean
//    fun redisCacheManager(): RedisCacheManager {
//        return RedisCacheManager.builder(redisConnectionFactory)
//            .cacheDefaults(
//                RedisCacheConfiguration.defaultCacheConfig()
//                    .entryTtl(Duration.ofSeconds(1000))
//                    .disableCachingNullValues()
//                    .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
//                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(Jackson2JsonRedisSerializer(SimpleDataDto::class.java)))
//            )
//            .build()
//    }

    @Bean
    fun redisClient(): RedisClient {
        return RedisClient.create("redis://localhost:8882")
    }

    @Bean
    fun cacheManager(redisClient: RedisClient) : CacheManager {
        val clientCache: Map<String, String> = ConcurrentHashMap()
        val otherParty: StatefulRedisConnection<String, String> = redisClient.connect()
        val commands = otherParty.sync()
        val connection: StatefulRedisConnection<String, String> = redisClient.connect()
        val frontend = ClientSideCaching.enable(
            CacheAccessor.forMap(clientCache), connection,
            TrackingArgs.Builder.enabled()
        )

        return object : CacheManager {
            private val cacheMap = frontend
            private val objectMapper = ObjectMapper()
            override fun getCache(name: String): Cache? {
                return object : Cache {
                    override fun getName(): String {
                        return name
                    }

                    override fun getNativeCache(): Any {
                        return cacheMap
                    }

                    override fun get(key: Any): Cache.ValueWrapper? {
                        val value = cacheMap.get(key.toString())
                        return if (value != null) {
                            Cache.ValueWrapper {
                            objectMapper.readValue(value, SimpleDataDto::class.java)
                            }
                        } else {
                            null
                        }
                    }

                    override fun <T : Any?> get(key: Any, type: Class<T>?): T? {
                        return objectMapper.readValue(cacheMap.get(key.toString()), type)
                    }

                    override fun <T : Any?> get(key: Any, valueLoader: Callable<T>): T? {
                        return cacheMap.get(key.toString()) as T
                    }

                    override fun put(key: Any, value: Any?) {
                        commands.set(key.toString(), objectMapper.writeValueAsString(value))
                    }

                    override fun evict(key: Any) {
                        commands.pexpire(key.toString(), 0)
                    }

                    override fun clear() {
                    }

                }
            }

            override fun getCacheNames(): MutableCollection<String> {
                return mutableListOf("clientCache")
            }
        }}
}