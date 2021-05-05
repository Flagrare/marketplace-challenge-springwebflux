package com.challenge.mktreactive.repository

import com.challenge.mktreactive.entity.Rating
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import java.time.LocalDateTime

interface RatingRepository : ReactiveMongoRepository<Rating, String> {

    @Query("""{
                creationDate: { ${'$'}gte: ?0 - '${24 * 60 * 60 * 1000 * 365}' }
    } """)
    fun findAllFromLastYear(today: LocalDateTime): Flux<Rating>
}