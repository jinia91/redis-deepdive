package com.redis.redis_deep_dive.searchlog

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SearchingWorkerConfig {
    @Bean
    fun searchingWorkers(): ScheduledExecutorService {
        return Executors.newScheduledThreadPool(10)
    }
}