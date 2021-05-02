package com.challenge.mktreactive.entity

import org.springframework.data.annotation.Id

data class User(
    @Id
    val id: String? = null,
    var name: String
)
