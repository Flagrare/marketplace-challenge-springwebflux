package com.example.marketplace.service

import com.example.marketplace.Endpoints
import com.challenge.mktreactive.data.NewsVO
import com.challenge.mktreactive.repository.ArticleRepository
import com.challenge.mktreactive.repository.CategoryRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class NewsService(val articleRepository: ArticleRepository, val categoryRepository: CategoryRepository) {

    @Scheduled(cron = "0 0 */4 * * *")
    fun consumeNews() {

//        val categories = Categories.javaClass.fields
        val categories = categoryRepository.findAll()

        categories.forEach { category ->

            var newsResponseObject: NewsVO? = RestTemplate().getForObject(
                Endpoints.NEWS_API_URL_WITH_CATEGORY + category.name,
                NewsVO::class.java
            )

            var articlesList =
                newsResponseObject?.articles?.toMutableList()

            if (articlesList != null) {

                articlesList.forEach { article ->
                    article.category = category
                }

                articleRepository.saveAll(articlesList)
            }
        }
    }
}