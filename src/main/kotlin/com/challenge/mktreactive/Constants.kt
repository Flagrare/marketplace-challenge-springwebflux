package com.challenge.mktreactive

import org.springframework.data.domain.Sort

object Endpoints {
    const val NEWS_API_URL_BASE = "https://newsapi.org/v2/top-headlines?country=br&apiKey=1ada37e377444047aad3a336e68b4bbe"
    const val NEWS_API_URL_WITH_CATEGORY = "https://newsapi.org/v2/top-headlines?country=br&apiKey=1ada37e377444047aad3a336e68b4bbe&category="
}

object Categories {
    const val business = "business"
    const val entertainment = "entertainment"
    const val general = "general"
    const val health = "health"
    const val science = "science"
    const val sports = "sports"
    const val technology = "technology"
}