package com.redis.redis_deep_dive.pubsub

import jakarta.annotation.PostConstruct
import java.util.concurrent.Executors
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer


@Configuration
class RedisListenConfig(
    private val redisConnectionFactory: RedisConnectionFactory
){
    @Bean
    fun listenerContainer(): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        val taskExecutor = Executors.newFixedThreadPool(3)
        container.setTaskExecutor(taskExecutor)
        container.setConnectionFactory(redisConnectionFactory)
        return container
    }
}

@Configuration
class RedisPubSubConfig(
    private val redisListeners: List<MessageListener>,
    private val listenerContainer: RedisMessageListenerContainer
) {
    @PostConstruct
    fun init() {
        val topic = ChannelTopic("Notification")

        redisListeners.forEach {
            listenerContainer.addMessageListener(it, topic)
            println("Listener added: ${it.javaClass.simpleName}")
        }
    }
}