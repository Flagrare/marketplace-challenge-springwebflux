package com.example.marketplace.service

import com.challenge.mktreactive.Endpoints
import com.challenge.mktreactive.data.NewsVO
import com.challenge.mktreactive.entity.Article
import com.challenge.mktreactive.repository.ArticleRepository
import com.challenge.mktreactive.repository.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class NewsService() {

    @Autowired
    protected lateinit var articleRepository: ArticleRepository

    @Autowired
    protected lateinit var categoryRepository: CategoryRepository

    @Scheduled(cron = "0 0 */4 * * *")
    fun consumeNews() {

//        val categories = Categories.javaClass.fields
        categoryRepository.findAll()
            .flatMap { category ->
                var newsResponseObject: NewsVO? = RestTemplate().getForObject(
                    Endpoints.NEWS_API_URL_WITH_CATEGORY + category.name,
                    NewsVO::class.java
                )

                var articlesList =
                    newsResponseObject?.articles?.toMutableList()


                articlesList?.forEach { article ->
                    article.category = category
                }

                articleRepository.saveAll(articlesList!!.toList())

            }
    }
}