package com.redis.redis_deep_dive.data_structure

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/hash")
class RedisHashController(
    private val redisTemplate: RedisTemplate<String, String>
) {
    data class HashSetRequest(
        val field: String,
        val value: String
    )

    @PostMapping("/hset")
    fun hset(@RequestBody request: HashSetRequest): Boolean {
        return redisTemplate.opsForHash<String, String>().putIfAbsent("hash", request.field, request.value)
    }

    data class HashGetRequest(
        val field: String,
    )

    @PostMapping("/hget")
    fun hget(@RequestBody request: HashGetRequest): String? {
        return redisTemplate.opsForHash<String, String>().get("hash", request.field)
    }
}