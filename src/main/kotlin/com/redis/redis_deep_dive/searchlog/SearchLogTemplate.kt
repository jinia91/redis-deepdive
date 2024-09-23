package com.redis.redis_deep_dive.searchlog

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class SearchLogTemplate(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun logSearchKeyword(keyword: String) {
        redisTemplate.opsForZSet().add("searchlog:keywords", keyword, System.currentTimeMillis().toDouble())
    }

    fun getTop5(): Set<String> {
        return redisTemplate.opsForZSet().reverseRange("searchlog:keywords", 0, 4)!!
    }
}