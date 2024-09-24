package com.redis.redis_deep_dive.pubsub.subs3

import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

@Component
class RedisSubs3 : MessageListener {
    override fun onMessage(message: Message, pattern: ByteArray?) {
        Thread.sleep(3000)
        println("${Thread.currentThread()}Consumer3 : Message received: ${String(message.body)}")
    }
}