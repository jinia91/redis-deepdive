package com.redis.redis_deep_dive.per

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("/per")
class PerRestController(
    private val perClient: PerClient
) {
    @PostMapping("/set")
    fun set(key: String, value: String, ttl: Long): Boolean {
        return perClient.set(key, value, ttl)
    }

    @GetMapping("/fetch/{key}")
    fun fetch(
        @PathVariable key: String,
        @RequestParam delta: Long): String? {
        return perClient.fetch(key, delta)
    }
}