package com.redis.redis_deep_dive.stream.producer

import com.redis.redis_deep_dive.stream.StreamMessage
import org.springframework.data.redis.connection.stream.StreamRecords
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisStreamProducer(
    private val redisTemplate: RedisTemplate<String, String>,
) {
    fun unLimitNumberProduce() {
        generateSequence(0) { it + 1 }
            .map {
                StreamRecords.newRecord()
                    .`in`("stream-key")
                    .ofObject(StreamMessage(it.toString(), "Message $it"))
            }
            .forEach {
                Thread.sleep(50)
                redisTemplate.opsForStream<String, String>().add(it)
            }
    }
}