package com.challenge.mktreactive.dto

import com.challenge.mktreactive.entity.Category

data class ProductDTO(
    var name: String,
    var desc: String,
    var category: Category,
    var userId: String
)
