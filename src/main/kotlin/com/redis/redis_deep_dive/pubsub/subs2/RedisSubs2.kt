package com.redis.redis_deep_dive.pubsub.subs2

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class RedisSubs2 : MessageListener {
    override fun onMessage(message: Message, pattern: ByteArray?) {
        Thread.sleep(2000)
        log.info { "${Thread.currentThread()}Consumer2 : Message received: ${String(message.body)}" }
    }
}