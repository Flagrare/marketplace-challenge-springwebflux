package com.challenge.mktreactive.service

import com.challenge.mktreactive.dto.ProductDTO
import com.challenge.mktreactive.entity.Product
import com.challenge.mktreactive.exception.NotFoundException
import com.challenge.mktreactive.repository.ArticleRepository
import com.challenge.mktreactive.repository.ProductRepository
import com.challenge.mktreactive.repository.RatingRepository
import com.challenge.mktreactive.repository.SaleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class ProductService() {

    @Autowired
    protected lateinit var productRepository: ProductRepository

    @Autowired
    protected lateinit var saleRepository: SaleRepository

    @Autowired
    protected lateinit var ratingRepository: RatingRepository

    @Autowired
    protected lateinit var articleRepository: ArticleRepository

    @Autowired
    protected lateinit var userService: UserService

    @Autowired
    protected lateinit var sellerService: SellerService

    fun findAll(): Flux<Product> =
        productRepository.findAll()

    fun findById(id: String): Mono<Product> =
        productRepository.findById(id)
            .switchIfEmpty(Mono.error(NotFoundException("Product with id $id not found")))

    fun createProduct(productDTO: ProductDTO): Mono<Product> =
        sellerService.findById(productDTO.userId)
            .flatMap {
                userService.findById(productDTO.userId)
                    .flatMap { user ->
                        sellerService.createSeller(user)
                            .flatMap { seller ->
                                productRepository.save(
                                    Product(
                                        name = productDTO.name,
                                        desc = productDTO.desc,
                                        category = productDTO.category,
                                        seller = seller
                                    )
                                )
                            }
                    }
            }


    fun updateProduct(id: String, productDTO: ProductDTO): Mono<Product> = findById(id)
        .flatMap { product ->
            product.name = productDTO.name
            product.desc = productDTO.desc
            product.updateDate = LocalDateTime.now()

            productRepository.save(product)
        }


    fun deleteById(id: String) =
        findById(id)
            .flatMap { product ->
                productRepository.delete(product)
            }


    @Scheduled(cron = "@daily")
    fun rateProduct() {
        var products = productRepository.findAll()
            .map { product ->

                //Relevance = sales / total days of product existence
                val salesCount = saleRepository.findByProductId(
                    product.id!!, Sort.by(Sort.Direction.ASC, "id")
                ).count().block()
                val daysOfCreation = ChronoUnit.DAYS.between(LocalDateTime.now(), product.creationDate)
                val relevance = salesCount?.div(daysOfCreation)

                //Average rating
                var ratings = ratingRepository.findAllFromLastYear(LocalDateTime.now())
                    .collectList()
                    .block()

                val avgRatings = ratings?.sumOf { it.rate }?.div(ratings?.size)


                //Category Importance
                val today = LocalDateTime.now().toLocalDate().atStartOfDay()
                var categoryArticlesCount = 0
                if (product.category?.id != null) {
                    val ctgImportance =
                        articleRepository.findAllByCategoryFromToday(product.category.id, today).collectList().block()
                    categoryArticlesCount = ctgImportance!!.size
                }

                //Score
                product.score = avgRatings!!.toLong() + relevance!! + categoryArticlesCount
                return@map product
            }

        productRepository.saveAll(products)
    }

}
