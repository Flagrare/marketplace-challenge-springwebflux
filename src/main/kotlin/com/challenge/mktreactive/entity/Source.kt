package com.challenge.mktreactive.entity

import org.springframework.data.annotation.Id

data class Source(
    @Id
    var id: String? = null,
    var name: String
)
