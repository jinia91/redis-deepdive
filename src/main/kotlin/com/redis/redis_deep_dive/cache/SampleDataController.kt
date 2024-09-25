package com.redis.redis_deep_dive.cache

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleDataController(
    private val sampleService: SampleService
) {
    @GetMapping("/sample/{id}")
    fun findById(id: Long): SimpleDataDto {
        return sampleService.findById(id)
    }

    @PostMapping("/sample")
    fun save(name: String): SimpleDataDto {
        return sampleService.save(name)
    }

    @PostMapping("/sample/{id}")
    fun update(id: Long, name: String): SimpleDataDto {
        return sampleService.update(id, name)
    }

    @DeleteMapping("/sample/{id}")
    fun delete(id: Long) {
        sampleService.delete(id)
    }
}