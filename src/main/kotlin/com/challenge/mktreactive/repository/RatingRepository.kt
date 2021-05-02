package com.challenge.mktreactive.repository

import com.challenge.mktreactive.entity.Rating
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.time.LocalDateTime

interface RatingRepository : MongoRepository<Rating, String> {

    @Query("""{
                creationDate: { ${'$'}gte: ?0 - '${24 * 60 * 60 * 1000 * 365}' }
    } """)
    fun findAllFromLastYear(today: LocalDateTime): MutableList<Rating>
}