package com.challenge.mktreactive.repository

import com.challenge.mktreactive.entity.Article
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.time.LocalDateTime

interface ArticleRepository : MongoRepository<Article, String> {
    @Query(
        """{
                category: { _id: ?0  },
                creationDate: ?1
    } """
    )
    fun findAllByCategoryFromToday(categoryId: String, today: LocalDateTime): MutableList<Article>
}