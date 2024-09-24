//package com.redis.redis_deep_dive.cache
//
//import org.springframework.cache.annotation.CacheEvict
//import org.springframework.cache.annotation.CachePut
//import org.springframework.cache.annotation.Cacheable
//import org.springframework.cache.annotation.Caching
//import org.springframework.stereotype.Service
//import kotlin.jvm.optionals.getOrElse
//import kotlin.jvm.optionals.getOrNull
//
//@Service
//class SampleService(
//    private val sampleDataRepository: SampleDataRepository
//) {
//    @Cacheable("sampleData", key = "#id")
//    fun findById(id: Long): SimpleDataDto {
//        return sampleDataRepository.findById(id).getOrNull().toDto()
//    }
//
//    @Caching(
//        put = [
//            CachePut("sampleData", key = "#result.id")
//        ]
//    )
//    fun save(name: String): SimpleDataDto {
//        return sampleDataRepository.save(
//            SampleData(name = name)
//        ).toDto()
//    }
//
//    @Caching(
//        put = [
//            CachePut("sampleData", key = "#id")
//        ]
//    )
//    fun update(id: Long, name: String): SimpleDataDto {
//        val sampleData = sampleDataRepository.findById(id).getOrElse { throw  IllegalArgumentException("Sample data not found") }
//        sampleData?.name = name
//        return sampleDataRepository.save(sampleData).toDto()
//    }
//
//    @Caching(
//        evict = [
//            CacheEvict("sampleData", key = "#id"),
//        ]
//    )
//    fun delete(id: Long) {
//        val entity = sampleDataRepository.findById(id)
//            .orElseThrow { IllegalArgumentException("Entity not found with id: $id") }
//
//        sampleDataRepository.deleteById(id)
//    }
//}
//
//private fun SampleData?.toDto(): SimpleDataDto {
//    return SimpleDataDto(
//        id = this?.id ?: 0,
//        name = this?.name ?: ""
//    )
//}
