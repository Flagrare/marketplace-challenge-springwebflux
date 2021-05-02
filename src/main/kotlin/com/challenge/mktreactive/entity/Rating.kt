package com.challenge.mktreactive.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import java.time.LocalDateTime

data class Rating(
    @Id
    val id: String? = null,
    val rate: Int,
    val sale: Sale,
    @Indexed
    val ratingDate: LocalDateTime = LocalDateTime.now(),
)
