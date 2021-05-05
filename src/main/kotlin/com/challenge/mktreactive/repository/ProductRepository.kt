package com.challenge.mktreactive.repository

import com.challenge.mktreactive.entity.Product
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface ProductRepository : ReactiveMongoRepository<Product, String> {
    override fun findAll(): Flux<Product> {
        return findAll(
            Sort.by(Sort.Direction.ASC, "score").and(Sort.by(Sort.Direction.ASC, "name"))
                .and(Sort.by(Sort.Direction.ASC, "category"))
        )
    }
}