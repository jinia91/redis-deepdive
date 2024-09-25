package com.redis.redis_deep_dive.stream.producer

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/stream")
class ProduceController(
    private val redisStreamProducer: RedisStreamProducer,
    private val redisTemplate: RedisTemplate<String, String>
) {
    @PostMapping("/produce")
    fun produce() {
        redisStreamProducer.unLimitNumberProduce()
    }

    @PostMapping("/subscribe-group")
    fun subscribeGroup() {
        redisTemplate.opsForStream<String, String>().createGroup("stream-key", "group")
    }
}