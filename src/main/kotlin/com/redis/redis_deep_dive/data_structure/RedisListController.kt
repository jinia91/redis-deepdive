package com.redis.redis_deep_dive.data_structure

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/list")
class RedisListController(
    private val redisTemplate: RedisTemplate<String, String>
) {
    @PostMapping("/lpush")
    fun lpush(@RequestBody value: String): Long {
        return redisTemplate.opsForList().leftPush("list", value)!!  // list의 왼쪽에 value를 넣고 길이를 반환, 인덱스 반환이 아니다;
    }

    @PostMapping("/index")
    fun index(@RequestBody index: Long): String {
        return redisTemplate.opsForList().index("list", index) ?: "fail"
    }

    data class RangeRequest(
        val start: Long,
        val end: Long
    )

    @PostMapping("/range")
    fun range(@RequestBody request: RangeRequest): List<String> {
        return redisTemplate.opsForList().range("list", request.start, request.end) ?: emptyList()
    }

    @PostMapping("/lpop")
    fun lpop(): String {
        return redisTemplate.opsForList().leftPop("list") ?: "fail"
    }

    @PostMapping("/rpop")
    fun rpop(): String {
        return redisTemplate.opsForList().rightPop("list") ?: "fail"
    }

    @PostMapping("/rpush")
    fun rpush(@RequestBody value: String): Long {
        return redisTemplate.opsForList().rightPush("list", value)!!  // list의 오른쪽에 value를 넣고 길이를 반환, 인덱스 반환이 아니다;
    }

    @PostMapping("/size")
    fun size(): Long {
        return redisTemplate.opsForList().size("list")!!
    }

    @PostMapping("/trim")
    fun trim(@RequestBody request: RangeRequest): Boolean {
        redisTemplate.opsForList().trim("list", request.start, request.end)
        return true
    }
}