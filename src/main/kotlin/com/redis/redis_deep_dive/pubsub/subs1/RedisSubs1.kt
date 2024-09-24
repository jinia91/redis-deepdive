package com.redis.redis_deep_dive.pubsub.subs1

import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

@Component
class RedisSubs1 : MessageListener {
    override fun onMessage(message: Message, pattern: ByteArray?) {
        Thread.sleep(1000)
        println("${Thread.currentThread()}Consumer1 : Message received: ${String(message.body)}")
    }
}