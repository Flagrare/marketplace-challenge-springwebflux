package com.challenge.mktreactive.entity

import com.challenge.mktreactive.entity.Buyer
import com.challenge.mktreactive.entity.Product
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import java.time.LocalDateTime

data class Sale(
    @Id
    val id: String? = null,
    @Indexed
    var buyer: Buyer,
    @Indexed
    var product: Product,
    @Indexed
    val creationDate: LocalDateTime = LocalDateTime.now()
)
