package com.challenge.mktreactive.entity

import org.springframework.data.annotation.Id

data class Category(
    @Id
    val id: String? = null,
    var name: String,
    var desc: String? = null
)
