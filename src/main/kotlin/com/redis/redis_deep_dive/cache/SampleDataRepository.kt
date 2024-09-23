package com.redis.redis_deep_dive.cache

import org.springframework.data.repository.CrudRepository

interface SampleDataRepository : CrudRepository<SampleData, Long> {
    fun findByName(name: String): SampleData?
}