package com.redis.redis_deep_dive.pubsub.subs2

import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

@Component
class RedisSubs2 : MessageListener {
    override fun onMessage(message: Message, pattern: ByteArray?) {
        Thread.sleep(2000)
        println("${Thread.currentThread()} Consumer2 : Message received: ${String(message.body)}")
    }
}