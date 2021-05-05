package com.challenge.mktreactive.repository

import com.challenge.mktreactive.entity.Seller
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface SellerRepository : ReactiveMongoRepository<Seller, String> {
    @Query("{ user: {id: ?0}}")
    fun findByUserId(userId: String): Mono<Seller>
}