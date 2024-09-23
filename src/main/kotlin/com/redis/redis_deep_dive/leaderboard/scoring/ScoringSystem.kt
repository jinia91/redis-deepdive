package com.redis.redis_deep_dive.leaderboard.scoring

import com.redis.redis_deep_dive.leaderboard.Player
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import org.springframework.stereotype.Component

@Component
class ScoringSystem(
    private val autoScoringWorkers: ScheduledExecutorService,
    private val scoringTemplate: ScoringTemplate
) {
    fun start() {
        for (i in 1..10) {
            autoScoringWorkers.scheduleAtFixedRate({ doScoring() }, 0, 1500, TimeUnit.MILLISECONDS)
        }
    }

    private fun doScoring() {
        val randomPlayerId = (1..100).random().toLong()
        val newScore = scoringTemplate.addScore(Player(randomPlayerId, (1..10).random().toDouble()))
        println("${Thread.currentThread()} : Player $randomPlayerId scored $newScore")
    }

    fun stop() {
        autoScoringWorkers.shutdown()
    }
}