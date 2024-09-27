package com.redis.redis_deep_dive.pubsub.subs3

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class RedisSubs3 : MessageListener {
    override fun onMessage(message: Message, pattern: ByteArray?) {
        Thread.sleep(3000)
        log.info { "${Thread.currentThread()}Consumer3 : Message received: ${String(message.body)}" }
    }
}