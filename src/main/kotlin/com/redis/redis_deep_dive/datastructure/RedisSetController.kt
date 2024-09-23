package com.redis.redis_deep_dive.datastructure

import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("set")
class RedisSetController(
    private val redisTemplate: RedisTemplate<String, String>,
) {
    data class SAddRequest(
        val key: String,
        val value: String,
    )

    @PostMapping("/sadd")
    fun sadd(@RequestBody request: SAddRequest): Long {
        return redisTemplate.opsForSet().add(request.key, request.value)!! // set에 value를 추가하고 추가된 요소의 수를 반환, 사이즈가 아님
    }

    @DeleteMapping("/srem")
    fun srem(@RequestBody request: SAddRequest): Long {
        return redisTemplate.opsForSet().remove(request.key, request.value)!! // set에서 value를 제거하고 제거된 요소의 수를 반환, 사이즈가 아님
    }

    @GetMapping("/smembers/{key}")
    fun smembers(
        @PathVariable key: String
    ): Set<String> {
        return redisTemplate.opsForSet().members(key) ?: emptySet()
    }

    @GetMapping("/sismember/{key}")
    fun sismember(
        @PathVariable key: String,
        @RequestParam value: String

    ): Boolean {
        return redisTemplate.opsForSet().isMember(key, value)!!
    }

    data class KeysRequest(
        val key1 : String,
        val key2 : String
    )

    @Operation(summary = "set의 교집합을 반환합니다.")
    @PostMapping("/sinter")
    fun sinter(@RequestBody request: KeysRequest): Set<String> {
        return redisTemplate.opsForSet().intersect(request.key1, request.key2) ?: emptySet()
    }

    @Operation(summary = "set의 합집합을 반환합니다.")
    @PostMapping("/sunion")
    fun sunion(@RequestBody request: KeysRequest): Set<String> {
        return redisTemplate.opsForSet().union(request.key1, request.key2) ?: emptySet()
    }

    @Operation(summary = "set의 차집합을 반환합니다.")
    @PostMapping("/sdiff")
    fun sdiff(@RequestBody request: KeysRequest): Set<String> {
        return redisTemplate.opsForSet().difference(request.key1, request.key2) ?: emptySet()
    }
}