package com.challenge.mktreactive.repository

import com.challenge.mktreactive.entity.Category
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
@Document
interface CategoryRepository : ReactiveMongoRepository<Category, String> {
    @Query("""{name: ?0}""")
    fun findByName(name: String): Mono<Category>
}
