package com.redis.redis_deep_dive.searchlog

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/searchlog")
class SearchLogController(
    private val searchSystem: SearchSystem
) {
    @PostMapping("/start")
    fun startSearch() {
        searchSystem.startSearch()
    }

    @GetMapping("/top5")
    fun showTop5() : Set<String> {
        return searchSystem.showTop5()
    }
}