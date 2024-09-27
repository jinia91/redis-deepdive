package com.redis.redis_deep_dive.transaction

import org.springframework.dao.DataAccessException
import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.SessionCallback
import org.springframework.stereotype.Component


@Component
class TransactionRedis(
    private val redisTemplate: RedisTemplate<String, String>,
) {
    fun transaction() {
        redisTemplate.execute(object : SessionCallback<String> {
            override fun <K : Any?, V : Any?> execute(operations: RedisOperations<K, V>): String? {
//                operations.watch("key1" as (K & Any)) 낙관락
                operations.multi()
                operations.opsForValue().set("key1" as (K & Any), "먼저 실행" as (V & Any))
                Thread.sleep(20000)
                operations.opsForValue().set("key2" as (K & Any), "최종커밋" as (V & Any))
                operations.exec()
                return "OK"
            }
        })
    }


    fun transaction2() {
        redisTemplate.execute(object : SessionCallback<String> {
            override fun <K : Any?, V : Any?> execute(operations: RedisOperations<K, V>): String? {
                operations.multi()
                operations.opsForValue().set("key1" as (K & Any), "나중실행" as (V & Any))
                operations.opsForValue().set("key2" as (K & Any), "먼저실행" as (V & Any))
                operations.exec()
                return "OK"
            }
        })
    }
}
