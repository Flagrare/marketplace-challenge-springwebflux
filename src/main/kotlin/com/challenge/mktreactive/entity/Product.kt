package com.challenge.mktreactive.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("products")
data class Product(
    @Id
    val id: String? = null,
    @Indexed
    var name: String,
    var desc: String,
    var score: Long? = 0,
    @Indexed
    var seller: Seller,
    @Indexed
    val category: Category? = null,
    @Indexed
    var updateDate: LocalDateTime = LocalDateTime.now(),
    @Indexed
    val creationDate: LocalDateTime = LocalDateTime.now(),
)