package com.redis.redis_deep_dive.per

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.springframework.stereotype.Component

@Component
class PerClient(
    private val redisTemplate: RedisTemplate<String, String>,
) {
    private val setLuaScript = DefaultRedisScript(SET_WITH_PER_LUASCRIPT, Boolean::class.java)
    private val fetchLuaScript = DefaultRedisScript(FETCH_WITH_PER_LUASCRIPT, String::class.java)

    fun set(key: String, value: String, ttl: Long): Boolean {
        return redisTemplate.execute(setLuaScript, listOf(key), value, ttl.toString(), 0.1.toString())
    }

    fun fetch(key: String, beta: Long): String? {
        return redisTemplate.execute(fetchLuaScript, listOf(key), beta.toString())
    }
}