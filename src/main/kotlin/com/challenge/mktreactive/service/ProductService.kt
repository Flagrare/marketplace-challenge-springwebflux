package com.example.marketplace.service

import com.challenge.mktreactive.dto.ProductDTO
import com.challenge.mktreactive.entity.Product
import com.challenge.mktreactive.entity.Seller
import com.challenge.mktreactive.entity.User
import com.challenge.mktreactive.exception.NotFoundException
import com.challenge.mktreactive.repository.ArticleRepository
import com.challenge.mktreactive.repository.ProductRepository
import com.challenge.mktreactive.repository.RatingRepository
import com.challenge.mktreactive.repository.SaleRepository
import org.springframework.data.domain.Sort
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class ProductService(
    val productRepository: ProductRepository,
    val saleRepository: SaleRepository,
    val ratingRepository: RatingRepository,
    val articleRepository: ArticleRepository,
    val userService: UserService,
    val sellerService: SellerService
) {

    fun findAll(): List<Product> =
        productRepository.findAll()

    fun findById(id: String): Product =
        productRepository.findById(id)
            .orElseThrow { NotFoundException("Product with id $id not found") }

    fun createProduct(productDTO: ProductDTO): Product {

        var user: User = sellerService.findById(productDTO.userId).user
        if (user.id == null) {
            user = userService.findById(productDTO.userId)
            sellerService.createSeller(user)
        }

        return productRepository.save(
            Product(
                name = productDTO.name,
                desc = productDTO.desc,
                category = productDTO.category,
                seller = Seller(user = user)
            )
        )
    }

    fun updateProduct(id: String, productDTO: ProductDTO): Product {
        val productToUpdate = findById(id)

        return productRepository.save(
            productToUpdate.apply {
                name = productDTO.name
                desc = productDTO.desc
                updateDate = LocalDateTime.now()
            }
        )
    }


    fun deleteById(id: String) {
        val product = findById(id)

        productRepository.delete(product)
    }

    @Scheduled(cron = "@daily")
    fun rateProduct(): MutableList<Product> {
        var products = productRepository.findAll()
        products.forEach { product ->

            //Relevance = sales / total days of product existence
            val sales = product.id?.let { saleRepository.findByProductId(it, Sort.by(Sort.Direction.ASC, "id")) }
            val salesCount = sales?.size?.toLong()
            val daysOfCreation = ChronoUnit.DAYS.between(LocalDateTime.now(), product.creationDate)
            val relevance = salesCount?.div(daysOfCreation)

            //Average rating
            val ratings = ratingRepository.findAllFromLastYear(LocalDateTime.now())
            val avgRatings = ratings.sumOf { it.rate } / ratings.size

            //Category Importance
            val today = LocalDateTime.now().toLocalDate().atStartOfDay()
            var categoryArticlesCount = 0
            if (product.category?.id != null) {
                val ctgImportance = articleRepository.findAllByCategoryFromToday(product.category.id, today)
                categoryArticlesCount = ctgImportance.size
            }

            product.score = avgRatings.toLong() + relevance!! + categoryArticlesCount

        }
        return productRepository.saveAll(products)
    }

}
