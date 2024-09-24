package com.redis.redis_deep_dive.pubsub.publisher.outbound

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Component

@Component
class RedisPublisher(
    private val redisTemplate: RedisTemplate<String, String>
){
    fun publish(message: String) {
        redisTemplate.convertAndSend(ChannelTopic("Notification").topic, message)
    }
}