package com.redis.redis_deep_dive.datastructure

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/sorted-set")
class RedisSortedSetController(
    private val redisTemplate: RedisTemplate<String, String>,
    private val key : String = "sorted-set"
) {
    enum class ZAddOption {
        NX, LT, GT
    }

    data class ZAddRequest(
        val value: String,
        val score: Double,
        val option : ZAddOption
    )

    @PostMapping("/zadd")
    fun zadd(request: ZAddRequest): Boolean {
        return when(request.option) {
            ZAddOption.NX -> redisTemplate.opsForZSet().addIfAbsent(key, request.value, request.score)!!
            ZAddOption.LT -> redisTemplate.opsForZSet().add(key, request.value, request.score)!!
            ZAddOption.GT -> redisTemplate.opsForZSet().add(key, request.value, request.score)!!
        }
    }




}