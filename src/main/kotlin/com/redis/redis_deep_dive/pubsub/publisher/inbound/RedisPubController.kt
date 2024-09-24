package com.redis.redis_deep_dive.pubsub.publisher.inbound

import com.redis.redis_deep_dive.pubsub.publisher.outbound.RedisPublisher
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RedisPubController(
    private val redisPublisher: RedisPublisher
) {
    @PostMapping("/publish")
    fun publishMessage() {
        redisPublisher.publish("Hello from Redis!")
    }
}