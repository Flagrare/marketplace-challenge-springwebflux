package com.challenge.mktreactive.repository

import com.challenge.mktreactive.entity.Product
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductRepository : MongoRepository<Product, String> {
    override fun findAll(): MutableList<Product> {
        return findAll(
            Sort.by(Sort.Direction.ASC, "score").and(Sort.by(Sort.Direction.ASC, "name"))
                .and(Sort.by(Sort.Direction.ASC, "category"))
        )
    }
}