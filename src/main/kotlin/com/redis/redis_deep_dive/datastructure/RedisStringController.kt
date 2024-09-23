package com.redis.redis_deep_dive.datastructure

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RedisStringController(
    private val redisTemplate: RedisTemplate<String, String>
) {
    @GetMapping("/hello")
    fun hello(): String {
        return redisTemplate.opsForValue().get("hello") ?: "fail"
    }

    @GetMapping("/hello-nx")
    fun helloNx(): Boolean {
        return redisTemplate.opsForValue().setIfAbsent("hello", "world") ?: false // key가 set 됬는지여부가 반환됨
    }

    @GetMapping("/hello-xx")
    fun helloXx(): Boolean {
        return redisTemplate.opsForValue().setIfPresent("hello", "world") ?: false // key가 set 됬는지여부가 반환됨
    }

    @GetMapping("/counter-incr")
    fun counterIncr(): Long {
        return redisTemplate.opsForValue().increment("counter", 1)!!
    }

    @GetMapping("/mset")
    fun mset(): Boolean {
        val map = mapOf("key1" to "value1", "key2" to "value2")
        redisTemplate.opsForValue().multiSet(map)
        return true
    }
}