package com.redis.redis_deep_dive.stream.consumer

import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Range
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ConsumerController(
    private val redisTemplate: RedisTemplate<String, String>
) {
    @PostMapping("/stream/fail-over")
    @Operation(summary = "현재 버그가 있음")
    fun failOver() {
        val messages = redisTemplate.opsForStream<String,String>()
            .pending("stream-key", "group", Range.leftOpen(1727173781532, 9727173781532), 1000)
        messages.forEach {
            println("Message id: ${it.id}")
            redisTemplate.opsForStream<String, String>()
                .claim("stream-key", "group", "consumer1", java.time.Duration.ofMillis(1000), it.id)
        }
    }
}