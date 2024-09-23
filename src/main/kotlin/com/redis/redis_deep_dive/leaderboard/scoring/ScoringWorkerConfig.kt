package com.redis.redis_deep_dive.leaderboard.scoring

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ScoringWorkerConfig {
    @Bean
    fun autoScoringWorkers(): ScheduledExecutorService {
        val executor = Executors.newScheduledThreadPool(10)
        return executor
    }
}