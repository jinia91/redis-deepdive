package com.redis.redis_deep_dive.transaction

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/transaction")
class TransactionController(
    private val transactionRedis: TransactionRedis
) {
    @PostMapping("/transaction")
    fun transaction() {
        transactionRedis.transaction()
    }

    @PostMapping("/transaction2")
    fun transaction2() {
        transactionRedis.transaction2()
    }
}