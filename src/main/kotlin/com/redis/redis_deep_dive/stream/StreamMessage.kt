package com.redis.redis_deep_dive.stream

data class StreamMessage(
    val id: String,
    val message: String
)