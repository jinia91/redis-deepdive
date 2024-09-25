//package com.redis.redis_deep_dive.stream
//
//import java.time.Duration
//import java.util.concurrent.Executors
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.data.redis.connection.RedisConnectionFactory
//import org.springframework.data.redis.connection.stream.Consumer
//import org.springframework.data.redis.connection.stream.ObjectRecord
//import org.springframework.data.redis.connection.stream.ReadOffset
//import org.springframework.data.redis.connection.stream.StreamOffset
//import org.springframework.data.redis.stream.StreamListener
//import org.springframework.data.redis.stream.StreamMessageListenerContainer
//
//@Configuration
//class RedisStreamContainerConfiguration(
//    private val redisConnectionFactory: RedisConnectionFactory,
//    private val streamListeners: List<StreamListener<String, ObjectRecord<String, StreamMessage>>>
//) {
//    @Bean
//    fun streamListenerContainer(): StreamMessageListenerContainer<String, ObjectRecord<String, StreamMessage>>? {
//        val containerOption = StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
//            .pollTimeout(Duration.ofMillis(100))
////            .executor(Executors.newFixedThreadPool(3))
//            .targetType(StreamMessage::class.java)
//            .build()
//        val container = StreamMessageListenerContainer.create(redisConnectionFactory, containerOption)
//
//        streamListeners.forEachIndexed() { index, it ->
//            println("Adding listener: $it")
//            container.receive(
//                Consumer.from("group", "consumer-$index"),
//                StreamOffset.create("stream-key",  ReadOffset.lastConsumed()),
//                it
//            )
//        }
//        container.start()
//        return container
//    }
//}
