package com.redis.redis_deep_dive.stream.cons1

import com.redis.redis_deep_dive.stream.StreamMessage
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.stream.StreamListener
import org.springframework.stereotype.Component

@Component
class RedisConsumer1(
    private val redisTemplate: RedisTemplate<String, String>,
) : StreamListener<String, ObjectRecord<String,StreamMessage>> {
    override fun onMessage(message: ObjectRecord<String, StreamMessage>?) {
        println("${Thread.currentThread()} Consumer1 : Message received: ${message?.value}")
        redisTemplate.opsForStream<String, String>().acknowledge("group", message!!)
    }
}