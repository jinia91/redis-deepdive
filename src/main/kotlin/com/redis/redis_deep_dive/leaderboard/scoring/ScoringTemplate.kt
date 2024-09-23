package com.redis.redis_deep_dive.leaderboard.scoring

import com.redis.redis_deep_dive.leaderboard.Player
import java.time.LocalDate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class ScoringTemplate(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun addScore(player : Player): Double? {
        return redisTemplate.opsForZSet().incrementScore("leaderboard:${LocalDate.now()}", player.id.toString(), player.score)
    }

    fun getScore(playerId : Long) : Double? {
        return redisTemplate.opsForZSet().score("leaderboard:${LocalDate.now()}", playerId.toString())
    }

    fun getAllPlayers(): List<Player> {
        return redisTemplate
            .opsForZSet()
            .reverseRangeWithScores("leaderboard:${LocalDate.now()}", 0, -1)!!
            .mapNotNull { Player(it.value!!.toLong(), it.score!!) }
    }

    fun getTop5(): List<Player> {
        return redisTemplate
            .opsForZSet()
            .reverseRangeWithScores("leaderboard:${LocalDate.now()}", 0, 4)!!
            .mapNotNull { Player(it.value!!.toLong(), it.score!!) }
    }
}