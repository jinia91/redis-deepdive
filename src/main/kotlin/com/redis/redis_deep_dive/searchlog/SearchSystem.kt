package com.redis.redis_deep_dive.searchlog

import java.util.concurrent.ScheduledExecutorService
import org.springframework.stereotype.Component

@Component
class SearchSystem(
    private val searchingWorkers: ScheduledExecutorService,
    private val searchLogTemplate: SearchLogTemplate
) {
    enum class SearchKeyword(val keyword: String) {
        REDIS("Redis"),
        SPRING("Spring"),
        KOTLIN("Kotlin"),
        JAVA("Java"),
        PYTHON("Python"),
        RUBY("Ruby"),
        SCALA("Scala"),
        GROOVY("Groovy"),
        CLOJURE("Clojure"),
        HASKELL("Haskell")
    }

    fun startSearch() {
        for (i in 1..10) {
            searchingWorkers.scheduleAtFixedRate({ doSearch(
                SearchKeyword.values().random().keyword
            ) }, 0, 1500, java.util.concurrent.TimeUnit.MILLISECONDS)
        }
    }

    private fun doSearch(query: String) {
        searchLogTemplate.logSearchKeyword(query)
        println("${Thread.currentThread()} : Searching for $query")
    }

    fun showTop5() : Set<String> {
        return searchLogTemplate.getTop5()
    }
}