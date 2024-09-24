package com.redis.redis_deep_dive.per

val SET_WITH_PER_LUASCRIPT = """
    local key = KEYS[1]
    local value = ARGV[1]
    local initialTtl = tonumber(ARGV[2])
    local processedTime = tonumber(ARGV[3])
    
    redis.call('HSET', key, 'value', value, 'initialTTL', initialTtl, 'processedTime', processedTime, 'renewCounter', 0)
    redis.call('EXPIRE', key, initialTtl)
    
    return true
""".trimIndent()

val FETCH_WITH_PER_LUASCRIPT = """
    local key = KEYS[1]
    local beta = tonumber(ARGV[1])
    
    local value = redis.call('HGET', key, 'value')
    
    if value == false then
        return nil
    end

    local initialTTL = tonumber(redis.call('HGET', key, 'initialTTL'))
    local ttl = tonumber(redis.call('TTL', key))
    local renewCounter = tonumber(redis.call('HGET', key, 'renewCounter'))
    local processedTime = tonumber(redis.call('HGET', key, 'processedTime'))
    local elapsedTime = -tonumber(processedTime * beta * math.log(math.random()))
    if ttl <= elapsedTime then
        redis.call('EXPIRE', key, initialTTL)
        redis.call('HSET', key, 'renewCounter', renewCounter+1)
    end
    
    return value..":"..renewCounter..":"..ttl..":"..tostring(elapsedTime)
""".trimIndent()
