package com.redis.redis_deep_dive.stream.producer

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/stream")
class ProduceController(
    private val redisStreamProducer: RedisStreamProducer
) {
    @PostMapping("/produce")
    fun produce() {
        redisStreamProducer.unLimitNumberProduce()
    }
}