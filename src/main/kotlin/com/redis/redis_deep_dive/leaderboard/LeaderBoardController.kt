package com.redis.redis_deep_dive.leaderboard

import com.redis.redis_deep_dive.leaderboard.scoring.ScoringSystem
import com.redis.redis_deep_dive.leaderboard.scoring.ScoringTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LeaderBoardController(
    private val scoringSystem: ScoringSystem,
    private val scoringTemplate: ScoringTemplate
) {
    @PostMapping("/scoring-start")
    fun scoringStart() {
        scoringSystem.start()
    }

    @GetMapping("/leaderboard")
    fun getLeaderBoard(): List<Player> {
        return scoringTemplate.getAllPlayers()
    }

    @PostMapping("/scoring-stop")
    fun scoringStop() {
        scoringSystem.stop()
    }

    @GetMapping("/leaderboard/top5")
    fun getTop5(): List<Player> {
        return scoringTemplate.getTop5()
    }

    @GetMapping("/leaderboard/{playerId}")
    fun getScore(playerId: Long): Double? {
        return scoringTemplate.getScore(playerId)
    }
}