package com.challenge.mktreactive.repository

import com.challenge.mktreactive.entity.Buyer
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface BuyerRepository : ReactiveMongoRepository<Buyer, String> {
    @Query("{ user: {id: ?0}}")
    fun findByUserId(userId: String): Mono<Buyer>
}