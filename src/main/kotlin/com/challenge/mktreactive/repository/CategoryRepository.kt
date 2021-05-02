package com.challenge.mktreactive.repository

import com.challenge.mktreactive.entity.Category
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
@Document
interface CategoryRepository : MongoRepository<Category, String> {
    @Query("""{name: ?0}""")
    fun findByName(name: String): Category
}
