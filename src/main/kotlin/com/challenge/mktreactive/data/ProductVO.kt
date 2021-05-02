package com.challenge.mktreactive.data

import com.challenge.mktreactive.entity.Category
import com.challenge.mktreactive.entity.Product

class ProductVO(
    val id: String,
    val name: String,
    val desc: String,
    val category: Category? = null,
) {
    companion object {
        fun fromEntity(product: Product): ProductVO =
            ProductVO(
                id = product.id!!,
                name = product.name,
                desc = product.desc,
                category = product.category
            )
    }
}

