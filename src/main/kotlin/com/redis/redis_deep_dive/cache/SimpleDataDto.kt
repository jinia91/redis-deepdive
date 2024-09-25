package com.redis.redis_deep_dive.cache

import jakarta.persistence.Cacheable

data class SimpleDataDto(
    val id: Long = 0,
    val name: String = ""
)